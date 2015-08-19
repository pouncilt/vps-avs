program WebClient;

uses
  Forms,
  Windows,
  Messages,
  Classes,
  Dialogs,
  SysUtils,
  WebClientForm in 'WebClientForm.pas' {WebClientView},
  LL_StringUtils in 'LL_StringUtils.pas',
  LL_SysUtils in 'LL_SysUtils.pas',
  LL_DateUtils in 'LL_DateUtils.pas',
  ContextorUnit in 'ContextorUnit.pas',
  GoToForm in 'GoToForm.pas' {GoToEntry},
  UserMsgForm in 'UserMsgForm.pas' {UserMsg};

{$R *.res}

var
  i                   : integer;
  fParams             : TStringList;
  fTitle              : string;
  fUrl                : String;
  fHeight             : integer;
  fWidth              : integer;
  fStationNo          : string;
  fUserDuz            : string;
  fPatientDfn         : string;
  fServer             : string;
  fPort               : integer;
  fAllowContextChange : boolean;
  //fMsg                : TStringList;
  fProduction         : boolean;
  Mutex               : THandle;
begin

  Mutex := CreateMutex(nil, true, '{9731F4AC-1AA1-40A0-A70E-A65571DFB561}');
  if (Mutex = 0) or (GetLastError = ERROR_ALREADY_EXISTS) then begin
    ShowMessage('Only one instance of ''After Visit Summary'' is allowed.');
    exit;
  end;

  Application.Initialize;
  fParams:=TStringList.create;
  for i:=1 to ParamCount do begin
    fParams.add(ParamStr(i));
  end;
  fTitle:=fParams.Values['title'];
  fUrl:=fParams.Values['url'];
  fStationNo:=fParams.Values['stationNo'];
  fUserDuz:=fParams.Values['userDuz'];
  fPatientDfn:=fParams.Values['patientDfn'];
  fHeight:=StrToIntDef(fParams.Values['height'], 700);
  fWidth:=StrToIntDef(fParams.Values['width'], 985);
  fProduction:=fParams.Values['production'] = '1';
  fServer:=fParams.Values['s'];
  if (fServer = '') then
    fServer:='localhost';
  fPort:=StrToIntDef(fParams.Values['p'], 19205);
  if (fParams.Values['ALLOW_CONTEXT_CHANGE'] <> '') then begin
    fAllowContextChange:=StrToBool(fParams.Values['ALLOW_CONTEXT_CHANGE']);
  end else begin
    fAllowContextChange:=true;
  end;
  Application.MainFormOnTaskbar := True;
  Application.Title := 'Web Client';
  Application.CreateForm(TWebClientView, WebClientView);
  Application.CreateForm(TGoToEntry, GoToEntry);
  Application.CreateForm(TUserMsg, UserMsg);

  with WebClientView do begin
    SetInitialContext(fTitle, fStationNo, fUserDuz, fPatientDfn, fUrl,
                      fServer, fPort, fProduction, fAllowContextChange);
    SetSize(fHeight, fWidth);
    SetPositionCenter;
    Navigate;
  end;
  Application.Run;

end.
