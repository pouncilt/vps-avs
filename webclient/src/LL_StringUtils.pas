(* ****************************************************************************)
 //  Date Created: Jan 25, 2012
 //  Site Name: Loma Linda VAMC
 //  Developers: Rob Durkin
 //  Description: R1 OR Schedule Viewer
 //  Note: This unit requires R1SRLOR in order to run.
(******************************************************************************)
unit LL_StringUtils;

interface

uses
  // Standard
  SysUtils, Classes, StdCtrls, Math, Windows;

Type
  TIniSettings = class
    Sections : TStringList;
    procedure Reset;
    procedure SetText(fText : String);
    function GetText : String;
    function GetValue(fSection, fKey : string) : string;
    procedure SetValue(fSection, fKey, fValue : string);
    procedure AddKeyValue(fSection, fKey, fValue : string);
    procedure InsertKeyValue(fSection, fKey, fValue : string;fIndex : integer);
    procedure DeleteKey(fSection, fKey : string); overload;
    procedure DeleteKey(fSection : string; fIndex : integer);  overload;
    function AddSection(fSection : string) : integer;
    function GetSection(fSection : string) : TStringList;
  end;

// STRING SEARCH FUNCTIONS
function IsInText(str1,str2 : string;fExcludeText : string) : boolean;
function IsInList(str : string;list : TStringList) : boolean;
function CharCount(ch : char;str : string) : integer;
function CharAt(const x: string; APos: Integer): Char;

// STRING FORMATTING FUNCTIONS
function MixedCase(const x: string): string;
procedure MixedCaseList(var fList: TStrings);
function FormatStringForLabel(fStr : String) : String;
function FormatSSN(const ssn : string) : string; overload
function FormatSSN(const ssn : string;delim : char) : string; overload;
function StripSSN(const ssn : string) : string;
function IsLast5(x: string): Boolean;
function IsFullSSN(x: string): Boolean;
function IsSSN(const x: string): Boolean;
function FilterText(x : string) : string;
function FilterCntlChrs(x : string) : string;
function DeleteCntlChrs(x : string) : string;
function FilterCRNL(x : string) : string;
function FilterNL(x : string) : string;
function DeleteCRNL(x : string) : string;
function DeleteTab(x : string) : string;
function ConvertToCRNL(x : string) : string;
function FormatCRNL(x : string) : string;
function EncodeURL(x : String) : string;
function ConvertToBR(x : string) : string;
function RemoveLeadingCntlChrs(x : string) : string;
function RemoveTrailingCntlChrs(x : string) : string;
function RemoveTrailingChar(x : string;c : char) : string;
function DeleteChar(x : string;c : char) : string;
function ValidInteger(d : string) : boolean;
function ValidFloat(d : string) : boolean;
function GetFloatFromString(fStr : String) : Double;
function GetIntFromString(fStr : String) : Integer;

// MISC STRING FUNCTIONS
function RandomString : string;
function GetRandomNumber(fLen : integer) : Integer;
function GetFirstName(fullname : string) : string;
function GetStateName(fCode : string) : string;
function GetStateCode(fState : string) : string;

// PIECE FUNCTIONS
function Piece(text : string;field : integer) : string;              overload;
function Piece(text : string;delim : char;field : integer) : string; overload;
function Pieces(text : string;delim: char;firstNum, lastNum: Integer): string;
function NumPieces(text : string;delim : char) : integer;
function ComparePieces(P1, P2: string; Pieces: array of integer;CaseInsensitive: boolean = FALSE): integer;
function SortByPieces(fList : TStringList;Pieces: array of integer) : TStringList;
function SortByPiece(fList: TStringList;PieceNum: Integer) : TStringList;
procedure SetPiece(var x: string;PieceNum: Integer; NewPiece: string);   overload;
procedure SetPiece(var x: string;Delim : char;PieceNum: Integer; const NewPiece: string);   overload;
procedure SetPieces(var x: string;Pieces: Array of Integer;FromString: string); overload;
procedure SetPieces(var x: string;Delim : char;Pieces: Array of Integer;FromString: string); overload;
function GetListPieces(Pieces: Array of Integer;FromList: TStrings) : TStrings;
function SubItem(text : string;field : integer;delim : char) : string;
function PieceList(list : TStringList;field : integer) : TStringList;
function PiecesToList(const x : string;delim : char) : TStringList;
function ListToPieces(fList : TStringList; fDelim : char) : string;
function InvertStringList(fList: TStringList) : TStringList;
procedure SetListPiece(AList: TStringList; ADelim: Char; PieceNum: Integer; AValue : String);

function NumberCommaDelimitedStrings(strng : string) : string;
function ParseString(LongString : String;delimiter : WideChar;AddNums : Boolean) : TStringList;
function RemoveTrailingChars(strng : string;c : char) : string;
function TrimString(strings : string) : string;
function ReturnCommaDelimString(fText : string) : string;
function CommaDelimitedStringToList(strng : string) : string;
function DelimStringToList(strng : string;delim : char) : TStringList;
function StringBlocks(fStr : string;fBlockSize : integer) : TStringList;
function GetElement(strng : string;n : integer) : string;
function WrapLines(fstrings : string;maxlen : integer) : string;
function BoolToStr(fBool : boolean) : string; overload;
function BoolToStr(fBool : boolean;fStrArray : array of string) : string; overload;
function StrToBool(fStr : string) : boolean; overload;
function BooleanToString(bool : boolean;fStrings : array of string) : string;
function StringToBoolean(fStr : string;fStrings : array of string) : boolean;
function SubString(text : string;n : integer) : string;
function GetSubstring(data : string;n : integer) : string; overload;
function GetSubstring(data : string;start,stop : integer) : string; overload;
function GetSubStringIndex(substring : string;stringlist : TStringList) : integer;
function RemoveCharFromText(text : string;c : char) : string;
function ReplaceChar(text : string;oldChar : char;newChar : char) : string;
function ReplaceText(text : string;oldChar : char;newText : string) : string; overload;
function ReplaceText(text : string;oldText : string;newText : string) : string; overload;
function ReplaceText(text : string;oldText : string;newChar : char) : string;  overload;
function DeleteCharInText(text : string;c : char) : string;

