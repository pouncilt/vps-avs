(* ****************************************************************************)
 //  Date Created: Jan 25, 2012
 //  Site Name: Loma Linda VAMC
 //  Developers: Rob Durkin
 //  Description: R1 OR Schedule Viewer
 //  Note: This unit requires R1SRLOR in order to run.
(******************************************************************************)
unit LL_SysUtils;

interface

uses

  // Standard
  SysUtils, Classes, StdCtrls, Math, Windows, Dialogs, FileCtrl,
  Controls, Forms, Variants, Registry, ShellAPI, WinSvc, Messages,
  // Networking
  Winsock, WinInet,
  // XML
  XMLDoc, XMLIntf,
  // Utils
  LL_StringUtils, LL_DateUtils;

type
  TMemoryInfo = class
    MemoryLoad : DWORD;
    TotalPhys : DWORD;
    AvailPhys : DWORD;
    TotalPageFile : DWORD;
    AvailPageFile : DWORD;
    TotalVirtual : DWORD;
    AvailVirtual : DWORD;
  end;

  TApplicationInfo = class
    CompanyName            : string;
    FileDescription        : string;
    FileVersion            : string;
    InternalName           : string;
    LegalCopyright         : string;
    LegalTradeMarks        : string;
    OriginalFilename       : string;
    ProductName            : string;
    ProductVersion         : string;
    Comments               : string;
  end;

  TOSInfo = class
    fWin32PlatformId   : Integer;
    fWin32Platform     : String;
    fWin32MajorVersion : Integer;
    fWin32MinorVersion : Integer;
    fBuildNumber       : Integer;
    fWin32CSDVersion   : String;
  end;

  TWin32Platform = class
    fWin32PlatformId   : Integer;
    fWin32Platform     : String;
    fBuildNumber       : Integer;
  end;

  HWNDArr = array of HWND;

// SYSTEM FUNCTIONS
function WinExecAndWait32(FileName,Parameters : String) : DWORD;
function WinExec(FileName,Parameters : String) : TProcessInformation;
function GetApplicationInfo : TApplicationInfo;
function StartProcess(FileName,Parameters : String) : DWORD;overload;
function StartProcess(FileName,Parameters : String;xPos, yPos : integer) : DWORD; overload;
function ProcessExists(processID : dWord) : boolean;
function GetOSInfo : string;
function GetOSInfo2 : TOSInfo;
function GetWin32Platform : TWin32Platform;
function IsRemoteSession: boolean;
function GetWindowsUsername : string;
function GetMemoryInfo : TMemoryInfo;
function GetMachineName : String;
function GetMachineIP : string;
function GetTempDir : string;
function GetShellFolder(fName : string) : string;
procedure ExecBrowser(fURL : string);
function GetIEVersion : string;
procedure DeleteIECache;
function GetInternetConnectionType : Word;
function DateTimeToFileTime(ADateTime: TDateTime): int64;
function CreateTimer : THandle;
procedure StartTimer(fHandle: THandle;ADateTime: TDateTime);
procedure ResetTimer(fHandle: THandle);
function CancelTimer(fHandle : THandle) : boolean;
function WaitTill(ADateTime: TDateTime) : THandle;
function GetServiceList(sMachine : string; dwServiceType,
                        dwServiceState : DWord; slServicesList : TStrings) : boolean;
function ServiceGetStatus(sMachine, sService : string ) : DWord;
function ServiceRunning(sMachine, sService : string ) : boolean;
function ServiceStopped(sMachine, sService : string ) : boolean;
function ServiceStart(sMachine, sService : string ) : boolean;
function ServiceStop(sMachine, sService : string ) : boolean;
function GetEnvVar(fName : string) : string;
function GetAppVersion(AppName : string): String;
function GetRegistryValue(Path, Key: string): string;                 overload;
function GetRegistryValue(Root : cardinal;Path, Key: string): string; overload;
function FindWindowEx(fTitle: string): HWND;  overload;
function FindWindowEx(fTitle: string; fCaseSensitive : boolean): HWND; overload;
function FindWindows(fTitle: string; fCaseSensitive : boolean): HWNDArr;

const
  // Internet connection status constants
  INTERNET_CONNECTION_MODEM       = 1;
  INTERNET_CONNECTION_LAN         = 2;
  INTERNET_CONNECTION_PROXY       = 4;
  INTERNET_CONNECTION_MODEM_BUSY  = 8;


  //
  // Service Types
  //
  SERVICE_KERNEL_DRIVER       = $00000001;
  SERVICE_FILE_SYSTEM_DRIVER  = $00000002;
  SERVICE_ADAPTER             = $00000004;
  SERVICE_RECOGNIZER_DRIVER   = $00000008;

  SERVICE_DRIVER              =
    (SERVICE_KERNEL_DRIVER or
     SERVICE_FILE_SYSTEM_DRIVER or
     SERVICE_RECOGNIZER_DRIVER);

  SERVICE_WIN32_OWN_PROCESS   = $00000010;
  SERVICE_WIN32_SHARE_PROCESS = $00000020;
  SERVICE_WIN32               =
    (SERVICE_WIN32_OWN_PROCESS or
     SERVICE_WIN32_SHARE_PROCESS);

  SERVICE_INTERACTIVE_PROCESS = $00000100;

  SERVICE_TYPE_ALL            =
    (SERVICE_WIN32 or
     SERVICE_ADAPTER or
     SERVICE_DRIVER  or
     SERVICE_INTERACTIVE_PROCESS);

