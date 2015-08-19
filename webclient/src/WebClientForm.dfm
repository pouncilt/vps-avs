object WebClientView: TWebClientView
  Left = 530
  Top = 333
  Caption = 'After Visit Summary'
  ClientHeight = 237
  ClientWidth = 421
  Color = clBtnFace
  DefaultMonitor = dmDesktop
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  Menu = MainMenu1
  OldCreateOrder = False
  PopupMode = pmAuto
  Position = poDesigned
  Scaled = False
  OnClose = FormClose
  OnCreate = FormCreate
  OnDestroy = FormDestroy
  OnResize = FormResize
  PixelsPerInch = 96
  TextHeight = 13
  object Panel1: TPanel
    AlignWithMargins = True
    Left = 3
    Top = 3
    Width = 415
    Height = 231
    Align = alClient
    AutoSize = True
    BevelOuter = bvNone
    Caption = 'After Vist Summary'
    TabOrder = 0
  end
  object MainMenu1: TMainMenu
    Left = 112
    Top = 80
    object FileMenuItem: TMenuItem
      Caption = '&File'
      object Goto1: TMenuItem
        Caption = '&Go to'
        OnClick = GoToMenuItemClick
      end
      object Reset1: TMenuItem
        Caption = '&Reset'
        OnClick = RefreshMenuItemClick
      end
      object N1: TMenuItem
        Caption = '-'
      end
      object ExitMenuItem: TMenuItem
        Caption = 'E&xit'
        OnClick = ExitMenuItemClick
      end
    end
  end
end
