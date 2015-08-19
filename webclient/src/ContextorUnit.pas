unit ContextorUnit;

interface

uses
  // Standard
  SysUtils, Forms, ComObj, OleCtrls, Dialogs,
  // CCOW
  VERGENCECONTEXTORLib_TLB,
  // Utils
  LL_StringUtils;

type
  TPatientContext = class
    fNatIdNum  : string;
    fDfn       : string;
    fName      : string;
  end;

  TUserContext = class
    fUsername    : string;
    fStationNo   : string;
    fVistaServer : string;
    fVistaName   : string;
    fVistaToken  : string;
    fVpid        : string;
  end;

  TStringArray = array of string;
  TContextPendingCallBack   = procedure(const aContextItemCollection: IDispatch;
                                        var fAllowChange : boolean;
                                        var fReason : string) of Object;
  TContextCanceledCallBack  = procedure of Object;
  TContextCommittedCallBack = procedure of Object;
  TContextor = class
    procedure ContextorControlCanceled(Sender: TObject);
    procedure ContextorControlCommitted(Sender: TObject);
    procedure ContextorControlPending(ASender: TObject;
                                      const aContextItemCollection: IDispatch);
  private
    fProduction               : boolean;
    fCurrentState             : integer;
    fContextorControl         : TContextorControl;
    fLastException            : EOleException;
    fContextKeyDfn            : string;
    fContextPendingCallBack   : TContextPendingCallBack;
    fContextCanceledCallBack  : TContextCanceledCallBack;
    fContextCommittedCallBack : TContextCommittedCallBack;
  public
    constructor Create;
    destructor Destroy;               override;
    procedure SetContextPendingCallBack(fContextPendingCallBack   : TContextPendingCallBack);
    procedure SetContextCanceledCallBack(fContextCanceledCallBack  : TContextCanceledCallBack);
    procedure SetContextCommittedCallBack(fContextCommittedCallBack : TContextCommittedCallBack);
    procedure SetStationNumber(fStationNumber : string);
    function JoinContext(fAppName : string; fPasscode : string;
                         fAllowMultipleInstances, fNotify : boolean) : boolean;
    function UpdateContext(fItemNames, fItemValues : array of string) : UserResponse;   overload;
    function UpdateContext(fItemName, fItemValue : string) : UserResponse;              overload;
    function UpdatePatientContext(fPatientContext : TPatientContext) : UserResponse;
    function UpdateUserContext(fUserContext : TUserContext) : UserResponse;
    function GetUserPrivilege : AccessPrivilege;
    function SuspendContext : boolean;
    function ResumeContext : boolean;
    function CheckContext(fItemName : string) : string;                                 overload;
    function CheckContext(fItemNames : array of string) : TStringArray;                 overload;
    function GetPatientContext : TPatientContext;
    function GetUserContext : TUserContext;
    function GetLastException : EOleException;
    property CurrentState : integer read fCurrentState;
    property ContextorControl : TContextorControl read fContextorControl;
    property Production : Boolean read fProduction write fProduction;
  end;

const
  // CCOW states
  CCOW_ENABLED   = 1;
  CCOW_DISABLED  = 2;
  CCOW_SUSPENDED = 3;

  // CCOW context keys.
  CONTEXT_KEY_PATIENT_NAT_ID    = 'patient.id.mrn.nationalidnumber';  // National ID number
  CONTEXT_KEY_PATIENT_NAME      = 'patient.co.patientname';           // Last^First MI
  CONTEXT_KEY_PATIENT_DFN       = 'patient.id.mrn.dfn_';              // Patient DFN
  CONTEXT_KEY_USER_NAME         = 'user.co.name';                     // User's full name
  CONTEXT_KEY_USER_VISTALOGON   = 'user.id.logon.vistalogon';         // Vista server host name
  CONTEXT_KEY_USER_VISTANAME    = 'user.id.logon.vistaname';          // User's full name
  CONTEXT_KEY_SIGNON_TOKEN      = 'user.id.logon.vistatoken';         // Vista signon token
  CONTEXT_KEY_USER_VPID         = 'user.id.logon.vpid';

  // HRESULT returned from Contextor for any CM error (including CM not installed).
  ITF_E_CONTEXT_MANAGER_ERROR = HRESULT($80040206);