function FormatLastNameFirst(fName : string) : string;
function FormatFirstNameFirst(fName : string) : string;
function TruncDigits(fNumber : string;fSigDigits : integer) : string;
function FormatFloatString(fNumber : string;fSigDigits : integer) : string;
function DeleteText(fRemove : string;fFrom : string) : string;

const
  { names of US states }
  STATE_CODES: array[1..51] of string[2] =
    ('AK','AL','AR','AZ','CA','CO','CT','DC','DE','FL','GA','HI','IA','ID','IL','IN',
     'KS','KY','LA','MA','MD','ME','MI','MN','MO','MS','MT','NC','ND','NE','NH','NJ',
     'NM','NV','NY','OH','OK','OR','PA','RI','SC','SD','TN','TX','UT','VA','VT','WA',
     'WI','WV','WY');
  STATE_NAMES: array[1..51] of string[20] =
    ('Alaska','Alabama','Arkansas','Arizona','California','Colorado','Connecticut',
     'District of Columbia','Delaware','Florida','Georgia','Hawaii','Iowa','Idaho',
     'Illinois','Indiana','Kansas','Kentucky','Louisiana','Massachusetts','Maryland',
     'Maine','Michigan','Minnesota','Missouri','Mississippi','Montana','North Carolina',
     'North Dakota','Nebraska','New Hampshire','New Jersey','New Mexico','Nevada',
     'New York','Ohio','Oklahoma','Oregon','Pennsylvania','Rhode Island','South Carolina',
     'South Dakota','Tennessee','Texas','Utah','Virginia','Vermont','Washington','Wisconsin',
     'West Virginia','Wyoming');
implementation

(******************************* INI SETTINGS ***************************)
procedure TIniSettings.Reset;
begin
   Sections.Clear;
end;

procedure TIniSettings.SetText(fText : String);
var
  i              : integer;
  x              : string;
  fStrList       : TStringList;
  fEntries       : TStringList;
begin
  fStrList:=TStringList.create;
  fStrList.Text:=fText;
  Sections:=TStringList.create;
  for i:=0 to fStrList.Count - 1 do begin
    x:=fStrList.Strings[i];
    if (x[1] = '[') AND (x[length(x)] = ']') then begin
      fEntries:=TStringList.create;
      x:=Copy(x, 2, length(x)-2);
      Sections.AddObject(x, fEntries);
    end else begin
      fEntries.Add(x);
    end;
  end;
end;

function TIniSettings.GetText : String;
var
  i, j     : integer;
  x        : string;
  fEntries : TStringList;
  fStrList : TStringList;
begin
  fStrList:=TStringList.create;
  for i:=0 to Sections.Count-1 do begin
    fStrList.Add('[' + Sections.Strings[i] + ']');
    fEntries:=TStringList(Sections.Objects[i]);
    for j:=0 to fEntries.count-1 do
      fStrList.Add(fEntries.Strings[j])
  end;
  Result:=fStrList.Text;
end;

function TIniSettings.GetValue(fSection,  fKey : string) : string;
var
  i        : integer;
  x        : string;
  fEntries : TStringList;
begin
  Result:='';
  fEntries:=nil;
  for i:=0 to Sections.Count-1 do begin
    if Sections.Strings[i] = fSection then begin
      fEntries:=TStringList(Sections.Objects[i]);
      break;
    end;
  end;
  if fEntries <> nil then begin
    for i:=0 to fEntries.Count-1 do begin
      x:=fEntries.Strings[i];
      if (Piece(x, '=', 1) = fKey) then begin
        Result:=Piece(x, '=', 2);
        break;
      end;
    end;
  end;
end;

procedure TIniSettings.SetValue(fSection, fKey, fValue : string);
var
  i, j     : integer;
  x        : string;
  fEntries : TStringList;
begin
  fEntries:=nil;
  for i:=0 to Sections.Count-1 do begin
    if Sections.Strings[i] = fSection then begin
      fEntries:=TStringList(Sections.Objects[i]);
      break;
    end;
  end;
  if fEntries <> nil then begin
    for j:=0 to fEntries.Count-1 do begin
      x:=fEntries.Strings[j];
      if (Piece(x, '=', 1) = fKey) then begin
        fEntries.Strings[j]:=fKey + '=' + fValue;
        Sections.Objects[i]:=fEntries;
        break;
      end;
    end;
  end;
end;

procedure TIniSettings.AddKeyValue(fSection, fKey, fValue : string);
begin
  InsertKeyValue(fSection, fKey, fValue, -1);
end;

procedure TIniSettings.InsertKeyValue(fSection, fKey, fValue : string;fIndex : integer);
var
  i        : integer;
  x        : string;
  fEntries : TStringList;
begin
  fEntries:=nil;
  for i:=0 to Sections.Count-1 do begin
    if Sections.Strings[i] = fSection then begin
      fEntries:=TStringList(Sections.Objects[i]);
      break;
    end;
  end;
  if fEntries <> nil then begin
    if (fIndex >= 0) AND (fIndex < fEntries.Count) then
      fEntries.Insert(fIndex, fKey + '=' + fValue)
    else
      fEntries.Add(fKey + '=' + fValue);
    Sections.Objects[i]:=fEntries;
  end else begin
    i:=AddSection(fSection);
    fEntries:=TStringList.create;
    fEntries.Add(fKey + '=' + fValue);
    Sections.Objects[i]:=fEntries;
  end;
end;

procedure TIniSettings.DeleteKey(fSection, fKey : string);
var
  i, j     : integer;
  x        : string;
  fEntries : TStringList;
