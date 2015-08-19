unit WebClientForm;

interface

uses
  // Standard
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, ExtCtrls, StrUtils, StdCtrls, Menus, ShellApi, registry,
  // Forms
  GoToForm, UserMsgForm,
  // ActiveX/Ole
  ActiveX, OleCtrls,
  // LL Utils
  LL_StringUtils, LL_SysUtils,
  // Embedded browser
  //SHDocVw_EWB, EwbCore, EmbeddedWB,
  // HTTP
  IdURI,
  // RPC Broker
  Trpcb, RpcSLogin, CCOWRPCBroker,
  // Contextor (CCOW)
  ContextorUnit;

type
  TWebClientView = class(TForm)
    MainMenu1: TMainMenu;
    FileMenuItem: TMenuItem;
    ExitMenuItem: TMenuItem;
    Panel1: TPanel;
    N1: TMenuItem;
    Reset1: TMenuItem;
    Goto1: TMenuItem;
    procedure FormDestroy(Sender: TObject);
    procedure EmbeddedBrowserBeforeNavigate2(Sender: TObject;  const pDisp: IDispatch;
                 var URL, Flags, TargetFrameName, PostData,
                 Headers: OleVariant; var Cancel: WordBool);
    procedure EmbeddedWebBrowserNavigateComplete2(ASender: TObject;
      const pDisp: IDispatch; var URL: OleVariant);
    procedure FormCreate(Sender: TObject);
    procedure ExitMenuItemClick(Sender: TObject);
    procedure RefreshMenuItemClick(Sender: TObject);
    procedure GoToMenuItemClick(Sender: TObject);
    procedure FormClose(Sender: TObject; var Action: TCloseAction);
    procedure FormResize(Sender: TObject);
  private
    fTitle                 : string;
    fBaseUrl               : string;
    fFullUrl               : string;
    fStationNo             : string;
    fUserDuz               : string;
    fVistaServer           : string;
    fVistaToken            : string;
    fServer                : string;
    fPort                  : integer;
    fPatientDfn            : string;
    fPatientName           : string;
    fPatientNatIdNum       : string;
    fAllowContextChange    : boolean;
    fContextor             : TContextor;
    fUserContext           : TUserContext;
    fPatientContext        : TPatientContext;
    fProduction            : boolean;
    //fEmbeddedWebBrowser    : TEmbeddedWB;
    fCCOWRPCBroker         : TCCOWRPCBroker;
    function CreateContextor(fAppTitle : string; fPassCode : string) : TContextor;
    function CheckUserContext : TUserContext;
    function CheckPatientContext : TPatientContext;
    procedure ContextPending(const aContextItemCollection: IDispatch;
                             var fAllowChange : boolean;
                             var fReason : string);
    procedure ContextCanceled;
    procedure ContextCommitted;
    function GetUserDuz : string;
    function EncodeParam(fParam : string) : string;
    function FindAvsWindow(processId: Cardinal): Hwnd;
    procedure UpdateContext(url: String);
    function Launch(ExecuteFile, paramstring: string): Cardinal;
    function GetIExplore: string;
    procedure KillAvs;
    procedure Refresh;
  protected
  public
    procedure Init(fProduction: Boolean);
    procedure SetInitialContext(fTitle, fStationNo, fUserDuz, fPatientDfn,
                                           fUrl, fServer : string;fPort : integer;
                                           fProduction : boolean;
                                           fAllowContextChange : boolean);
    procedure SetSize(fHeight, fWidth : integer);
    procedure Navigate; overload;
    procedure Navigate(fUrl : String); overload;
    procedure Print;
    procedure ResizeBy(fPercent : integer);
    procedure SetPosition(fTop, fLeft : integer);
    procedure SetPositionCenter;
  end;

const
  CPRS_TITLE = 'VistA CPRS in use by:';
  AVSCaption = 'After Visit Summary';