implementation

// CONSTRUCTOR / DESTRUCTOR
constructor TContextor.Create;
begin
  inherited Create;
  fProduction := true;
  fContextorControl:=TContextorControl.Create(Application);
  if fContextorControl <> nil then begin
    with fContextorControl do begin
      OnPending:=ContextorControlPending;
      OnCanceled:=ContextorControlCanceled;
      OnCommitted:=ContextorControlCommitted;
    end;
  end;
  fCurrentState:=CCOW_DISABLED;
end;

destructor TContextor.Destroy;
begin
  inherited Destroy;
  try
    if fContextorControl <> nil then
      fContextorControl.Free;
  except
  end;
end;

// Set Callbacks
procedure TContextor.SetContextPendingCallBack(fContextPendingCallBack   : TContextPendingCallBack);
begin
  self.fContextPendingCallBack:=fContextPendingCallBack;
end;

procedure TContextor.SetContextCanceledCallBack(fContextCanceledCallBack  : TContextCanceledCallBack);
begin
  self.fContextCanceledCallBack:=fContextCanceledCallBack;
end;

procedure TContextor.SetContextCommittedCallBack(fContextCommittedCallBack : TContextCommittedCallBack);
begin
  self.fContextCommittedCallBack:=fContextCommittedCallBack;
end;

// Set the division # for the patient key name
procedure TContextor.SetStationNumber(fStationNumber : string);
begin
  fContextKeyDfn:=CONTEXT_KEY_PATIENT_DFN + fStationNumber;
  if not fProduction then
    fContextKeyDfn:=fContextKeyDfn + '_test';

end;

//
// Join the common context
//
function TContextor.JoinContext(fAppName, fPasscode : string;
                                fAllowMultipleInstances, fNotify : boolean) : boolean;
begin
  if fContextorControl <> nil then begin
    Result:=TRUE;
    try
      // Start the Contextor which will join the context.
      // Indicate desire to be surveyed and notified of all subjects.
      // Append '#' to the app name to permit multiple instances of this app to
      // participate in the context.
      if fAllowMultipleInstances then
        fAppName:=fAppName + '#';
      fContextorControl.Run(fAppName, fPasscode, fNotify, '*');
      fCurrentState:=CCOW_ENABLED;
    except
      on exc : EOleException do begin
        if (exc.ErrorCode = ITF_E_CONTEXT_MANAGER_ERROR) then begin
          fLastException:=exc;
          Result:=FALSE;
          fCurrentState:=CCOW_DISABLED;
        end;
      end;
    end;
  end else
    Result:=FALSE;
end;

//
//  Instigate a context change
//
function TContextor.UpdateContext(fItemNames, fItemValues : array of string) : UserResponse;
var
  data        : IContextItemCollection;
  dataItem    : IContextItem;
  i           : integer;
begin
  Result:=$00000000;
  if (fContextorControl <> nil) AND (fCurrentState = CCOW_ENABLED) then begin
    try
      // Start a context change transaction
      fContextorControl.StartContextChange;
      // Set the new proposed context data.
      data := CoContextItemCollection.Create;
      for i:=0 to High(fItemNames) do begin
        dataItem:=CoContextItem.Create;
        dataItem.Name:=fItemNames[i];
        dataItem.Value:=fItemValues[i];
        data.Add(dataItem);
      end;
      // End the context change transaction.
      Result:=fContextorControl.EndContextChange(true, data);
    except
      on exc : EOleException do
        fLastException:=exc;
    end;
  end;
end;

function TContextor.UpdateContext(fItemName, fItemValue : string): UserResponse;
var
  data        : IContextItemCollection;
  dataItem    : IContextItem;
begin
  Result:=$00000000;
  if (fContextorControl <> nil) AND (fCurrentState = CCOW_ENABLED) then begin
    try
      // Start a context change transaction
      fContextorControl.StartContextChange;
      // Set the new proposed context data.
      data := CoContextItemCollection.Create;
      dataItem:=CoContextItem.Create;
      dataItem.Name:=fItemName;
      dataItem.Value:=fItemValue;
      data.Add(dataItem);
      // End the context change transaction.
       Result:=fContextorControl.EndContextChange(true, data);
    except
      on exc : EOleException do
        fLastException:=exc;
    end;
  end;