begin
  fEntries:=nil;
  for i:=0 to Sections.Count-1 do begin
    if Sections.Strings[i] = fSection then begin
      fEntries:=TStringList(Sections.Objects[i]);
      break;
    end;
  end;
  if fEntries <> nil then begin
    j:=fEntries.IndexOf(fKey);
    if j >= 0 then begin
      fEntries.Delete(j);
      Sections.Objects[i]:=fEntries;
    end;
  end;
end;

procedure TIniSettings.DeleteKey(fSection : string; fIndex : integer);
var
  i, j     : integer;
  x        : string;
  fEntries : TStringList;
begin
  fEntries:=nil;
  for i:=0 to Sections.Count-1 do begin
    if Sections.Strings[i] = fSection then begin
      fEntries:=TStringList(Sections.Objects[i]);
      break;
    end;
  end;
  if fEntries <> nil then begin
    fEntries.Delete(fIndex);
    Sections.Objects[i]:=fEntries;
  end;
end;

function TIniSettings.AddSection(fSection : string) : integer;
begin
  Result:=Sections.AddObject(fSection, TStringList.create);
end;

function TIniSettings.GetSection(fSection : string) : TStringList;
var
  i    : integer;
begin
  Result:=nil;
  for i:=0 to Sections.Count-1 do begin
    if Sections.Strings[i] = fSection then begin
      Result:=TStringList(Sections.Objects[i]);
      break;
    end;
  end;
end;

(************************** STRING UTILS ****************************)

function MixedCase(const x: string): string;
var
  i: integer;