implementation

function GetApplicationInfo: TApplicationInfo;
const
  InfoNum = 10;
  InfoStr: array[1..InfoNum] of string = ('CompanyName', 'FileDescription', 'FileVersion',
                                          'InternalName', 'LegalCopyright', 'LegalTradeMarks',
                                          'OriginalFileName', 'ProductName', 'ProductVersion', 'Comments');
var
  S          : string;
  n, Len, i  : DWORD;
  Buf        : PChar;
  Value      : PChar;
begin
  S := Application.ExeName; //this application's EXE filename
  n := GetFileVersionInfoSize(PChar(S), n); // the return value is the size in bytes of the file's version information.
  Result := TApplicationInfo.create;
  if n > 0 then begin
    Buf := AllocMem(n); // allocate the needed amount of memory into the buffer
    GetFileVersionInfo(PChar(S), 0, n, Buf); // store the file version information in the memory buffer. it stores all of the values listed in the InfoStr array.
    for i := 1 to InfoNum do begin
      if VerQueryValue(Buf, PChar('StringFileInfo\040904E4\' + InfoStr[i]), Pointer(Value), Len) then begin
        S:=Trim(UpperCase(InfoStr[i]));
        if S = 'COMPANYNAME' then
          Result.CompanyName:=Value
        else if S = 'FILEDESCRIPTION' then
          Result.FileDescription:=Value
        else if S = 'FILEVERSION' then
          Result.FileVersion:=Value
        else if S = 'INTERNALNAME' then
          Result.InternalName:=Value
        else if S = 'LEGALCOPYRIGHT' then
          Result.LegalCopyright:=Value
        else if S = 'LEGALTRADEMARKS' then
          Result.LegalTrademarks:=Value
        else if S = 'ORIGINALFILENAME' then
          Result.OriginalFilename:=Value
        else if S = 'PRODUCTNAME' then
          Result.ProductName:=Value
        else if S = 'PRODUCTVERSION' then
          Result.ProductVersion:=Value
        else if S = 'COMMENTS' then
          Result.Comments:=Value;
      end;
    end;
    FreeMem(Buf, n); // free the memory we stored in the buffer.
  end;
end;

function WinExecAndWait32(FileName,Parameters : String) : DWORD;
var
  ProcessInfo : TProcessInformation;
begin
  ProcessInfo:=WinExec(FileName, Parameters);
  if ProcessInfo.hProcess <> 0 then begin
    WaitforSingleObject(ProcessInfo.hProcess,INFINITE);
    GetExitCodeProcess(ProcessInfo.hProcess, Result);
  end else
    Result:=0;
end;

function WinExec(FileName,Parameters : String) : TProcessInformation;
var
  zCurDir:array[0..255] of char;
  zParams:array[0..255] of char;
  WorkDir:String;
  StartupInfo:TStartupInfo;
begin
  StrPCopy(zParams,FileName + ' ' + Parameters);
  GetDir(0,WorkDir);
  StrPCopy(zCurDir,WorkDir);
  FillChar(StartupInfo,Sizeof(StartupInfo),#0);
  StartupInfo.cb := Sizeof(StartupInfo);
  if NOT CreateProcess(nil,
    zParams,                           { pointer to command line string }
    nil,                               { pointer to process security attributes }
    nil,                               { pointer to thread security attributes }
    false,                             { handle inheritance flag }
    CREATE_NEW_CONSOLE or              { creation flags }
    NORMAL_PRIORITY_CLASS,
    nil,                               { pointer to new environment block }
    nil,                               { pointer to current directory name }
    StartupInfo,                       { pointer to STARTUPINFO }
    Result) then Result.hProcess:=0;   { pointer to PROCESS_INF }
end;

function StartProcess(FileName,Parameters : String) : DWORD;overload;
var
  zParams:array[0..255] of char;
  WorkDir:String;
  StartupInfo:TStartupInfo;
  ProcessInfo:TProcessInformation;
begin
  FillMemory(@StartupInfo, sizeof(StartupInfo), 0);
  StartupInfo.cb:=sizeof(StartupInfo);
  if CreateProcess(nil,
    PChar(FileName + ' ' + Parameters), { pointer to command line string }
    nil,                                { pointer to process security attributes }
    nil,                                { pointer to thread security attributes }
    false,                              { handle inheritance flag }
    CREATE_NEW_CONSOLE or               { creation flags }
    NORMAL_PRIORITY_CLASS,
    nil,                                { pointer to new environment block }
    nil,                                { pointer to current directory name }
    StartupInfo,                        { pointer to STARTUPINFO }
    ProcessInfo)                        { pointer to PROCESS_INF }
  then
    result:=ProcessInfo.dwProcessID
  else
    result:=0;
end;

function StartProcess(FileName,Parameters : String;xPos, yPos : integer) : DWORD; overload;
var
  zCurDir:array[0..255] of char;
  zParams:array[0..255] of char;
  WorkDir:String;
  StartupInfo:TStartupInfo;
  ProcessInfo:TProcessInformation;
begin
  StrPCopy(zParams,FileName + ' ' + Parameters);
  GetDir(0,WorkDir);
  StrPCopy(zCurDir,WorkDir);
  StrPCopy(zCurDir,WorkDir);
  FillChar(StartupInfo,Sizeof(StartupInfo),#0);
  With StartupInfo do begin
    if (xPos >= 0) AND (yPos >= 0) then begin
      dwFlags := STARTF_USEPOSITION;
      dwX:=xPos;
      dwY:=yPos;
    end else
      dwFlags := STARTF_USESHOWWINDOW;
    cb := Sizeof(StartupInfo);
  end;
  if CreateProcess(nil,
    zParams,                      { pointer to command line string }
    nil,                           { pointer to process security attributes }
    nil,                           { pointer to thread security attributes }
    false,                         { handle inheritance flag }
    CREATE_NEW_CONSOLE or          { creation flags }
    NORMAL_PRIORITY_CLASS,
    nil,                           { pointer to new environment block }
    nil,                           { pointer to current directory name }
    StartupInfo,                   { pointer to STARTUPINFO }
    ProcessInfo)                  { pointer to PROCESS_INF }
    then
    result:=ProcessInfo.dwProcessID;
end;

function ProcessExists(processID : dWord) : boolean;
var
  handle : dword;
begin
  handle:=OpenProcess(PROCESS_QUERY_INFORMATION,FALSE,processID);
  if (handle <> 0) AND (handle <> null) then begin  
    Result:=true;
    CloseHandle(handle);
  end else
    Result:=false;
end;

function FormatLastNameFirst(fName : string) : string;
var
  num, i : integer;
  str    : string;
begin
  if LL_StringUtils.IsInText(' ', fName, '') AND (NOT LL_StringUtils.IsInText(',', fName, '')) then begin
    num:=CharCount(' ', fName);
    for i:=1 to num+1 do begin
      str:=Piece(fName, ' ', i);
      if i = num+1 then
        Result:=str + ', ' + Trim(Result)
      else
        Result:=Result + str + ' ';
     end;
  end else
    Result:='';
end;

function FormatFirstNameFirst(fName : string) : string;
var
  num, i : integer;
  str    : string;
  suffix : string;
begin
  if IsInText(',', fName, '') then begin
    num:=CharCount(',', fName);
    if num >= 1 then begin
      for i:=1 to 2 do begin
        str:=Piece(fName, ',', i);
        if i = 2 then begin
          suffix:=Trim(Piece(Trim(str), ' ', 2));
          if (AnsiUpperCase(Trim(suffix)) <> 'JR') AND
             (AnsiUpperCase(Trim(suffix)) <> 'JR.') then
            suffix:=Trim(Piece(Trim(str), ' ', 3));
          if (Trim(str) <> Trim(suffix)) AND (Trim(suffix) <> '') then begin
            if (AnsiUpperCase(Trim(suffix)) = 'JR') OR
               (AnsiUpperCase(Trim(suffix)) = 'JR.') then
              str:=ReplaceText(str, suffix, '')
            else
              suffix:='';
          end else
            suffix:='';
          Result:=Trim(str) + ' ' + Trim(Result)
        end else begin
          Result:=str + ' ';
        end;
      end;
    end;
    Result:=Trim(Result + ' ' + suffix);
  end else
    Result:='';
end;

function CheckDate(fDate : string) : string;
begin
  fDate:=ReplaceChar(fDate, ' ', '0');
  if (fDate <> '') then begin
    if ValidDate(fDate) then begin
      result:=EnglishDate(AnsiDate(fDate));
    end else
      result:='';
  end else
    result:='';
end;

function CheckAnsiDate(fDate : string) : string;
begin
  fDate:=ReplaceChar(fDate, ' ', '0');
  if (fDate <> '') then begin
    if ValidAnsiDate(fDate) then
      result:=fDate
    else
      result:='';
  end else
    result:='';
end;

function ConvertToFileURL(fFilename : string) : string;
var
  i : integer;
begin
  result:='';
  for i:=1 to length(fFilename) do begin
    if fFilename[i] = '\' then
      Result:=Result + '/'
    else if fFilename = ' ' then
      Result:=Result + '%20'
    else
      Result:=Result + fFilename[i];
  end;
  Result:='file:///' + Result;
end;

function TruncDigits(fNumber : string;fSigDigits : integer) : string;
var
  point : boolean;
  n     : integer;
  i     : integer;
begin
  Result:='';
  point:=FALSE;
  n:=0;
  for i:=1 to length(fNumber) do begin
    point:=(fNumber[i] = '.') OR point;
    if point then begin
      if n <= fSigDigits then
        Result:=Result + fNumber[i];
      inc(n);
    end else
      Result:=Result + fNumber[i];
  end;
end;

function FormatFloatString(fNumber : string;fSigDigits : integer) : string;
var
  point : boolean;
  n     : integer;
  i     : integer;
begin
  Result:='';
  fNumber:=Trim(fNumber);
  point:=FALSE;
  n:=0;
  for i:=1 to length(fNumber) do begin
    point:=(fNumber[i] = '.') OR point;
    if point then begin
      if n <= fSigDigits then begin
        if (n = fSigDigits) then begin
          if (fNumber[i] <> '0') AND (fNumber[i] <> ' ') then
            Result:=Result + fNumber[i];
        end else
          Result:=Result + fNumber[i];
      end;
      inc(n);
    end else
      Result:=Result + fNumber[i];
  end;
  if (NOT Point) then
    Result:=Result + '.0'
  else if Point AND (n = 1) then
    Result:=Result + '0';
end;

function DeleteText(fRemove : string;fFrom : string) : string;
var
  P, S, SubStr : PChar;
  t, len       : integer;
begin
  S:=PChar(fFrom);
  SubStr:=PChar(fRemove);
  len:=Length(fRemove);
  P:=StrPos(S, SubStr);
  if P <> nil then begin
    t:=(P - S);
    Delete(fFrom, t+1, len);
    Result:=fFrom
  end else
    Result:=fFrom;
end;

function GetFoldersFromPath(fPath : string;fPathDelim : char) : TStringList;
var
  i    : integer;
  fDir : string;
begin
  Result:=TStringList.create;
  fDir:='';
  for i:=1 to length(fPath) do begin
    if (fPath[i] = fPathDelim) AND (i < length(fPath)) then begin
      Result.add(fDir);
      fDir:='';
    end else
      fDir:=fDir + fPath[i];
  end;
  Result.add(fDir);
end;

function GetOSInfo : string;
var
  fPlatform      : string;
  BuildNumber    : Integer;
  fWin32Platform : TWin32Platform;
begin
  fWin32Platform:=GetWin32Platform;
  fPlatform:=fWin32Platform.fWin32Platform;
  BuildNumber:=fWin32Platform.fBuildNumber;
  if (Win32Platform = VER_PLATFORM_WIN32_WINDOWS) or (Win32Platform = VER_PLATFORM_WIN32_NT) then begin
    if Win32CSDVersion = '' then
      Result := Format('%s %d.%d (Build %d)', [fPlatform, Win32MajorVersion, Win32MinorVersion, BuildNumber])
    else
      Result := Format('%s %d.%d (Build %d: %s)', [fPlatform, Win32MajorVersion, Win32MinorVersion, BuildNumber, Win32CSDVersion]);
  end else
    Result:=Format('%s %d.%d', [fPlatform, Win32MajorVersion, Win32MinorVersion])
end;

function GetOSInfo2 : TOSInfo;
var
  fPlatform : TWin32Platform;
begin
  Result:=TOSInfo.create;
  fPlatform:=GetWin32Platform;
  with Result do begin
    fWin32PlatformId   := fPlatform.fWin32PlatformId;
    fWin32Platform     := fPlatform.fWin32Platform;
    fBuildNumber       := fPlatform.fBuildNumber;
    fWin32MajorVersion := Win32MajorVersion;
    fWin32MinorVersion := Win32MinorVersion;
    fWin32CSDVersion   := Win32CSDVersion;
  end;
end;

function GetWin32Platform : TWin32Platform;
begin
  Result:=TWin32Platform.create;
  with Result do begin
    fWin32PlatformId:=Win32Platform;
    case Win32Platform of
      VER_PLATFORM_WIN32s : begin
        fWin32Platform := 'Windows 32 bits';
      end;
      VER_PLATFORM_WIN32_WINDOWS : begin
        case Win32MinorVersion of
          0  : begin
                 if Win32BuildNumber < 1000 then
                   fWin32Platform := 'Windows 95'
                 else if Win32BuildNumber = 1111 then
                   fWin32Platform := 'Windows 95b'
                 else if Win32BuildNumber > 1111 then
                   fWin32Platform := 'Windows 95c';
               end;
          10 : begin
                 if Win32BuildNumber < 2000 then
                   fWin32Platform := 'Windows 98'
                 else if Win32BuildNumber = 1111 then
                   fWin32Platform := 'Windows 98 Second Edition';
               end;
          90 : begin
                 fWin32Platform := 'Windows ME';
               end;
        end;
        fBuildNumber := Win32BuildNumber and $0000FFFF;
      end;
      VER_PLATFORM_WIN32_NT : begin
        if (Win32MajorVersion = 5) AND (Win32MinorVersion = 2) then
          fWin32Platform := 'Windows 2003'
        else if (Win32MajorVersion = 5) AND (Win32MinorVersion = 1) then
          fWin32Platform := 'Windows XP'
        else if (Win32MajorVersion = 4) then
          fWin32Platform := 'Windows NT'
        else if (Win32MajorVersion = 5) then
          fWin32Platform := 'Windows 2000'
        else if (Win32MajorVersion = 6) and (Win32MinorVersion = 0) then
          fWin32Platform := 'Windows Vista'
        else if (Win32MajorVersion = 7) then
          fWin32Platform := 'Windows 7';
        fBuildNumber := Win32BuildNumber;
      end else begin
        fWin32Platform := 'Windows';
        fBuildNumber := 0;
      end;
    end;
  end;
end;

// returns true if running under Windows Terminal Services
function IsRemoteSession: boolean;
const
  sm_RemoteSession = $1000;
begin
  result := GetSystemMetrics(sm_RemoteSession) <> 0;
end;

function GetWindowsUsername : string;
var
  buffer : string;
  buffSize : DWORD;
begin
  buffSize:=128;
  SetLength(buffer,BuffSize);
  GetUserName(PChar(buffer), buffSize);
  Result := Copy(buffer, 1, buffSize - 1)
end;

function GetMemoryInfo : TMemoryInfo;
var
  fMemoryStatus : MemoryStatus;
begin
  fMemoryStatus.dwLength := SizeOf(fMemoryStatus);
  GlobalMemoryStatus(fMemoryStatus);
  Result:=TMemoryInfo.create;
  with Result, fMemoryStatus do begin
    // Per-Cent of Memory in use by your system
    Result.MemoryLoad:=dwMemoryLoad;
    // The amount of Total Physical memory allocated to your system.
    Result.TotalPhys:=dwTotalPhys;
    // The amount available of physical memory in your system.
    Result.AvailPhys:=dwAvailPhys;
    // The amount of Total Bytes allocated to your page file.
    Result.TotalPageFile:=dwTotalPageFile;
    // The amount of available bytes in your page file.
    Result.AvailPageFile:=dwAvailPageFile;
    // The amount of Total bytes allocated to this program
    // (generally 2 gigabytes of virtual space).
    Result.TotalVirtual:=dwTotalVirtual;
    // The amount of avalable bytes that is left to your program to use.
    Result.AvailVirtual:=dwAvailVirtual;
  end;
end;

(*
function GetMachineName : String;
var
  s           : array[0..128] of char;
  p           : PHostEnt;
begin
  // Get the computer name
  GetHostName(@s, 128);
  p:=GetHostByName(@s);
  Result:=p^.h_Name;
end;
*)

function GetMachineName: string;
var
  buffer: array[0..255] of char;
  size: dword;
begin
  size := 256;
  if GetComputerName(buffer, size) then
    Result := buffer
  else
    Result := ''
end;

function GetMachineIP : string;
var
  s          : array[0..128] of char;
  p          : PHostEnt;
  c          : PAnsiChar;
begin
  //Get the IP Address
  GetHostName(@s, 128);
  p:=GetHostByName(@s);
  c:=iNet_ntoa(PInAddr(p^.h_addr_list^)^);
  Result:=c;
end;

(*
function GetMachineIP : string;
type 
  Name = array[0..100] of Char; 
  PName = ^Name; 
var 
  HEnt: pHostEnt; 
  HName: PName; 
  WSAData: TWSAData; 
  i: Integer;
  HostName : string;
begin 
  Result := '';
  if WSAStartup($0101, WSAData) <> 0 then begin 
    Result := 'Winsock is not responding."';
    Exit; 
  end; 
  New(HName);
  if GetHostName(HName^, SizeOf(Name)) = 0 then
  begin 
    HostName := StrPas(HName^);
    HEnt := GetHostByName(HName^);
    for i := 0 to HEnt^.h_length - 1 do
      Result := Concat(Result, IntToStr(Ord(HEnt^.h_addr_list^[i])) + '.');
    SetLength(Result, Length(Result) - 1);
  end;
  Dispose(HName);
  WSACleanup; 
end;
*)

function GetTempDir : string;
var
  buf      : array[0..1023] of Char;
begin
  SetString(Result, buf, GetTempPath(Sizeof(buf)-1, buf));
end;

function GetShellFolder(fName : string) : string;
var
  i           : integer;
  reg         : TRegistry;
  ts          : TStrings;
begin
  Result:='';
  reg := TRegistry.Create;
  reg.RootKey := HKEY_CURRENT_USER;
  reg.LazyWrite := false;
  reg.OpenKey('Software\Microsoft\Windows\CurrentVersion\Explorer\Shell Folders',false);
  ts:=TStringList.Create;
  reg.GetValueNames(ts);
  i:=0;
  while (i < ts.count) AND (ts.strings[i] <> fName) do
    inc(i);
  if i < ts.count then
    Result:=reg.ReadString(ts.Strings[i]);
  ts.Free;
  reg.CloseKey;
  reg.free;
end;

procedure ExecBrowser(fURL : string);
begin
  ShellExecute(Application.Handle, 'open', pChar(fUrl), nil, nil, SW_SHOW);
end;

function GetIEVersion : string;
var 
  Reg: TRegistry; 
begin 
  Reg := TRegistry.Create;
  try 
    Reg.RootKey := HKEY_LOCAL_MACHINE; 
    Reg.OpenKey('Software\Microsoft\Internet Explorer', False); 
    try 
      Result := Reg.ReadString('Version');
    except 
      Result := '';
    end; 
    Reg.CloseKey; 
  finally 
    Reg.Free;
  end; 
end; 

procedure DeleteIECache;
var
  lpEntryInfo : PInternetCacheEntryInfo;
  hCacheDir   : LongWord;
  dwEntrySize : LongWord;
begin
  dwEntrySize := 0;
  FindFirstUrlCacheEntry(nil, TInternetCacheEntryInfo(nil^), dwEntrySize);
  GetMem(lpEntryInfo, dwEntrySize);
  if dwEntrySize > 0 then lpEntryInfo^.dwStructSize := dwEntrySize;
  hCacheDir := FindFirstUrlCacheEntry(nil, lpEntryInfo^, dwEntrySize);
  if hCacheDir <> 0 then
  begin
    repeat
      DeleteUrlCacheEntry(lpEntryInfo^.lpszSourceUrlName);
      FreeMem(lpEntryInfo, dwEntrySize);
      dwEntrySize := 0;
      FindNextUrlCacheEntry(hCacheDir, TInternetCacheEntryInfo(nil^), dwEntrySize);
      GetMem(lpEntryInfo, dwEntrySize);
      if dwEntrySize > 0 then lpEntryInfo^.dwStructSize := dwEntrySize;
    until not FindNextUrlCacheEntry(hCacheDir, lpEntryInfo^, dwEntrySize);
  end;
  FreeMem(lpEntryInfo, dwEntrySize);
  FindCloseUrlCache(hCacheDir);
end;

function GetInternetConnectionType : Word;
var
  flags : DWORD;
  state : LongBool;
begin 
  state := InternetGetConnectedState(@flags, 0);
  if state then begin
    if (flags and INTERNET_CONNECTION_MODEM) = INTERNET_CONNECTION_MODEM then
      Result:=INTERNET_CONNECTION_MODEM;
    if (flags and INTERNET_CONNECTION_LAN) = INTERNET_CONNECTION_LAN then
      Result:=INTERNET_CONNECTION_LAN;
    if (flags and INTERNET_CONNECTION_PROXY) = INTERNET_CONNECTION_PROXY then
      Result:=INTERNET_CONNECTION_PROXY;
    if (flags and INTERNET_CONNECTION_MODEM_BUSY) = INTERNET_CONNECTION_MODEM_BUSY then
      Result:=INTERNET_CONNECTION_MODEM_BUSY;
  end else
    Result:=0;
end;

function DateTimeToFileTime(ADateTime: TDateTime): int64;
var SysTime : _SystemTime;
    FTime : _FileTime;
begin
  DateTimeToSystemTime(ADateTime, SysTime);
  SystemTimeToFileTime(SysTime, FTime);
  LocalFileTimeToFileTime(FTime, FTime);
  Result := int64(FTime);
end;

function CreateTimer : THandle;
begin
  Result := CreateWaitableTimer(nil, false, 'WaitClock');
end;

procedure StartTimer(fHandle: THandle;ADateTime: TDateTime);
var
  T64: Int64;
begin
  T64 := DateTimeToFileTime(ADateTime);
  SetWaitableTimer(fHandle, T64, 0, nil, nil, false);
  WaitForSingleObjectEx(fHandle, Infinite, false);
end;

procedure ResetTimer(fHandle: THandle);
var
  T64: Int64;
begin
  T64 := DateTimeToFileTime(NOW);
  SetWaitableTimer(fHandle, T64, 0, nil, nil, false);
end;

function CancelTimer(fHandle : THandle) : boolean;
begin
  Result := CancelWaitableTimer(fHandle);
  CloseHandle(fHandle);
end;

function WaitTill(ADateTime: TDateTime) : THandle;
var
  T64: Int64;
begin
  Result := CreateWaitableTimer(nil, false, 'WaitClock');
  T64 := DateTimeToFileTime(ADateTime);
  SetWaitableTimer(Result, T64, 0, nil, nil, false);
  WaitForSingleObjectEx(Result, Infinite, false);
  CloseHandle(Result);
end;

// Get a list of services
//
// return TRUE if successful
//
// sMachine:
//   machine name, ie: \SERVER
//   empty = local machine
//
// dwServiceType
//   SERVICE_WIN32,
//   SERVICE_DRIVER or
//   SERVICE_TYPE_ALL
//
// dwServiceState
//   SERVICE_ACTIVE,
//   SERVICE_INACTIVE or
//   SERVICE_STATE_ALL
//
// slServicesList
//   TStrings variable to storage
//
function GetServiceList(sMachine : string; dwServiceType,
                        dwServiceState : DWord; slServicesList : TStrings) : boolean;
const
  // assume that the total number of
  // services is less than 4096.
  // increase if necessary
  cnMaxServices = 4096;

type
  TSvcA = array[0..cnMaxServices] of TEnumServiceStatus;
  PSvcA = ^TSvcA;

var
  // temp. use
  j : integer;

  // service control
  // manager handle
  schm          : SC_Handle;

  // bytes needed for the
  // next buffer, if any
  nBytesNeeded,

  // number of services
  nServices,

  //
  // pointer to the
  // next unread service entry
  nResumeHandle : DWord;

  //
  // service status array
  ssa : PSvcA;
begin
  Result := false;

  // connect to the service
  // control manager
  schm := OpenSCManager(PChar(sMachine), Nil, SC_MANAGER_ALL_ACCESS);

  // if successful...
  if(schm > 0)then begin
    nResumeHandle := 0;

    New(ssa);

    EnumServicesStatus(schm, dwServiceType, dwServiceState, ssa^[0], SizeOf(ssa^),
                       nBytesNeeded, nServices, nResumeHandle);

    // assume that our initial array
    // was large enough to hold all
    // entries. add code to enumerate
    // if necessary.
    for j := 0 to nServices-1 do begin
      slServicesList.Add( StrPas(ssa^[j].lpDisplayName ) );
    end;

    Result := true;

    Dispose(ssa);

    // close service control
    // manager handle
    CloseServiceHandle(schm);
  end;
end;

// get service status
//
// return status code if successful
// -1 if not
//
// return codes:
//   SERVICE_STOPPED
//   SERVICE_RUNNING
//   SERVICE_PAUSED
//
// following return codes
// are used to indicate that
// the service is in the
// middle of getting to one
// of the above states:
//   SERVICE_START_PENDING
//   SERVICE_STOP_PENDING
//   SERVICE_CONTINUE_PENDING
//   SERVICE_PAUSE_PENDING
//
// sMachine:
//   machine name, ie: \SERVER
//   empty = local machine
//
// sService
//   service name, ie: Alerter
//
function ServiceGetStatus(sMachine, sService : string ) : DWord;
var
  //
  // service control
  // manager handle
  schm,
  //
  // service handle
  schs   : SC_Handle;
  //
  // service status
  ss     : TServiceStatus;
  //
  // current service status
  dwStat : DWord;
begin
  dwStat := 0;

  // connect to the service
  // control manager 
  schm := OpenSCManager(PChar(sMachine), Nil, SC_MANAGER_CONNECT);

  // if successful...
  if(schm > 0)then begin
    // open a handle to
    // the specified service
    schs := OpenService(schm, PChar(sService), SERVICE_QUERY_STATUS);

    // if successful...
    if(schs > 0)then begin
      // retrieve the current status
      // of the specified service
      if(QueryServiceStatus(schs,ss)) then begin
        dwStat := ss.dwCurrentState;
      end;

      // close service handle
      CloseServiceHandle(schs);
    end;

    // close service control
    // manager handle
    CloseServiceHandle(schm);
  end;

  Result := dwStat;
end;

// return TRUE if the specified
// service is running, defined by
// the status code SERVICE_RUNNING.
// return FALSE if the service
// is in any other state, including
// any pending states
//
function ServiceRunning(sMachine, sService : string ) : boolean;
begin
  Result := SERVICE_RUNNING = ServiceGetStatus(sMachine, sService);
end;

// return TRUE if the specified
// service was stopped, defined by
// the status code SERVICE_STOPPED.
//
function ServiceStopped(sMachine, sService : string ) : boolean;
begin
  Result := SERVICE_STOPPED = ServiceGetStatus(sMachine, sService );
end;

// start service
//
// return TRUE if successful
//
// sMachine:
//   machine name, ie: \SERVER
//   empty = local machine
//
// sService
//   service name, ie: Alerter
function ServiceStart(sMachine, sService : string ) : boolean;
var
  //
  // service control
  // manager handle
  schm,
  //
  // service handle
  schs   : SC_Handle;
  //
  // service status
  ss     : TServiceStatus;
  //
  // temp char pointer
  psTemp : PChar;
  //
  // check point
  dwChkP : DWord;
begin
  ss.dwCurrentState := 0;
  
  // connect to the service
  // control manager
  schm := OpenSCManager(PChar(sMachine), Nil, SC_MANAGER_CONNECT);

  // if successful...
  if(schm > 0)then begin
    // open a handle to
    // the specified service
    schs := OpenService(schm, PChar(sService), SERVICE_START or SERVICE_QUERY_STATUS);

    // if successful...
    if(schs > 0)then begin
      psTemp := Nil;
      if(StartService(schs, 0, psTemp)) then begin
        // check status
        if(QueryServiceStatus(schs, ss))then begin
          while(SERVICE_RUNNING <> ss.dwCurrentState) do begin
            //
            // dwCheckPoint contains a
            // value that the service
            // increments periodically
            // to report its progress
            // during a lengthy
            // operation.
            //
            // save current value
            //
            dwChkP := ss.dwCheckPoint;

            //
            // wait a bit before
            // checking status again
            //
            // dwWaitHint is the
            // estimated amount of time
            // the calling program
            // should wait before calling
            // QueryServiceStatus() again
            //
            // idle events should be
            // handled here...
            //
            Sleep(ss.dwWaitHint);

            if(not QueryServiceStatus(schs, ss)) then begin
              // couldn't check status
              // break from the loop
              break;
            end;

            if(ss.dwCheckPoint < dwChkP) then begin
              // QueryServiceStatus
              // didn't increment
              // dwCheckPoint as it
              // should have.
              // avoid an infinite
              // loop by breaking
              break;
            end;
          end;
        end;
      end;

      // close service handle
      CloseServiceHandle(schs);
    end;

    // close service control
    // manager handle
    CloseServiceHandle(schm);
  end;

  // return TRUE if
  // the service status is running
  Result := SERVICE_RUNNING = ss.dwCurrentState;
end;

// stop service
//
// return TRUE if successful
//
// sMachine:
//   machine name, ie: \SERVER
//   empty = local machine
//
// sService
//   service name, ie: Alerter
//
function ServiceStop(sMachine, sService : string ) : boolean;
var
  //
  // service control
  // manager handle
  schm,
  //
  // service handle
  schs   : SC_Handle;
  //
  // service status
  ss     : TServiceStatus;
  //
  // check point
  dwChkP : DWord;
begin
  // connect to the service
  // control manager
  schm := OpenSCManager(PChar(sMachine), Nil, SC_MANAGER_CONNECT);

  // if successful...
  if(schm > 0)then begin
    // open a handle to
    // the specified service
    schs := OpenService(schm, PChar(sService), SERVICE_STOP or SERVICE_QUERY_STATUS);

    // if successful...
    if(schs > 0)then begin
      if(ControlService(schs, SERVICE_CONTROL_STOP, ss)) then begin
        // check status
        if(QueryServiceStatus(schs, ss)) then begin
          while(SERVICE_STOPPED <> ss.dwCurrentState) do begin
            //
            // dwCheckPoint contains a
            // value that the service
            // increments periodically
            // to report its progress
            // during a lengthy
            // operation.
            //
            // save current value
            //
            dwChkP := ss.dwCheckPoint;

            //
            // wait a bit before
            // checking status again
            //
            // dwWaitHint is the
            // estimated amount of time
            // the calling program
            // should wait before calling
            // QueryServiceStatus() again
            //
            // idle events should be
            // handled here...
            //
            Sleep(ss.dwWaitHint);

            if(not QueryServiceStatus(schs, ss)) then begin
              // couldn't check status
              // break from the loop
              break;
            end;

            if(ss.dwCheckPoint < dwChkP) then begin
              // QueryServiceStatus
              // didn't increment
              // dwCheckPoint as it
              // should have.
              // avoid an infinite
              // loop by breaking
              break;
            end;
          end;
        end;
      end;

      // close service handle
      CloseServiceHandle(schs);
    end;

    // close service control
    // manager handle
    CloseServiceHandle(schm);
  end;

  // return TRUE if
  // the service status is stopped
  Result := SERVICE_STOPPED = ss.dwCurrentState;
end;

function GetEnvVar(fName : string) : string;
var
  pc1, pc2  : PAnsiChar;
begin
  pc1 := StrAlloc( Length(fName)+1 );
  pc2 := StrAlloc( 250 + 1 );
  StrPCopy( pc1, fName );
  GetEnvironmentVariableA(pc1, pc2, 250 );
  Result := StrPas( pc2 );
  StrDispose( pc1 );
  StrDispose( pc2 );
end;

function GetAppVersion(AppName : string): String;
const
  InfoNum = 10;
  InfoStr: array[1..InfoNum] of string = ('CompanyName', 'FileDescription', 'FileVersion',
                                          'InternalName', 'LegalCopyright', 'LegalTradeMarks',
                                          'OriginalFileName', 'ProductName', 'ProductVersion', 'Comments');
var
  n, Len, i : DWORD;
  Buf       : PChar;
  Value     : PChar;
begin
  n := GetFileVersionInfoSize(PChar(AppName), n); // the return value is the size in bytes of the file's version information.
  Result := '';
  if n > 0 then begin
    Buf := AllocMem(n); // allocate the needed amount of memory into the buffer
    GetFileVersionInfo(PChar(AppName), 0, n, Buf); // store the file version information in the memory buffer. it stores all of the values listed in the InfoStr array.
    for i := 1 to InfoNum do
      if VerQueryValue(Buf, PChar('StringFileInfo\040904E4\' + InfoStr[i]), Pointer(Value), Len) then
        if trim(lowerCase(InfoStr[i])) = 'fileversion' then
          Result := Value;// loop through each value until we find the "FileVersion" information.
    FreeMem(Buf, n); // free the memory we stored in the buffer.
  end;
end;

function GetRegistryValue(Path, Key: string): string;
begin
  Result:=GetRegistryValue(HKEY_LOCAL_MACHINE, Path, Key);
end;

function GetRegistryValue(Root : cardinal;Path, Key: string): string;
var
  Registry: TRegistry;
begin
  Registry := TRegistry.Create(KEY_READ);
  try
    Registry.RootKey:=Root;
    // False because we do not want to create it if it doesn't exist
    Registry.OpenKey(Path, False);
    Result := Registry.ReadString(Key);
  finally
    Registry.Free;
  end;
end;

function FindWindowEx(fTitle: string): HWND;
begin
  Result:=FindWindowEx(fTitle, FALSE);
end;

function FindWindowEx(fTitle: string; fCaseSensitive : boolean): HWND;
var
  hWndTemp   : hWnd;
  iLenText   : Integer;
  cTitletemp : array [0..254] of Char;
  sTitleTemp : string;
begin
  hWndTemp := FindWindow(nil, nil);
  while hWndTemp <> 0 do begin
    iLenText := GetWindowText(hWndTemp, cTitletemp, 255);
    sTitleTemp := cTitletemp;
    if fCaseSensitive then begin
      sTitleTemp := copy( sTitleTemp, 1, iLenText);
    end else begin
      sTitleTemp := UpperCase(copy( sTitleTemp, 1, iLenText));
      fTitle := UpperCase(fTitle);
    end;
    if pos( fTitle, sTitleTemp ) <> 0 then
      Break;
    hWndTemp := GetWindow(hWndTemp, GW_HWNDNEXT);
  end;
  result := hWndTemp;
end;

function FindWindows(fTitle: string; fCaseSensitive : boolean): HWNDArr;
var
  i          : integer;
  hWndTemp   : hWnd;
  iLenText   : Integer;
  cTitletemp : array [0..254] of Char;
  sTitleTemp : string;
  fHWNDArr   : HWNDArr;
begin
  i:=0;
  SetLength(fHWNDArr, 256);
  hWndTemp := FindWindow(nil, nil);
  while hWndTemp <> 0 do begin
    iLenText := GetWindowText(hWndTemp, cTitletemp, 255);
    sTitleTemp := cTitletemp;
    if fCaseSensitive then begin
      sTitleTemp := copy( sTitleTemp, 1, iLenText);
    end else begin
      sTitleTemp := UpperCase(copy( sTitleTemp, 1, iLenText));
      fTitle := UpperCase(fTitle);
    end;
    if pos( fTitle, sTitleTemp ) <> 0 then begin
      fHWNDArr[i]:=hWndTemp;
      Inc(i);
    end;
    hWndTemp := GetWindow(hWndTemp, GW_HWNDNEXT);
  end;
  SetLength(fHWNDArr, i);
  result := fHWNDArr;
end;

end.