end;

function TContextor.UpdatePatientContext(fPatientContext : TPatientContext): UserResponse;
begin
   //Result:=UpdateContext(fContextKeyDfn, fPatientContext.fDfn);
   Result:=UpdateContext([CONTEXT_KEY_PATIENT_NAT_ID, CONTEXT_KEY_PATIENT_NAME, fContextKeyDfn],
                         [fPatientContext.fNatIdNum, fPatientContext.fName, fPatientContext.fDfn]);
end;

function TContextor.UpdateUserContext(fUserContext : TUserContext) : UserResponse;
begin
  //Result:=UpdateContext([CONTEXT_KEY_SIGNON_TOKEN], [fUserContext.fVistaToken]);
   Result:=UpdateContext([CONTEXT_KEY_USER_NAME, CONTEXT_KEY_USER_VISTALOGON,
                          CONTEXT_KEY_USER_VISTANAME, CONTEXT_KEY_SIGNON_TOKEN,
                          CONTEXT_KEY_USER_VPID],
                         [fUserContext.fUsername, fUserContext.fVistaServer, fUserContext.fVistaName,
                          fUserContext.fVistaToken, fUserContext.fVpid]);
end;

function TContextor.GetUserPrivilege : AccessPrivilege;
begin
  Result := ApNone;
  if (fContextorControl <> nil) AND (fCurrentState = CCOW_ENABLED) then begin
    try
      Result := fContextorControl.GetPrivilege('user');
    except
      on exc : EOleException do
        fLastException:=exc;
    end;
  end;
end;

function TContextor.SuspendContext : boolean;
begin
  if (fContextorControl <> nil) AND (fCurrentState = CCOW_ENABLED) then begin
    Result:=TRUE;
    try
      fContextorControl.Suspend;
      fCurrentState:=CCOW_SUSPENDED;
      // This app will not receive notifications of context changes until it
      // rejoins the common context.
    except
      on exc : EOleException do begin
        fLastException:=exc;
        Result:=FALSE;
      end;
    end;
  end else
    Result:=FALSE;
end;

function TContextor.ResumeContext : boolean;
begin
  if (fContextorControl <> nil) AND (fCurrentState = CCOW_SUSPENDED) then begin
    Result:=TRUE;
    try
      fContextorControl.Resume;
      fCurrentState:=CCOW_ENABLED;
    except
      on exc : EOleException do begin
        fLastException:=exc;
        Result:=FALSE;
      end;
    end;
  end else
    Result:=FALSE;
end;

//
// CheckContext - check the context for the given item name
//
function TContextor.CheckContext(fItemName : string) : string;
var
  contextItems        : IContextItemCollection;
  anItem              : IContextItem;
begin
  Result:='';
  if (fContextorControl <> nil) AND (fCurrentState = CCOW_ENABLED) then begin
    // Get the current context (if any).
    try
      contextItems := fContextorControl.CurrentContext;
      // See if the context contains the ID item(s) of interest to this app.
      anItem := contextItems.Present(fItemName);  // returns null if item not in context
      if (anItem <> nil) then begin
        // Get the specific item value
        Result := anItem.value;
      end;
    except
      on exc : EOleException do
        fLastException:=exc;
    end;
  end;
end;

//
// CheckContext - check the context for the given item names
//
function TContextor.CheckContext(fItemNames : array of string) : TStringArray;
var
  contextItems        : IContextItemCollection;
  anItem              : IContextItem;
  i                   : integer;
begin
  SetLength(Result, High(fItemNames)+1);
  if (fContextorControl <> nil) AND (fCurrentState = CCOW_ENABLED) then begin
    // Get the current context (if any).
    try
      contextItems := fContextorControl.CurrentContext;
      for i:=0 to High(fItemNames) do begin
        // See if the context contains the ID item(s) of interest to this app.
        anItem := contextItems.Present(fItemNames[i]);  // returns null if item not in context
        if (anItem <> nil) then begin
          // Get the specific item value
          Result[i]:=anItem.value;
        end else
          Result[i]:='';
      end;
    except
      on exc : EOleException do
        fLastException:=exc;
    end;
  end;