var
  WebClientView    : TWebClientView;
  GSaved8087CW     : Word;
  NeedToUnitialize : Boolean; // True if the OLE subsystem could be initialized successfully.
  AvsHwnd          : Hwnd;
  AvsProcInfo      : TProcessInformation;

function GetHWndByPID(const hPID: THandle): THandle;

implementation

{$R *.dfm}


procedure TWebClientView.SetInitialContext(fTitle, fStationNo, fUserDuz, fPatientDfn,
                                           fUrl, fServer : string;fPort : integer;
                                           fProduction : boolean;
                                           fAllowContextChange : boolean);
begin
  self.fTitle:=fTitle;
  self.caption:=fTitle;
  self.fServer:=fServer;
  self.fPort:=fPort;
  if (fStationNo <> '') then
    self.fStationNo:=fStationNo;
  if (fUserDuz <> '') then
    self.fUserDuz:=fUserDuz;
  if (fPatientDfn <> '') then
    self.fPatientDfn:=fPatientDfn;

  self.fBaseUrl:=fUrl;

  if Pos('?', fBaseUrl) = 0 then
    fBaseUrl:=fBaseUrl + '?'
  else
    fBaseUrl:=fBaseUrl + '&';
  self.fAllowContextChange:=fAllowContextChange;
  Init(fProduction);
end;

procedure TWebClientView.Init(fProduction: Boolean);
begin
  if fContextor <> nil then begin
    fContextor.Production := fProduction;
    fUserContext:=CheckUserContext;
    // get the user's DUZ
    if (fUserContext <> nil) then begin
      if (fUserContext.fVistaToken <> '') then begin
        fVistaToken:=fUserContext.fVistaToken;
      end;
      if (fUserContext.fVistaServer <> '') then begin
        fVistaServer:=fUserContext.fVistaServer;
      end;
      if (self.fStationNo = '') AND (fUserContext.fStationNo <> '') then begin
        fStationNo:=fUserContext.fStationNo;
      end;
    end;
    fPatientContext:=CheckPatientContext;
  end;
end;

procedure TWebClientView.Navigate;
var
  i     : integer;
  fList : TStringList;
begin
  if fFullUrl = '' then begin
    fFullUrl:=fBaseUrl + 'stationNo=' + fStationNo + '&' +
              'userDuz=' + fUserDuz + '&' +
              'patientDfn=' + fPatientDfn + '&' +
              'patientName=' + EncodeParam(fPatientName) + '&' +
              'patientNatIdNum=' + fPatientNatIdNum + '&' +
              'vistaServer=' + EncodeParam(fVistaServer) + '&' +
              'vistaToken=' + EncodeParam(fVistaToken);
  end;
  Navigate(fFullUrl);
end;

procedure TWebClientView.Navigate(fUrl : String);
var
  //r : TRect;
  IExplore   : String;
  processID : Cardinal;
  Wnd : Hwnd;
begin
  UpdateContext(fUrl);
  AvsHwnd := 0;
  try
    IExplore := GetIExplore;
    processId := Launch(IExplore, '-k ' + fUrl);
    wnd := FindAvsWindow(processId);
    if wnd > 0 then begin
      AvsHwnd := Wnd;
      Windows.SetParent( Wnd, Panel1.Handle );
      SetWindowPos(self.Handle, 0,0,0, ClientWidth, ClientHeight, SWP_ASYNCWINDOWPOS);
      ShowWindow(Wnd, SW_MAXIMIZE);
    end;
  except
  end;

  Caption := AVSCaption;
end;

function TWebClientView.Launch(ExecuteFile, paramstring: string): Cardinal;
var
  StartInfo  : TStartupInfo;
  CreateOK   : Boolean;