begin
  Result := x;
  for i := 2 to Length(x) do
    if (not (x[i-1] in [' ','''',',','-','.','/','^'])) and (x[i] in ['A'..'Z']) then
      Result[i] := Chr(Ord(x[i]) + 32);
end;

procedure MixedCaseList(var fList: TStrings);
var
  i: integer;
begin
  for i := 0 to (fList.Count - 1) do
    fList[i] := MixedCase(fList[i]);
end;

function FormatStringForLabel(fStr : String) : String;
begin
  // if there are any ampersand chars (&), double them so they are
  // properly displayed in a label component.
  Result:=ReplaceText(fStr, '&', '&&');
end;

function FormatSSN(const ssn: string): string;
{ places the dashes in a social security number }
begin
  Result:=FormatSSN(ssn, '-');
end;

function FormatSSN(const ssn : string;delim : char) : string;
{ formats a social security number with an arbitrary delimiter}
begin
  if Length(ssn) > 8 then
    Result := Copy(ssn,1,3) + delim + Copy(ssn,4,2) + delim + Copy(ssn,6,Length(ssn))
  else
    Result := ssn;
end;

function StripSSN(const ssn : string) : string;
{ removes dash delimiters from ssn }
begin
  Result:=DeleteChar(ssn, '-');
end;

function SecondsToTime(fSeconds : longint): string;
var
  hours      : Integer;
  minutes    : Shortint;
  seconds    : Shortint;
  s1, s2, s3 : string;
  temp       : real;
begin
  temp:=fSeconds / 3600;
  hours:=Trunc(temp);
  temp:=(temp - Trunc(temp)) * 60;
  minutes:=Trunc(temp);
  temp:=(temp - Trunc(temp)) * 60;
  seconds:=Round(temp);
  s1:=IntToStr(hours);
  s2:=IntToStr(minutes);
  if length(s2) = 1 then
    s2:='0' + s2;
  s3:=IntToStr(seconds);
  if length(s3) = 1 then
    s3:='0' + s3;
  result:=s1 + ':' + s2 + ':' + s3;
end;

function CharCount(ch : char;str : string) : integer;
var
  i,num      : integer;
begin
  num:=0;
  For i:=1 to length(str) do
    if str[i] = ch then
      inc(num);
  result:=num;
end;

function IsInText(str1,str2 : string;fExcludeText : string) : boolean;
var
  Result1,Result2 : boolean;
begin
   Result1:=(AnsiPos(AnsiUpperCase(str1),AnsiUpperCase(str2)) <> 0);
   if fExcludeText <> '' then
     Result2:=(AnsiPos(AnsiUpperCase(fExcludeText),AnsiUpperCase(str2)) = 0)
   else
     Result2:=TRUE;
   Result:=Result1 AND Result2;
end;

function IsInList(str : string;list : TStringList) : boolean;
var
  i : integer;
begin
  Result:=FALSE;
  for i:=0 to list.count-1 do
    if IsInText(list.strings[i], str, '') then begin
      Result:=TRUE;
      break;
    end;
end;

function RandomString : string;
var
  r : integer;
  s : string;
begin
  r:=Random(99999);
  Str(r,s);
  result:=s;
end;

function GetRandomNumber(fLen : integer) : Integer;
begin
  if fLen = 1 then
    result:=Random(9)
  else if fLen = 2 then
    result:=Random(99)
  else if fLen = 3 then
    result:=Random(999)
  else if fLen = 4 then
    result:=Random(9999)
  else if fLen = 5 then
    result:=Random(99999)
  else
    result:=Random(999999);
end;

function SubItem(text : string;field : integer;delim : char) : string;
var
  len,index,i : integer;
begin
  index:=0;
  i:=0;
  len:=length(text);
  result:='';
  if len > 0 then begin
    //go to nth field (the nth-1 delim)
    While (i < field) AND (index <= len) do begin
      inc(index);
      if text[index] = delim then
        inc(i);
    end;
    inc(index);
    While (index <= len) AND (text[index] <> delim) do begin
      result:=result + text[index];
      inc(index);
    end;
  end;
end;

function Piece(text : string;field : integer) : string;
{ returns the nth field of a '^' list of fields (1-based) }
begin
  Result:=Piece(text, '^', field);
end;

function Piece(text : string;delim : char;field : integer) : string;
{ returns the nth field of a '^' list of fields (1-based) }
begin
  Result:=SubItem(text,field-1,delim);
end;

function Pieces(text : string;delim: char;FirstNum,LastNum: Integer): string;
{ returns several contiguous pieces }
var
  i : Integer;
begin
  Result := '';
  for i := FirstNum to LastNum do
    Result := Result + Piece(text, delim, i) + delim;
  if Length(Result) > 0 then
    Delete(Result, Length(Result), 1);
end;

function NumPieces(text : string;delim : char) : integer;
begin
  Result:=CharCount(delim, text) + 1;
end;

function SortByPiece(fList: TStringList;PieceNum: Integer) : TStringList;
var
  i: integer;
begin
  Result:=TStringList.create;
  for i := 0 to fList.Count - 1 do
    Result.add(Piece(fList[i], PieceNum) + '^' + fList[i]);
  Result.Sort;
  for i := 0 to Result.Count - 1 do
    Result[i] := Copy(Result[i], Pos('^', Result[i]) + 1, MaxInt);
end;

function ComparePieces(P1, P2: string; Pieces: array of integer;CaseInsensitive: boolean = FALSE): integer;
var
  i: integer;

const
  Delim: Char = '^';

begin
  i := 0;
  Result := 0;
  while i <= high(Pieces) do
  begin
    if(CaseInsensitive) then
      Result := CompareText(Piece(P1, Pieces[i]),
                            Piece(P2, Pieces[i]))
    else
      Result := CompareStr(Piece(P1, Pieces[i]),
                           Piece(P2, Pieces[i]));
    if(Result = 0) then
      inc(i)
    else
      break;
  end;
end;

function SortByPieces(fList : TStringList;Pieces: array of integer) : TStringList;
const
  Delim: Char = '^';

  procedure QSort(L, R: Integer);
  var
    I, J : Integer;
    P    : string;
  begin
    repeat
      I := L;
      J := R;
      P := fList.Strings[(L + R) shr 1];
      repeat
        while ComparePieces(fList.Strings[I], P, Pieces, TRUE) < 0 do
          Inc(I);
        while ComparePieces(fList.Strings[J], P, Pieces, TRUE) > 0 do
          Dec(J);
        if I <= J then begin
          fList.Exchange(I, J);
          Inc(I);
          Dec(J);
        end;
      until I > J;
      if L < J then QSort(L, J);
      L := I;
    until I >= R;
  end;

begin
  if fList.Count > 1 then
    QSort(0, fList.Count - 1);
  Result:=fList;
end;

procedure SetPiece(var x: string;PieceNum: Integer; NewPiece: string);
{ sets the Nth piece (PieceNum) of a string to NewPiece, adding delimiters as necessary }
begin
  SetPiece(x, '^', PieceNum, NewPiece);
end;

procedure SetPiece(var x: string;Delim : char;PieceNum: Integer; const NewPiece: string);
{ sets the Nth piece (PieceNum) of a string to NewPiece, adding delimiters as necessary }
var
  i: Integer;
  Strt, Next: PChar;
begin
  i := 1;
  Strt := PChar(x);
  Next := StrScan(Strt, Delim);
  while (i < PieceNum) and (Next <> nil) do
  begin
    Inc(i);
    Strt := Next + 1;
    Next := StrScan(Strt, Delim);
  end;
  if Next = nil then Next := StrEnd(Strt);
  if i < PieceNum
    then x := x + StringOfChar(Delim, PieceNum - i) + NewPiece
    else x := Copy(x, 1, Strt - PChar(x)) + NewPiece + StrPas(Next);
end;

procedure SetPieces(var x: string;Pieces: Array of Integer;FromString: string);
begin
  SetPieces(x, '^', Pieces, FromString);
end;

procedure SetPieces(var x: string;Delim : char;Pieces: Array of Integer;FromString: string);
var
  i: integer;
begin
  for i := low(Pieces) to high(Pieces) do
    SetPiece(x, Delim, Pieces[i], Piece(FromString, Pieces[i]));
end;

function GetListPieces(Pieces: Array of Integer;FromList: TStrings) : TStrings;
var
  i,j : integer;
  s   : string;
begin
  Result:=TStringList.create;
  for i:=0 to FromList.Count-1 do begin
    s:='';
    for j:=0 to High(Pieces) do begin
      s:=s + Piece(FromList[i], Pieces[j]);
      if j < High(Pieces) then
        s:=s + '^';
    end;
    Result.add(s);
  end;
end;

function PieceList(list : TStringList;field : integer) : TStringList;
var
  i : integer;
begin
  Result:=TStringList.create;
  for i:=0 to list.count-1 do
    Result.add(Piece(list.strings[i], '^', field));
end;

function PiecesToList(const x : string;delim : char) : TStringList;
var
  i : integer;
begin
  Result:=TStringList.create;
  for i:=1 to NumPieces(x,delim) do
    Result.add(Piece(x,delim, i));
end;

function ListToPieces(fList : TStringList; fDelim : char) : string;
var
  i : integer;
begin
  result:='';
  for i:=0 to fList.Count-1 do begin
    result:=result + fList[i];
    if (i < fList.count-1) then
      result:=result + ',';
  end;
end;

function InvertStringList(fList: TStringList) : TStringList;
var
  i: Integer;
begin
  with fList do
    for i:=0 to ((Count div 2) - 1) do
      Exchange(i, Count - i - 1);
  Result:=fList;
end;

procedure SetListPiece(AList: TStringList; ADelim: Char; PieceNum: Integer; AValue : String);
var
  i: Integer;
  s, x, x1: string;
begin
  for i := 0 to AList.Count - 1 do begin
    s := AList[i];
    x := Piece(s, ADelim, PieceNum);
    x1 := Avalue;
    SetPiece(s, ADelim, PieceNum, x);
    AList[i] := s;
  end;
end;

function CharAt(const x: string; APos: Integer): Char;
{ returns a character at a given position in a string or the null character if past the end }
begin
  if Length(x) < APos then
    Result := #0
  else
    Result := x[APos];
end;

function IsLast5(x: string): Boolean;
{ returns true if string matchs patterns: A9999 or 9999 (BS & BS5 xrefs for patient lookup) }
var
  i: Integer;
begin
  Result:=False;
  if not ((Length(x) = 4) or (Length(x) = 5)) then
    Exit;
  if Length(x) = 5 then begin
    if not (x[1] in ['A'..'Z', 'a'..'z']) then
      Exit;
    x:=Copy(x, 2, 4);
  end;
  for i := 1 to 4 do
    if not (x[i] in ['0'..'9']) then
      Exit;
  Result:=True;
end;

function IsFullSSN(x: string): boolean;
var
  i: integer;
begin
  Result := False;
  if (Length(x) < 9) or (Length(x) > 12) then
    Exit;
  case Length(x) of
    9:  // no dashes, no 'P'
        for i := 1 to 9 do if not (x[i] in ['0'..'9']) then
          Exit;
   10:  // no dashes, with 'P'
        begin
          for i := 1 to 9 do if not (x[i] in ['0'..'9']) then
            Exit;
          if (Uppercase(x[10]) <> 'P') then
            Exit;
        end;
   11:  // dashes, no 'P'
        begin
          if (x[4] <> '-') or (x[7] <> '-') then
            Exit;
          x := Copy(x,1,3) + Copy(x,5,2) + Copy(x,8,4);
          for i := 1 to 9 do if not (x[i] in ['0'..'9']) then
            Exit;
        end;
   12:  // dashes, with 'P'
        begin
          if (x[4] <> '-') or (x[7] <> '-') then
            Exit;
          x := Copy(x,1,3) + Copy(x,5,2) + Copy(x,8,5);
          for i := 1 to 9 do if not (x[i] in ['0'..'9']) then
            Exit;
          if UpperCase(x[10]) <> 'P' then
            Exit;
        end;
  end;
  Result := True;
end;

function IsSSN(const x: string): Boolean;
var
  i: Integer;
begin
  Result := False;
  if IsInText('-', x, '') then begin
    if (Length(x) <> 11) then
      Exit;
  end else if (Length(x) <> 9) then
    Exit;
  for i := 1 to Length(x) do
    if (not (x[i] in ['0'..'9'])) AND (x[i] <> '-') then
      Exit;
  Result := True;
end;

function GetFirstName(fullname : string) : string;
var
  len, index : integer;
  fname : string;
  EndOfString : boolean;
begin
   fname:='';
   If Trim(fullname) <> '' then begin
     len:=length(fullname);
     index:=1;
     EndOfString:=false;
     while ((fullname[index] <> ',') AND (NOT EndOfString)) do begin
        If index = len then
          EndOfString:=true
        else
          inc(index);
     end;
     inc(index);
     while ((fullname[index] <> ' ') AND (NOT EndOfString)) do begin
        fname:=fname + fullname[index];
        If index = len then
          EndOfString:=true
        else
          inc(index);
     end;
   end;
   if trim(fname) <> '' then
     result:=fname
   else
     result:=fullname;
end;

function ValidInteger(d : string) : boolean;
var
  NumberSet : set of char;
  i         : integer;
begin
   NumberSet := ['0','1','2','3','4','5','6','7','8','9'];
   If Trim(d) <> '' then begin
     Result:=TRUE;
     for i:=1 to length(d) do
       Result:=Result AND (d[i] in NumberSet);
   end else
     Result:=FALSE;
end;

function ValidFloat(d : string) : boolean;
var
  NumberSet : set of char;
  i         : integer;
begin
   NumberSet := ['0','1','2','3','4','5','6','7','8','9', '.'];
   If Trim(d) <> '' then begin
     Result:=TRUE;
     for i:=0 to length(d)-1 do
       Result:=Result AND (d[i] in NumberSet);
   end else
     Result:=FALSE;
end;

function GetFloatFromString(fStr : String) : Double;
begin
  try
    Result:=StrToFloat(fStr);
  except
    Result:=0.0;
  end;
end;

function GetIntFromString(fStr : String) : Integer;
begin
  try
    Result:=StrToInt(fStr);
  except
    Result:=0;
  end;
end;

function NumberCommaDelimitedStrings(strng : string) : string;
// takes a list of comma-delimited strings (i.e. xxxxxx,yyyyyy,zzzzz) and formats it
// into a numbered list of the strings, one string per line.
var
   l,n,i : integer;
   c, s  : string;
   list  : TStringList;
begin
   list:=TStringList.create;
   l:=length(strng);
   n:=0;
   for i:=1 to l do begin
      if (strng[i] = ',') then begin
         inc(n);
         str(n,c);
         list.add(c + '.  ' + s);
         s:='';
      end else
         s:=s + strng[i];
   end;
   inc(n);
   str(n,c);
   list.add(c + '.  ' + s);
   result:=list.text;
   list.free;
end;

function RemoveTrailingChars(strng : string;c : char) : string;
var
  temp : string;
  l,i  : integer;
begin
  strng:=Trim(strng);
  If length(Trim(strng)) > 0 then begin
    temp:='';
    l:=Length(strng);
    While (strng[l] = ' ') OR (strng[l] = c) do
      dec(l);
    If (l > 0) then begin
      For i:=1 to l do
         Temp:=Temp + strng[i];
    end;
  end;
  result:=temp;
end;

function CommaDelimitedStringToList(strng : string) : string;
var
  temp       : TStringList;
begin
  temp:=DelimStringToList(strng, ',');
  result:=temp.text;
end;

function DelimStringToList(strng : string;delim : char) : TStringList;
var
  temp       : string;
  l,i        : integer;
begin
  result:=Tstringlist.create;
  If length(Trim(strng)) > 0 then begin
    temp:='';
    l:=Length(strng);
    For i:=1 to l do begin
      If strng[i] = delim then begin
        temp:=Trim(temp);
        result.add(temp);
        temp:='';
      end else
        Temp:=Temp + strng[i];
    end;
    temp:=Trim(temp);
    result.add(temp);
  end;
end;

function StringBlocks(fStr : string;fBlockSize : integer) : TStringList;
var
  i, j, n       : integer;
  d             : double;
  temp          : string;
  start, finish : integer;
begin
  Result:=TStringList.create;
  d:=Length(fStr) / fBlockSize;
  n:=Trunc(d);
  if (d - n) > 0 then
    n:=n+1;
  start:=1;
  finish:=fBlockSize;
  for i:=1 to n do begin
    temp:='';
    for j:=start to finish do
      temp:=temp + fStr[j];
    Result.add(temp);
    start:=finish+1;
    if (start + fBlockSize) > Length(fStr) then
      finish:=Length(fStr)
    else
      finish:=start + fBlockSize;
  end;
end;

function GetElement(strng : string;n : integer) : string;
var
  temp         : string;
  found        : boolean;
  l, i, index  : integer;
begin
  found:=false;
  If length(trim(strng)) > 0 then begin
    temp:='';
    l:=length(strng);
    i:=1;
    index:=-1;
    while (i <= l) AND (NOT Found) do begin
      If (strng[i] = ',') OR (i = l) then begin
        inc(index);
        if index = n then begin
          if i = l then
            temp:=temp + strng[i];
          found:=true
        end else begin
          found:=false;
          temp:='';
        end;
      end else
        temp:=temp + strng[i];
      inc(i);
    end;
  end;
  if found then
    result:=temp
  else
    result:='';
end;

function ParseString(LongString : String;delimiter : WideChar;AddNums : Boolean) : TStringList;
var
   l, index     : integer;
   tempstring,s : String;
   Strings      : TStringList;
begin
   l:=length(LongString);
   TempString:='';
   Strings:=TStringList.create;
   Strings.clear;
   index:=1;
   While index <=l do begin
      If (WideChar(longstring[index]) <> delimiter) then begin
         tempstring:=tempstring + longstring[index];
         inc(index);
         If (index = l) AND (WideChar(longstring[index]) <> delimiter) then begin
            tempstring:=tempstring + longstring[index];
            Str(Strings.Count+1,s);
            if tempstring = 'none listed' then
               Strings.add(TempString)
            else
               If AddNums then
                  Strings.add(s + '.  ' + TempString)
               else
                  Strings.add(TempString);
         end;
      end else begin
         If TempString <> '' then begin
            Str(Strings.Count+1,s);
            if tempstring = 'none listed' then
               Strings.add(TempString)
            else
               If AddNums then
                  Strings.add(s + '.  ' + TempString)
               else
                  Strings.add(TempString);
         end;
         TempString:='';
         inc(index);
      end;
   end;
   If Strings.Count = 0 then
      Strings.add('none listed');
   result:=Strings;
   {Strings.Free;}
end;

function TrimString(strings : string) : string;
var
   l,i : integer;
   temp : string;
begin
   l:=length(strings);
   while (strings[l] = ' ') OR (strings[l] = chr(13)) do
      dec(l);
   For i:=1 to l do
      temp:=temp + strings[i];
   result:=temp;

end;

function ReturnCommaDelimString(fText : string) : string;
var
  s : string;
  i : integer;
  stringlist : TStringlist;
begin
   stringlist:=TStringlist.create;
   stringlist.text:=fText;
   s:='';
   for i:=0 to stringlist.count-1 do begin
     s:=s + stringlist.strings[i];
     if i<stringlist.count-1 then
       s:=s + ', ';
   end;
   result:=s;
end;

function WrapLines(fStrings : string;maxlen : integer) : string;
var
   index,i,len,segs,start,count,totalcount : integer;
   strings,OldList, NewList                : Tstringlist;
   line                                    : string;
begin
   strings:=TStringlist.create;
   strings.settext(Pchar(fStrings));
   OldList:=TStringlist.create;
   OldList.SetText(PChar(strings.text));
   OldList.sorted:=false;
   NewList:=TStringList.create;
   NewList.sorted:=false;
   index:=0;
   While index < OldList.count do begin
      line:=OldList.strings[index];
      len:=length(line);
      If len > maxlen then begin
         segs:=Ceil(len / maxlen);
         line:=copy(OldList.strings[index],1,maxlen);
         start:=1;
         count:=maxlen;
         totalcount:=0;
         For i:=1 to segs do begin
            If ((len - totalcount) + 1) > MaxLen then begin
              line:=copy(OldList.strings[index],start,count);
              while (line[count] <> ' ') AND (line[count] <> '>') do
                dec(count);
              totalcount:=totalcount + count;
              line:=copy(line,1,count);
              start:=start + count;
              count:=maxlen;
              NewList.add(line);
            end else begin
              Count:=(len - totalcount) + 1;
              totalcount:=totalcount + count;
              line:=copy(OldList.strings[index],start,count);
              NewList.add(line);
            end;
         end;
         If totalcount <= len then begin
           Count:=(len - totalcount) + 1;
           line:=copy(OldList.strings[index],start,count);
           NewList.add(line);
         end;
      end else
         NewList.add(line);
      inc(index);
   end;
   result:=NewList.text;
   NewList.free;
   OldList.free;
end;

function BoolToStr(fBool : boolean) : string;
begin
  Result:=BoolToStr(fBool, ['TRUE', 'FALSE']);
end;

function BoolToStr(fBool : boolean;fStrArray : array of string) : string;
begin
  if fBool then
    Result:=fStrArray[0]
  else
    Result:=fStrArray[1];
end;

function StrToBool(fStr : string) : boolean;
begin
  Result:=StringToBoolean(fStr, ['TRUE', 'FALSE']);
end;

function BooleanToString(bool : boolean;fStrings : array of string) : string;
begin
  if bool then
    result:=fStrings[0]
  else
    result:=fStrings[1];
end;

function StringToBoolean(fStr : string;fStrings : array of string) : boolean;
begin
  fStr:=Trim(fStr);
  result:=AnsiUpperCase(fStr) = AnsiUpperCase(fStrings[0]);
end;

function SubString(text : string;n : integer) : string;
var
   index     : integer;
   substring : string;
   len       : integer;
begin
   // get first n characters of data
   index:=1;
   len:=length(text);
   substring:='';
   While (index <= len) AND (index <= n) do begin
      substring:=substring + text[index];
      inc(index);
   end;
   result:=substring;
end;

function GetSubstring(data : string;n : integer) : string;
var
   l, index  : integer;
   temp,substring : string;
begin
   // get last n characters of data
   l:=length(data);
   index:=l;
   substring:='';
   While (index>=1) AND ((l - index) <=n) do begin
      substring:=substring + data[index];
      dec(index);
   end;
   temp:=substring;
   substring:='';
   For index:=length(temp) downto 1 do
      If (Ord(temp[index]) >= 32) AND (Ord(temp[index]) <= 126) then
         substring:=substring + temp[index];
   result:=substring;
end;

function GetSubstring(data : string;start,stop : integer) : string;
var
   len, index  : integer;
   substring : string;
begin
   len:=length(data);
   index:=start+1;
   substring:='';
   While (index <= len) AND ((index >= start) AND (index <= stop)) do begin
      substring:=substring + data[index];
      inc(index);
   end;
   result:=substring;
end;

function GetSubStringIndex(substring : string;stringlist : TStringList) : integer;
begin
  result:=-1;
  Repeat
    inc(result);
  Until (result = stringlist.count) OR (IsInText(stringlist.names[result],substring,''));
  if result = stringlist.count then
    result:=-1;
end;

function ReplaceChar(text : string;oldChar : char;newChar : char) : string;
var
  i : integer;
begin
  For i:=1 to length(text) do
    If text[i] = oldChar then
      text[i]:=newChar;
  result:=text;
end;

function ReplaceText(text : string;oldChar : char;newText : string) : string;
var
  i   : integer;
  str : string;
begin
  str:='';
  For i:=1 to length(text) do begin
    If text[i] = oldChar then
      str:=str + newText
    else
      str:=str + text[i];
  end;
  result:=str;
end;

function ReplaceText(text : string;oldText : string;newText : string) : string;
var
  P, S, SubStr : PChar;
  t, len       : integer;
begin
  Result:=text;
  S:=PChar(Result);
  SubStr:=PChar(oldText);
  len:=Length(oldText);
  P:=StrPos(S, SubStr);
  if P <> nil then begin
    t:=(P - S);
    Delete(Result, t+1, len);
    Insert(newText, Result, t+1);
    Result:=ReplaceText(Result, oldText, newText);
  end;
end;

function ReplaceText(text : string;oldText : string;newChar : char) : string;
var
  P, S, SubStr : PChar;
  temp         : string;
  t, len       : integer;
  done         : boolean;
begin
  if StrPos(PChar(text), PChar(oldText)) <> nil then begin
    Result:='';
    SubStr:=PChar(oldText);
    len:=Length(oldText);
    temp:=text;
    Repeat
      S:=PChar(temp);
      P:=StrPos(S, SubStr);
      if P <> nil then begin
        t:=(P - S);
        Result:=Result + Copy(temp, 1, t) + newChar;
        t:=t + len + 1 ;
        temp:=Copy(temp, t, Length(temp));
      end;
    Until (P = nil);
    Result:=Result + S;
  end else
    Result:=text;
end;

function RemoveCharFromText(text : string;c : char) : string;
var
  i : integer;
begin
  For i:=1 to length(text) do
    If text[i] = c then
      text[i]:=' ';
  result:=text;
end;

function DeleteCharInText(text : string;c : char) : string;
var
  i : integer;
  temp : string;
begin
  temp:='';
  For i:=1 to length(text) do
    If text[i] <> c then
      temp:=temp + text[i];
  result:=temp;
end;

function FilterText(x : string) : string;
begin
  x:=FilterCntlChrs(x);
  x:=ReplaceText(x, #$D#$A, '\n');
  Result:=ReplaceText(x, #$A, '\n');
end;

function FilterCntlChrs(x : string) : string;
var
  i : integer;
  s : shortstring;
begin
  result:='';
  x:=Trim(x);
  for i:=1 to length(x) do begin
    s:=x[i];
    if s = Chr(189) then
      s:='1/2'
    else if s = Chr(188) then
      s:='1/4'
    else if s = Chr(190) then
      s:='3/4'
    else if s = Chr(177) then
      s:='+/-'
    else if s = Chr(242) then
      s:='>='
    else if s = Chr(243) then
      s:='<='
    else if s = Chr(246) then
      s:='/'
    else if s = Chr(247) then
      s:='='
    else if s = Chr(164) then
      s:='n'
    else if s > Chr(126) then
      s:=' ';
    result:=result + s;
  end;
end;

function DeleteCntlChrs(x : string) : string;
var
  i : integer;
  temp : string;
begin
  temp:='';
  For i:=1 to length(x) do
    If ( (x[i] <> Chr(9)) AND (x[i] <> Chr(10)) AND (x[i] <> Chr(13)) ) OR
       ( (x[i] >= Chr(32)) AND (x[i] <= Chr(126)) ) then
      temp:=temp + x[i];
  result:=temp;
end;

function FilterCRNL(x : string) : string;
var
  i : integer;
  temp : string;
begin
  temp:='';
  For i:=1 to length(x) do
    If (x[i] <> Chr(13)) AND (x[i] <> Chr(10)) then begin
      temp:=temp + x[i]
    end else if (x[i] = Chr(10)) then
      temp:=temp + '\n'
    else if (x[i] = Chr(13)) then
      temp:=temp + '\r';
  result:=temp;
end;

function FilterNL(x : string) : string;
var
  i : integer;
  temp : string;
begin
  temp:='';
  For i:=1 to length(x) do
    If (x[i] <> Chr(10)) then
      temp:=temp + x[i]
    else if (x[i] = Chr(10)) then
      temp:=temp + '\n\r';
  result:=temp;
end;

function ConvertToCRNL(x : string) : string;
var
  i : integer;
  temp : string;
begin
  temp:='';
  i:=1;
  while i <= length(x) do begin
    if (x[i] =  '\') AND (x[i+1] = 'n') then begin
      temp:=temp + Chr(10);
      inc(i)
    end else if (x[i] =  '\') AND (x[i+1] = 'r') then begin
      temp:=temp + Chr(13);
      inc(i)
    end else
      temp:=temp + x[i];
    inc(i);
  end;
  result:=temp;
end;

function FormatCRNL(x : string) : string;
var
  i : integer;
  temp : string;
begin
  temp:='';
  i:=1;
  while i <= length(x) do begin
    if ((x[i] = #10) AND (x[i+1] <> #13)) OR
       ((x[i] = #13) AND (x[i-1] <> #10)) then
      temp:=temp + #10 + #13
    else
      temp:=temp + x[i];
    inc(i);
  end;
  result:=temp;
end;

function DeleteCRNL(x : string) : string;
var
  i : integer;
  temp : string;
begin
  temp:='';
  i:=1;
  while i <= length(x) do begin
    if (x[i] =  #10) then
      temp:=temp + ' '
    else if (x[i] =  #13) then
      temp:=temp + ' '
    else
      temp:=temp + x[i];
    inc(i);
  end;
  result:=temp;
end;

function DeleteTab(x : string) : string;
var
  i : integer;
  temp : string;
begin
  temp:='';
  i:=1;
  while i <= length(x) do begin
    if (x[i] =  #9) then
      temp:=temp + ' '
    else
      temp:=temp + x[i];
    inc(i);
  end;
  result:=temp;
end;

function EncodeURL(x : String) : string;
var
  chars   : array[1..22] of Char;
  codes   : array[1..22] of String;
  i, j, len  : integer;
  Found      : boolean;
begin
  // Characters to be escaped
  chars[1]:=' ';chars[2]:='<';chars[3]:='>';chars[4]:='#';chars[5]:='%';
  chars[6]:='{';chars[7]:='}';chars[8]:='|';chars[9]:='\';chars[10]:='^';
  chars[11]:='~';chars[12]:='[';chars[13]:=']';chars[14]:='`';chars[15]:=';';
  chars[16]:='/';chars[17]:='?';chars[18]:=':';chars[19]:='@';chars[20]:='=';
  chars[21]:='&';chars[22]:='$';
  // Escape codes
  codes[1]:='%20';codes[2]:='%3C';codes[3]:='%3E';codes[4]:='%23';codes[5]:='%7B';
  codes[6]:='%7B';codes[7]:='%7D';codes[8]:='%7C';codes[9]:='%5C';codes[10]:='%5E';
  codes[11]:='%7E';codes[12]:='%5B';codes[13]:='%5D';codes[14]:='%60';codes[15]:='%3B';
  codes[16]:='%2F';codes[17]:='%3F';codes[18]:='%3A';codes[19]:='%40';codes[20]:='%3D';
  codes[21]:='%26';codes[21]:='%24';
  Result:='';
  i:=1;
  len:=Length(x);
  while (i <= len) do begin
    Found:=FALSE;
    for j:=1 to 5 do begin
      if x[i] = chars[j] then begin
        Result:=Result + codes[j];
        Found:=TRUE;
        break;
      end;
    end;
    if NOT Found then
      Result:=Result + x[i];
   inc(i);
  end;
end;

function ConvertToBR(x : string) : string;
var
  i : integer;
  temp : string;
begin
  temp:='';
  For i:=1 to length(x) do
    if (x[i] = Chr(10)) then
      temp:=temp + '<BR>'
    else
      temp:=temp + x[i];
  result:=temp;
end;

function RemoveTrailingCntlChrs(x : string) : string;
var
  i,j         : integer;
  temp        : string;
  ControlChar : boolean;
begin
  temp:=x;
  i:=length(x);
  ControlChar:=true;
  while (i >= 1) AND (ControlChar) do begin
    ControlChar:=((x[i] < Chr(32)) OR (x[i] > Chr(126)));
    If ControlChar then begin
      Dec(i);
      temp:='';
      for j:=1 to i do
        temp:=temp + x[j];
    end;
  end;
  result:=temp;
end;

function RemoveLeadingCntlChrs(x : string) : string;
var
  i           : integer;
  temp        : string;
  ControlChar : boolean;
begin
  temp:='';
  for i:=1 to length(x) do begin
    ControlChar:=((x[i] < Chr(32)) OR (x[i] > Chr(126)));
    If NOT ControlChar then
      temp:=temp + x[i];
  end;
  result:=temp;
end;

function RemoveTrailingChar(x : string;c : char) : string;
begin
  result:=x;
  if length(x) > 0 then begin
    if x[length(x)] = c then
      result:=Substring(x, length(x)-1);
  end;
end;

function DeleteChar(x : string;c : char) : string;
var
  i : integer;
  temp : string;
begin
  temp:='';
  For i:=1 to length(x) do
    If x[i] <> c then
      temp:=temp + x[i];
  result:=temp;
end;

function GetStateCode(fState : string) : string;
var
  i : integer;
begin
  Result:=fState;
  i:=0;
  while (i < Length(STATE_NAMES)) do begin
    if (fState <> STATE_NAMES[i]) then
      inc(i)
    else begin
      Result:=STATE_CODES[i];
      exit;
    end;
  end;
end;

function GetStateName(fCode : string) : string;
var
  i : integer;
begin
  Result:=fCode;
  i:=0;
  while (i < Length(STATE_CODES)) do begin
    if (fCode <> STATE_CODES[i]) then
      inc(i)
    else begin
      Result:=STATE_NAMES[i];
      exit;
    end;
  end;
end;

function FormatLastNameFirst(fName : string) : string;
var
  num, i : integer;
  str    : string;
begin
  if IsInText(' ', fName, '') AND (NOT IsInText(',', fName, '')) then begin
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

end.