end;

//
// GetPatientContext - checks the context for the patient's DFN.
//                     returns the dfn if context found, otherwise 0.
//
function TContextor.GetPatientContext : TPatientContext;
var
  fValues             : TStringArray;
  fContextKeyPatNatID : string;
begin
  Result:=TPatientContext.create;
  fContextKeyPatNatID := CONTEXT_KEY_PATIENT_NAT_ID;
  if not fProduction then
    fContextKeyPatNatID := CONTEXT_KEY_PATIENT_NAT_ID + '_test';

  fValues:=CheckContext([fContextKeyPatNatID, CONTEXT_KEY_PATIENT_NAME,
                         fContextKeyDfn]);
  Result.fNatIdNum:=fValues[0];
  Result.fName:=fValues[1];
  Result.fDfn:=fValues[2];
  //ShowMessage(fContextKeyPatNatID + '=' +  Result.fNatIdNum + Chr(13) +
  //            CONTEXT_KEY_PATIENT_NAME + '=' +  Result.fName + Chr(13) +
  //            fContextKeyDfn + '=' + Result.fDfn);
end;

//
// GetUserContext - checks the context for the user's signon token.
//                  returns the token if context found, otherwise ''.
//
function TContextor.GetUserContext : TUserContext;
var
  fValues            : TStringArray;
begin
  Result:=TUserContext.create;
  fValues:=CheckContext([CONTEXT_KEY_USER_NAME, CONTEXT_KEY_USER_VISTALOGON,
                         CONTEXT_KEY_USER_VISTANAME, CONTEXT_KEY_SIGNON_TOKEN,
                         CONTEXT_KEY_USER_VPID]);
  Result.fUsername:=fValues[0];
  Result.fVistaServer:=LL_StringUtils.Piece(fValues[1], '^', 1);
  Result.fStationNo:=LL_StringUtils.Piece(fValues[1], '^', 2);
  Result.fVistaName:=fValues[2];
  Result.fVistaToken:=fValues[3];
  Result.fVpid:=fValues[4];
//  ShowMessage(CONTEXT_KEY_USER_NAME + '=' + Result.fUsername + Chr(13) +
//              CONTEXT_KEY_USER_VISTALOGON + '=' + Result.fVistaServer + '^' + Result.fStationNo + Chr(13) +
//              CONTEXT_KEY_USER_VISTANAME + '=' + Result.fVistaName + Chr(13) +
//              CONTEXT_KEY_SIGNON_TOKEN + '=' + Result.fVistaToken + Chr(13) +
//              CONTEXT_KEY_USER_VPID + '=' + Result.fVpid);
end;

procedure TContextor.ContextorControlCanceled(Sender: TObject);
begin
  inherited;
  // proposed context change canceled, maintain current state.
  if assigned(fContextPendingCallBack) then
    fContextCanceledCallBack;
end;

procedure TContextor.ContextorControlCommitted(Sender: TObject);
begin
  inherited;
  // proposed context change committed, access new context and update state.
  if assigned(fContextCanceledCallBack) then
    fContextCommittedCallBack;
end;

procedure TContextor.ContextorControlPending(ASender: TObject;
                                             const aContextItemCollection: IDispatch);
var
  fAllowChange : boolean;
  fReason      : string;
begin
  inherited;
  // proposed context change pending appliation survey.
  // If this app can change context, simply return from this event and the
  // Contextor will return an 'accept'.
  // If this app would lose data, or have other problems changing context at
  // this time, it should specify a reason using SetSurveyReponse, and the
  // Contextor will return an 'accept_conditional' with the supplied reason.
  if assigned(fContextCommittedCallBack) then begin
    fContextPendingCallBack(aContextItemCollection, fAllowChange, fReason);
    if NOT fAllowChange then
      fContextorControl.SetSurveyResponse(fReason);
  end;
end;

// Returns the last OLE exception caught
function TContextor.GetLastException : EOleException;
begin
  Result:=fLastException;
end;

end.
