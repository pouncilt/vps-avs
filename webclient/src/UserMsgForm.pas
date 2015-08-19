(* ****************************************************************************)
 //  Date Created: Jan 25, 2012
 //  Site Name: Loma Linda VAMC
 //  Developers: Rob Durkin
 //  Description: R1 OR Schedule Viewer
 //  Note: This unit requires R1SRLOR in order to run.
(******************************************************************************)
unit UserMsgForm;

interface

uses
  Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  ExtCtrls, StdCtrls, ComCtrls, Buttons, Menus;

type
  TUserMsg = class(TForm)
    LeftSidePanel: TPanel;
    TopSidePanel: TPanel;
    BtnPanel: TPanel;
    RightSidePanel: TPanel;
    CenterPanel: TPanel;
    MessageMemo: TRichEdit;
    image: TImage;
    MemoPopUpMenu: TPopupMenu;
    CopyPopUpMenu: TMenuItem;
    SelectAllPopUpMenu: TMenuItem;
    procedure CopyBtnClick(Sender: TObject);
    procedure SelectAllBtnClick(Sender: TObject);
    procedure ButtonClick(Sender: TObject);
  private
    fReturnValue : integer;
    procedure Init(const fCaption : string; const fMsg : string;const fIcon : integer);
    function CreateBtn(fLeft : integer;fCaption : string;fWidth : integer;
                       fReturnType : integer) : TButton;
  public
    function ShowMessage(const fCaption : string; const fMsg : string;
                         const fIcon : integer; const fBtns : integer) : integer;              overload;
    function ShowMessage(const fCaption : string; const fMsg : string;
                         const fIcon : integer; const fCaptions : Array of String) : integer;  overload;
    procedure SetFont(const fName : string;const fSize : integer);
  end;

var
  UserMsg: TUserMsg;

const
  // Return types
  RET_OK            = 1;
  RET_YES           = 2;
  RET_NO            = 3;
  RET_CANCEL        = 4;
  // Icons
  ICON_NONE        = 0;
  ICON_APPLICATION = 1;
  ICON_ERROR       = 2;
  ICON_QUESTION    = 3;
  ICON_INFORMATION = 4;
  ICON_WARNING     = 5;
  ICON_WINLOGO     = 6;
  // Buttons
  BTN_CLOSE         = 1;
  BTN_OK            = 2;
  BTN_OK_CANCEL     = 3;
  BTN_YES_NO        = 4;
  BTN_YES_NO_CANCEL = 5;

implementation

{$R *.DFM}

function TUserMsg.ShowMessage(const fCaption : string; const fMsg : string;
                              const fIcon : integer; const fBtns : integer) : integer;
var
  fBtnList  : TList;
  i         : integer;
begin
  Init(fCaption, fMsg, fIcon);
  fBtnList:=TList.create;
  if fBtns = BTN_OK then begin
    fBtnList.add(CreateBtn(143, '&Ok', 77, RET_OK));
  end else if fBtns = BTN_OK_CANCEL then begin
    fBtnList.add(CreateBtn(103, '&Ok', 77, RET_OK));
    fBtnList.add(CreateBtn(185, '&Cancel', 77, RET_CANCEL));
  end else if fBtns = BTN_YES_NO then begin
    fBtnList.add(CreateBtn(103, '&Yes', 77, RET_YES));
    fBtnList.add(CreateBtn(185, '&No', 77, RET_NO));
  end else if fBtns = BTN_YES_NO_CANCEL then begin
    fBtnList.add(CreateBtn(61, '&Yes', 77, RET_YES));
    fBtnList.add(CreateBtn(143, '&No', 77, RET_NO));
    fBtnList.add(CreateBtn(225, '&Cancel', 77, RET_CANCEL));
  end else begin
    fBtnList.add(CreateBtn(143, '&Close', 77, 0));
  end;
  ShowModal;
  for i:=0 to fBtnList.Count-1 do
    TButton(fBtnList.Items[i]).Free;
  fBtnList.free;
  Result:=fReturnValue;
end;

function TUserMsg.ShowMessage(const fCaption : string; const fMsg : string;
                              const fIcon : integer; const fCaptions : Array of String) : integer;
var
  i         : integer;
  fBtnList  : TList;
  fPosArray : Array of integer;
begin
  Init(fCaption, fMsg, fIcon);
  fBtnList:=TList.create;
  SetLength(fPosArray, High(fCaptions)+1);
  case (High(fPosArray)+1) of
    1 : fPosArray[0]:=132;
    2 : begin
          fPosArray[0]:=77;
          fPosArray[1]:=186;
        end;
    3 : begin
          fPosArray[0]:=26;
          fPosArray[1]:=132;
          fPosArray[2]:=238;
        end;
  end;
  for i:=0 to High(fCaptions) do begin
    fBtnList.add(CreateBtn(fPosArray[i], fCaptions[i], 100, i));
  end;
  ShowModal;
  for i:=0 to fBtnList.Count-1 do
    TButton(fBtnList.Items[i]).Free;
  fBtnList.free;
  Result:=fReturnValue;
end;

procedure TUserMsg.Init(const fCaption : string; const fMsg : string;const fIcon : integer);
var
  fIconName : pAnsiChar;
begin
  Caption:=fCaption;
  MessageMemo.Lines.Text:=fMsg;
  if fIcon = ICON_APPLICATION then
    fIconName:=IDI_APPLICATION
  else if fIcon = ICON_ERROR then
    fIconName:=IDI_HAND
  else if fIcon = ICON_QUESTION then
    fIconName:=IDI_QUESTION
  else if fIcon = ICON_INFORMATION then
    fIconName:=IDI_INFORMATION
  else if fIcon = ICON_WARNING then
    fIconName:=IDI_EXCLAMATION
  else if fIcon = ICON_WINLOGO then
    fIconName:=IDI_WINLOGO
  else
    fIconName:='';
  if fIcon > ICON_NONE then
    image.Picture.Icon.Handle := LoadIcon(0, fIconName)
  else
    image.Picture:=nil;
end;

procedure TUserMsg.SetFont(const fName : string;const fSize : integer);
begin
  self.Font.Name:=fName;
  self.Font.Size:=fSize;
end;

procedure TUserMsg.CopyBtnClick(Sender: TObject);
begin
  SetFocus;
  MessageMemo.SetFocus;
  MessageMemo.CopyToClipboard;
end;

procedure TUserMsg.SelectAllBtnClick(Sender: TObject);
begin
  SetFocus;
  MessageMemo.SetFocus;
  MessageMemo.SelectAll;
end;

procedure TUserMsg.ButtonClick(Sender: TObject);
begin
  fReturnValue:=TButton(Sender).Tag;
  ModalResult:=mrOk;
end;

function TUserMsg.CreateBtn(fLeft : integer;fCaption : string;fWidth : integer;
                            fReturnType : integer) : TButton;
begin
  Result:=TButton.create(self);
  with Result do begin
    Parent:=BtnPanel;
    Caption:=fCaption;
    Top:=7;
    Height:=25;
    Width:=fWidth;
    Left:=fLeft;
    Tag:=fReturnType;
    OnClick:=ButtonClick;
  end;
end;

end.
