object UserMsg: TUserMsg
  Left = 500
  Top = 131
  BorderIcons = []
  BorderStyle = bsDialog
  ClientHeight = 166
  ClientWidth = 364
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  FormStyle = fsStayOnTop
  OldCreateOrder = False
  Position = poScreenCenter
  PixelsPerInch = 96
  TextHeight = 13
  object LeftSidePanel: TPanel
    Left = 0
    Top = 15
    Width = 60
    Height = 115
    Align = alLeft
    BevelOuter = bvNone
    TabOrder = 0
    object image: TImage
      Left = 16
      Top = 0
      Width = 32
      Height = 32
    end
  end
  object TopSidePanel: TPanel
    Left = 0
    Top = 0
    Width = 364
    Height = 15
    Align = alTop
    BevelOuter = bvNone
    TabOrder = 1
  end
  object BtnPanel: TPanel
    Left = 0
    Top = 130
    Width = 364
    Height = 36
    Align = alBottom
    BevelOuter = bvNone
    TabOrder = 2
  end
  object RightSidePanel: TPanel
    Left = 342
    Top = 15
    Width = 22
    Height = 115
    Align = alRight
    BevelOuter = bvNone
    TabOrder = 3
  end
  object CenterPanel: TPanel
    Left = 60
    Top = 15
    Width = 282
    Height = 115
    Align = alClient
    BevelOuter = bvNone
    TabOrder = 4
    object MessageMemo: TRichEdit
      Left = 0
      Top = 0
      Width = 282
      Height = 115
      Align = alClient
      BevelInner = bvNone
      BevelOuter = bvNone
      BorderStyle = bsNone
      Color = clBtnFace
      ReadOnly = True
      ScrollBars = ssVertical
      TabOrder = 0
    end
  end
  object MemoPopUpMenu: TPopupMenu
    Left = 11
    Top = 57
    object CopyPopUpMenu: TMenuItem
      Caption = 'C&opy'
      OnClick = CopyBtnClick
    end
    object SelectAllPopUpMenu: TMenuItem
      Caption = '&Select All'
      OnClick = SelectAllBtnClick
    end
  end
end