begin
  FillChar(StartInfo,SizeOf(TStartupInfo),#0);
  FillChar(AvsProcInfo,SizeOf(TProcessInformation),#0);
  StartInfo.cb := SizeOf(TStartupInfo);
  CreateOK := Windows.CreateProcess(nil,
                PChar(ExecuteFile + ' ' + paramstring),
                nil, nil, False,
                CREATE_NEW_PROCESS_GROUP+IDLE_PRIORITY_CLASS+SYNCHRONIZE,
                nil, nil, StartInfo, AvsProcInfo);

  if CreateOk then begin
    WaitForInputIdle(AvsProcInfo.hProcess, INFINITE);
    Result := AvsProcInfo.dwProcessId;
    Closehandle(AvsProcInfo.hThread);
  end;
end;

function TWebClientView.GetIExplore: string;
var
  RegistryEntry: TRegistry;
  RegistryString: string;
Const
  C_KEY='SOFTWARE\Microsoft\Windows\CurrentVersion\App Paths\IEXPLORE.EXE';
begin
  Result := '';
  RegistryEntry := TRegistry.Create(KEY_READ or KEY_WOW64_64KEY);
  try
    RegistryEntry.RootKey := HKEY_LOCAL_MACHINE;
    RegistryEntry.Access := KEY_READ or KEY_WOW64_64KEY;
    if RegistryEntry.OpenKey(C_KEY, false) then
      begin
      Result := RegistryEntry.ReadString('');
      end;
    RegistryEntry.CloseKey();
  finally
    RegistryEntry.Free;
  end;
end;


function TWebClientView.FindAvsWindow(processId: Cardinal): Hwnd;
var
//  WinHandle : HWND;
//  Name: array[0..255] of Char;
//  Title: array[0..255] of Char;
//  AvsTitle: array[0..255] of Char;
  I : integer;
//  dwProcessId : Cardinal;
//  winClass : String;
begin
  Result := 0;
  for I := 0 to 10 do begin
    Sleep(1000);    //Wait to give AVS enough time to show
    Result := GetHWndByPID(processId);
    if Result <> 0 then exit;
  end;
  
  if Result = 0 then begin
    showmessage('Unable to open Avs Window. Terminate IExplore process in Task Manager and try again');
  end;

  //showmessage('Find Avs Window');
  //Result := 0;
  //
  //for I := 0 to 10 do begin
  //  Sleep(1000);
  //  WinHandle := GetTopWindow(GetDesktopWindow);
  //  while WinHandle <> 0 do begin // go thru the window list
  //    GetClassName(WinHandle, @Name[0], 255);
  //    GetWindowText(WinHandle,@Title[0],255);

  //    if (CompareText(string(Name), 'IEFrame') = 0) then begin
  //      GetWindowThreadProcessId(winHandle, dwProcessId);
  //      SHOWMESSAGE('ieframe found. ' + string(title)
  //      + ' Avs ProcessID=' + intToStr(processId)
  //      + ' IEFrame ProcessId=' + intToStr(dwProcessId));
  //      if dwProcessId = processId then begin
  //        showmessage('avs process found!');
  //        Result := WinHandle;
  //        Exit;
  //      end;
  //    end;
  //    WinHandle := GetNextWindow(WinHandle, GW_HWNDNEXT);
  //  end;

  //end;
end;

procedure TWebClientView.Print;
begin
  //fEmbeddedWebBrowser.Print;
end;

function TWebClientView.CreateContextor(fAppTitle : string; fPassCode : string) : TContextor;
var
  ClassID: TCLSID;
  strOLEObject: string;
begin
  Result:=nil;
  try
    strOLEObject := 'Sentillion.Contextor';
    if (CLSIDFromProgID(PWideChar(WideString(strOLEObject)), ClassID) = S_OK) then begin
      Result:=TContextor.Create;
      if Result <> nil then begin
        with Result do begin
          SetContextPendingCallBack(ContextPending);
          SetContextCanceledCallBack(ContextCanceled);
          SetContextCommittedCallBack(ContextCommitted);
          JoinContext(fAppTitle, fPassCode, TRUE, FALSE);
        end;
      end;
    end;
  except
    // ignore
  end;
end;

function TWebClientView.CheckUserContext : TUserContext;
begin
  Result:=nil;
  if fContextor <> nil then begin
    // Check CCOW context for a user
    try
      Result:=fContextor.GetUserContext;
      if (Result <> nil) AND (Result.fVistaServer <> '') then begin
        fVistaServer:=Result.fVistaServer;
        if (self.fStationNo = '') then begin
          fStationNo:=Result.fStationNo;
        end;
        fContextor.SetStationNumber(fStationNo);
      end else
        fContextor.SetStationNumber(fStationNo);
    except
      // ignore
    end;
  end;
end;

function TWebClientView.CheckPatientContext : TPatientContext;
begin
  Result:=nil;
  if fContextor <> nil then begin
    //ShowMessage('Switching patient');
    // Check CCOW context for an open patient
    try
      Result:=fContextor.GetPatientContext;
      //ShowMessage('Getting New PatientContext. New DFN=' + Result.fDfn);
      if (Result.fDfn <> '') then begin
        fPatientDfn:=Result.fDfn;
      end;
      if (Result.fName <> '') then begin
        fPatientName:=Result.fName;
      end;
      if (Result.fNatIdNum <> '') then begin
        fPatientNatIdNum:=Result.fNatIdNum;
      end;
    except
      // ignore
    end;
  end;
end;

function TWebClientView.GetUserDuz : string;
begin
  Result:='';
  if fContextor <> nil then begin
    try
      fCCOWRPCBroker:=TCCOWRPCBroker.Create(application);
      with fCCOWRPCBroker do begin
        Server:=fServer;
        ListenerPort:=fPort;
        KernelLogIn:=TRUE;
        Login.Mode:=lmAppHandle;
        Contextor:=fContextor.ContextorControl;
        if (fUserContext <> nil) then begin
          Login.NTToken:=fUserContext.fVistaToken;
          Login.LogInHandle:=fUserContext.fVistaToken;
        end;
        Connected:=TRUE;
        Result:=User.DUZ;
        Connected:=FALSE;
      end;
    except
    end;
  end;
end;

procedure TWebClientView.GoToMenuItemClick(Sender: TObject);
begin
  if GoToEntry.GetUrl(fFullUrl) then begin
    KillAvs;
    Navigate(fFullUrl);
  end;
end;

procedure TWebClientView.ContextPending(const aContextItemCollection: IDispatch;
                                var fAllowChange : boolean;
                                var fReason : string);
begin
  fAllowChange:=TRUE;
end;

procedure TWebClientView.ContextCanceled;
begin
  //
end;

procedure TWebClientView.ContextCommitted;
begin
  if (fContextor.CurrentState = ContextorUnit.CCOW_ENABLED) AND
      fAllowContextChange then begin

    //KillAvs;

    fUserContext:=CheckUserContext;
    if (fUserContext <> nil) AND (fUserContext.fVistaToken <> fVistaToken) then begin
      fUserDuz:=GetUserDuz;
      fVistaToken:=fUserContext.fVistaToken;
      if (self.fStationNo = '') then begin
        fStationNo:=fUserContext.fStationNo;
      end;
      fVistaServer:=fUserContext.fVistaServer;
      fContextor.UpdateUserContext(fUserContext);
    end;
    fPatientContext:=CheckPatientContext;
    fContextor.UpdatePatientContext(fPatientContext);
    Sleep(1000);
    Refresh;
  end;
end;

procedure TWebClientView.SetSize(fHeight, fWidth : integer);
begin
  self.ClientHeight:=fHeight;
  self.ClientWidth:=fWidth;
end;

procedure TWebClientView.RefreshMenuItemClick(Sender: TObject);
begin
  Refresh;
end;

procedure TWebClientView.Refresh;
begin
  KillAvs;
  fFullUrl := '';
  Navigate;
end;

procedure TWebClientView.ResizeBy(fPercent : integer);
var
  fResizeBy : double;
begin
  fResizeBy:=fPercent * 0.01;
  self.ClientHeight:=self.ClientHeight + Round(self.ClientHeight * fResizeBy);
  self.ClientWidth:=self.ClientWidth + Round(self.ClientWidth * fResizeBy);
end;

procedure TWebClientView.SetPosition(fTop, fLeft : integer);
begin
  self.Top:=fTop;
  self.Left:=fLeft;
end;

procedure TWebClientView.SetPositionCenter;
var
  fMonitor        : TMonitor;
  fLeft, fTop,
  fWidth, fHeight : integer;
  fCprsHwnd       : HWND;
begin
  fCprsHwnd:=LL_SysUtils.FindWindowEx(CPRS_TITLE);
  if (fCprsHwnd <> 0) then begin
    fMonitor:=Screen.MonitorFromWindow(fCprsHwnd, mdNearest);
    if (fMonitor <> nil) then begin
      fLeft:=fMonitor.Left;
      fTop:=fMonitor.Top;
      fHeight:=fMonitor.Height;
      fWidth:=fMonitor.Width;
    end;
  end else begin
    fLeft:=Screen.DesktopLeft;
    fTop:=Screen.DesktopTop;
    fHeight:=Screen.DesktopHeight;
    fWidth:=Screen.DesktopWidth;
  end;
  fLeft:=fLeft + ((fWidth div 2) - (self.Width div 2));
  fTop:=fTop + ((fHeight div 2) - (self.Height div 2));
  SetPosition(fTop, fLeft);
end;

procedure TWebClientView.UpdateContext(url: String);
var
  fStr, fPatientDfn, fStationNo : string;
  fParams : TStringList;
  fIndex  : integer;
begin
  if (AnsiStartsStr('about:blank', URL)) then begin
    //ShowMessage(URL);
    fStr:=LL_StringUtils.Piece(URL, '^', 2);
    if (AnsiStartsStr('CCOWContext', fStr)) then begin
      fStr:=LL_StringUtils.Piece(fStr, '?', 2);
      fParams:=LL_StringUtils.PiecesToList(fStr, '&');
      fPatientDfn:=fParams.Values['patientDfn'];
      fStationNo:=fParams.Values['stationNo'];
      if (fPatientDfn <> '') then begin
        fContextor.UpdateContext(CONTEXT_KEY_PATIENT_DFN + fStationNo, fPatientDfn);
      end;
    end else begin
    end;
  end else begin
  end;
end;


procedure TWebClientView.EmbeddedBrowserBeforeNavigate2(Sender: TObject;  const pDisp: IDispatch;
                                                   var URL, Flags, TargetFrameName, PostData,
                                                   Headers: OleVariant; var Cancel: WordBool);
var
  fStr, fPatientDfn, fStationNo : string;
  fParams : TStringList;
  fIndex  : integer;
begin
  if (AnsiStartsStr('about:blank', URL)) then begin
    fStr:=LL_StringUtils.Piece(URL, '^', 2);
    if (AnsiStartsStr('CCOWContext', fStr)) then begin
      fStr:=LL_StringUtils.Piece(fStr, '?', 2);
      fParams:=LL_StringUtils.PiecesToList(fStr, '&');
      fPatientDfn:=fParams.Values['patientDfn'];
      fStationNo:=fParams.Values['stationNo'];
      if (fPatientDfn <> '') then begin
        fContextor.UpdateContext(CONTEXT_KEY_PATIENT_DFN + fStationNo, fPatientDfn);
      end;
      Cancel:=TRUE;
    end else begin
      Cancel:=FALSE;
    end;
  end else begin
    Cancel:=FALSE;
  end;
end;



procedure TWebClientView.EmbeddedWebBrowserNavigateComplete2(ASender: TObject;
      const pDisp: IDispatch; var URL: OleVariant);
begin
  //fEmbeddedWebBrowser.UserInterfaceOptions:=[DontUse3DBorders];
end;

procedure TWebClientView.ExitMenuItemClick(Sender: TObject);
begin
Close;
end;

procedure TWebClientView.FormClose(Sender: TObject; var Action: TCloseAction);
begin
  KillAvs;
end;

procedure TWebClientView.KillAvs;
var
  exist, dwProcessId: Cardinal;
begin
  if AvsHwnd <> 0 then begin
    PostMessage(AvsHwnd, WM_CLOSE, 0,0);
    exist := 1;
    while exist <> 0 do begin
      Sleep(1000);
      exist := GetWindowThreadProcessId(AvsHwnd, dwProcessId);
    end;
    AvsHwnd := 0;
  end;
end;


procedure TWebClientView.FormCreate(Sender: TObject);
begin
  //fEmbeddedWebBrowser:=TEmbeddedWB.Create(nil);
  //with fEmbeddedWebBrowser do begin
  //  Align:=alClient;
   // Parent:=self;
   // UserInterfaceOptions:=[DontUse3DBorders, DontUseScrollBars];
  //  OnBeforeNavigate2:=EmbeddedBrowserBeforeNavigate2;
  //  OnNavigateComplete2:=EmbeddedWebBrowserNavigateComplete2;
  //end;
  fContextor:=CreateContextor('Web Client', '');
end;

procedure TWebClientView.FormDestroy(Sender: TObject);
begin
  if Assigned(fContextor) then begin
    try
      fContextor.SuspendContext;
      FreeAndNil(fContextor);
    except
    end;
  end;
  //if Assigned(fEmbeddedWebBrowser) then begin
  //  fEmbeddedWebBrowser.Navigate('about:blank');
  //  Application.ProcessMessages;
  //  CoFreeUnusedLibraries();
  //  FreeAndNil(fEmbeddedWebBrowser);
  //end;
end;

procedure TWebClientView.FormResize(Sender: TObject);
begin
 SetWindowPos(AVSHwnd, 0, 0, 0, Panel1.clientwidth, Panel1.clientheight, SWP_NOACTIVATE); //SWP_NOZORDER or SWP_SHOWWINDOW
end;

function TWebClientView.EncodeParam(fParam : string) : string;
begin
  fParam:=LL_StringUtils.ReplaceText(fParam, '&', '&#38;');
  Result:=TIdURI.ParamsEncode(fParam);
end;


function GetHWndByPID(const hPID: THandle): THandle;
     type
     PEnumInfo = ^TEnumInfo;
     TEnumInfo = record
     ProcessID: DWORD;
     HWND: THandle;
   end;

     function EnumWindowsProc(Wnd: DWORD; var EI: TEnumInfo): Bool; stdcall;
     var
         PID: DWORD;
     begin
         GetWindowThreadProcessID(Wnd, @PID);
         Result := (PID <> EI.ProcessID) or
                 (not IsWindowVisible(WND)) or
                 (not IsWindowEnabled(WND));

         if not Result then EI.HWND := WND; //break on return FALSE
     end;

     function FindMainWindow(PID: DWORD): DWORD;
     var
         EI: TEnumInfo;
     begin
         EI.ProcessID := PID;
         EI.HWND := 0;
         EnumWindows(@EnumWindowsProc, Integer(@EI));
         Result := EI.HWND;
     end;

 begin
     if hPID<>0 then
         Result:=FindMainWindow(hPID)
     else
         Result:=0;
 end;


initialization
  //GSaved8087CW := Default8087CW;
  //Set8087CW($133F);
  // Initialize OLE subsystem for drag'n drop and clipboard operations.
  //NeedToUnitialize := Succeeded(OleInitialize(nil));

finalization
  //Set8087CW(GSaved8087CW);
  //if NeedToUnitialize then
  //try
  //  OleUninitialize;
  //except
  //end;

end.
