(* ****************************************************************************)
 //  Date Created: Jan 25, 2012
 //  Site Name: Loma Linda VAMC
 //  Developers: Rob Durkin
 //  Description: R1 OR Schedule Viewer
 //  Note: This unit requires R1SRLOR in order to run.
(******************************************************************************)
unit LL_DateUtils;

interface

uses
  // Standard
  SysUtils, Classes, Controls, XSBuiltIns,
  // Utils
  LL_StringUtils;

type
  EFMDateTimeError = class(Exception);  

// DATE FUNCTIONS
procedure InitDefaultFormatSettings;
function ShortDateStrToDate(shortdate: string): string ;
function AnsiDateDiff(fDate1, fDate2 : string) : integer;
function DateInRange(fDate, startDate, stopDate : string) : boolean;
function AnsiDate(fDate : string) : string; overload;
function AnsiDate(fDate : TDate) : string;  overload;
function AnsiDateTime(fDateTime : TDateTime) : string;
function AnsiDateShortTime(fDateTime : TDateTime) : string;
function AnsiStrToDate(fDate : string) : TDateTime;
function FormatTime(fTime : string) : string; overload;
function FormatTime(fTime : string;fShowSecs : boolean; fFormatBlank : boolean) : string; overload;
function GetHoursMinutes(fTime : string) : string;
function DateIsTodayOrLater(fDate : string) : boolean;
function IsFutureDate(fDate : string) : boolean;
function AddDaysToDate(fDate : string;fDays : integer) : string;
function AddDaysToAnsiDate(fDate : string;fDays : integer) : string;
function DateDiff(fDate1, fDate2 : string) : integer;
function ValidDate(d : string) : boolean;
function ValidAnsiDate(d : string) : boolean;
function ValidTime(t : string) : boolean;
function CalcAge(aDate : string;birthdate : string) : integer;
procedure AnsiYearMonthDay(dt : string;var year, month, day : string);    overload;
procedure EnglishYearMonthDay(dt : string;var year, month, day : string); overload;
procedure AnsiYearMonthDay(dt : string;var year, month, day : integer);    overload;
procedure EnglishYearMonthDay(dt : string;var year, month, day : integer); overload;
function AnsiDateToISODate(dt : string) : string;
function EnglishDateToISODate(dt : string) : string;
function EnglishDate(dt : TDate) : string; overload;
function EnglishDate(dt : string) : string; overload;
function EnglishDateTime(dt : string) : string; overload;
function EnglishDateTime(dt : TDateTime) : string; overload;
function ShortEnglishDateTime(dt : TDateTime) : string; overload;
function ShortEnglishDate(dt : TDate) : string; overload;
function ShortEnglishDateTime(fDateTimeStr : string) : string; overload;
function ShortEnglishDate(fDateStr : string) : string; overload;
function AnsiDateTimeToShortEnglishDateTime(ansidatetime : string) : string; overload;
function AnsiDateTimeToShortEnglishDate(ansidate : string) : string; overload;
function GetMostRecentDate(list : string) : string;
function GetMostRecentDateIndex(list : string) : integer;
function LongDateTimeStrToDateTime(datetime: string) : TDateTime; overload;
function LongDateTimeStrToDateTime(datetime: string;delim : char) : TDateTime; overload;
function LongDateStrToDate(fDateStr: string) : TDate;
function ExtractDateFromDateTime(date_time : string) : string;
function GetMonthString(fMonth : integer;fFullName : boolean) : string;
function LongToShortDate(dt : string) : string;
function SecondsToTime(fSeconds : longint): string;
function AnsiDateToDateTime(fAnsiDate : string) : TDateTime;
function AnsiDateToDate(fAnsiDate : string) : TDate;
function CheckDate(fDate : string) : string;
function CheckAnsiDate(fDate : string) : string;

// Fileman Date Functions
function IsFMDate(const x: string): Boolean;
function DateTimeToFMDateTime(ADateTime: TDateTime): double;
function AnsiDateTimeStrToFMDateTime(ADateTimeStr: String): double;
function EnglishDateTimeStrToFMDateTime(ADateTimeStr: String): double;
function FMDateTimeToDateTime(ADateTime: double): TDateTime;
function FMDateTimeToAnsiDateTime(ADateTime: double): String;
function FMDateTimeOffsetBy(ADateTime: double; DaysDiff: Integer): double;
function FormatFMDateTime(AFormat: string; ADateTime: double): string;
function FormatFMDateTimeStr(const AFormat, ADateTime: string): string;
function IsFMDateTime(x: string): Boolean;
function MakeFMDateTime(const AString: string): double;
procedure SetListFMDateTime(AFormat: string; AList: TStringList; ADelim: Char;
                            PieceNum: Integer; KeepBad: boolean = FALSE);
//function StrToDateEx(AString : string; AShortDateFormat : string = '') : TDateTime;                            

// XSDate Functions
function DateToXSDate(fDate : TDate) : TXSDate;
function XSDateToDate(fXSDate : TXSDate) : TDate;
function DateTimeToXSDateTime(fDateTime : TDateTime) : TXSDateTime;
function XSDateTimeToDateTime(fXSDateTime : TXSDateTime) : TDateTime;
function XSDateTimeToEnglishDateTime(fXSDateTime : TXSDateTime) : String;
function XSDateTimeToShortEnglishDateTime(fXSDateTime : TXSDateTime) : String;

const
  { days of week }
  DAYS_OF_WEEK: array[1..7] of string =
    ('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday');
  { names of months }
  MONTHS_SHORT: array[1..12] of string[3] =
    ('Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec');
  MONTHS_LONG:  array[1..12] of string[9] =
    ('January','February','March','April','May','June','July','August','September','October',
     'November', 'December');

implementation

procedure InitDefaultFormatSettings;
begin
  GetFormatSettings;
  ShortDateFormat:='mm/dd/yyyy';
  ShortTimeFormat:='hh:nn';
  LongTimeFormat:='hh:nn:ss';
end;

function ShortDateStrToDate(shortdate: string): string ;
{Converts date in format 'mmm dd,yy' or 'mmm dd,yyyy' to standard 'mm/dd/yy'}
var
  month,day,year: string ;
  i: integer ;
begin
  result := '' ;
  if (Pos(' ',shortdate) <> 4) or ((Pos(',',shortdate) <> 7) and (Pos(',',shortdate) <> 6)) then exit ;  {no spaces or comma}
  for i := 1 to 12 do
    if UpperCase(MONTHS_SHORT[i]) = UpperCase(Copy(shortdate,1,3)) then month := IntToStr(i);
  if month = '' then exit ;    {invalid month name}
  if length(month) = 1 then month := '0' + month;
  if Pos(',',shortdate) = 7 then
    begin
      day  := IntToStr(StrToInt(Copy(shortdate,5,2))) ;
      year := IntToStr(StrToInt(Copy(shortdate,8,99))) ;
    end;
  if Pos(',',shortdate) = 6 then
    begin
      day  := '0' + IntToStr(StrToInt(Copy(shortdate,5,1))) ;
      year := IntToStr(StrToInt(Copy(shortdate,7,99))) ;
    end;
  result := month+'/'+day+'/'+year ;
end;

function LongDateTimeStrToDateTime(datetime: string) : TDateTime;
begin
  Result:=LongDateTimeStrToDateTime(datetime, '@');
end;

function LongDateTimeStrToDateTime(datetime: string;delim : char) : TDateTime;
var
  fDateStr : String;
  fTimeStr : String;
begin
  fDateStr:=LL_StringUtils.Piece(datetime, delim, 1);
  fTimeStr:=LL_StringUtils.Piece(datetime, delim, 2);
  try
    if AnsiPos('/', fDateStr) = 0 then
      fDateStr:=ShortDateStrToDate(fDateStr);
    Result:=StrToDateTime(fDateStr + ' '+ fTimeStr);
  except
    Result:=0;
  end;
end;

function LongDateStrToDate(fDateStr: string) : TDate;
begin
  try
    if AnsiPos('/', fDateStr) = 0 then
      fDateStr:=ShortDateStrToDate(fDateStr);
    Result:=StrToDate(fDateStr);
  except
    Result:=0;
  end;
end;

function ExtractDateFromDateTime(date_time : string) : string;
var
  l, index : integer;
  dt : string;
  EndOfString : boolean;
begin
   dt:='';
   If Trim(date_time) <> '' then begin
     l:=length(date_time);
     index:=1;
     EndOfString:=false;
     while ((date_time[index] <> '@') AND (date_time[index] <> ' ')) AND (NOT EndOfString) do begin
        dt:=dt + date_time[index];
        If index = l then
          EndOfString:=true
        else
          inc(index);
     end;
   end;
   result:=dt;
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

function AnsiDate(fDate : string) : string;
var
   temp       : string;
   mo, yr, dy : string;
   i, n       : integer;
begin
  If Trim(fDate) <> '' then begin
    n:=0;
    for i:=1 to length(fDate) do begin
      if (fDate[i] = '/') OR (fDate[i] = '-') then begin
        if n = 0 then
          mo := trim(temp)
        else if n = 1 then
          dy := trim(temp);
        temp:='';
        inc(n);
      end else
        temp := temp + fDate[i];
    end;
    if length(mo) = 0 then
      mo := '00'
    else if length(mo) = 1 then
      mo := '0' + mo;
    if length(dy) = 0 then
      dy := '00'
    else if length(dy) = 1 then
      dy := '0' + dy;
    if trim(temp) = '' then
      yr := '0000'
    else
      yr := temp;
    result:=yr + '-' + mo +'-' + dy;
  end else
    result:='';
end;

function AnsiDate(fDate : TDate) : string;
var
   year, month, day     : word;
begin
  DecodeDate(fDate, year, month, day);
  Result:=IntToStr(year) + '-' + IntToStr(month) + '-' + IntToStr(day);
end;

function AnsiDateTime(fDateTime : TDateTime) : string;
var
   year, month, day     : word;
   hour, min, sec, msec : word;
   fDate, fTime         : string;
begin
  DecodeDate(fDateTime, year, month, day);
  DecodeTime(fDateTime, hour, min, sec, msec);
  fDate:=IntToStr(year) + '-' + IntToStr(month) + '-' + IntToStr(day);
  fTime:=IntToStr(hour) + ':' + IntToStr(min) + ':' + IntToStr(sec);
  result:=fdate + ' ' + fTime;
end;

function AnsiDateShortTime(fDateTime : TDateTime) : string;
var
   year, month, day     : word;
   hour, min, sec, msec : word;
   fDate, fTime         : string;
begin
  DecodeDate(fDateTime, year, month, day);
  DecodeTime(fDateTime, hour, min, sec, msec);
  fDate:=IntToStr(year) + '-' + IntToStr(month) + '-' + IntToStr(day);
  fTime:=IntToStr(hour) + ':' + IntToStr(min);
  result:=fdate + ' ' + fTime;
end;

function AnsiStrToDate(fDate : string) : TDateTime;
begin
  Result:=StrToDateTime(EnglishDateTime(fDate));
end;

function FormatTime(fTime : string) : string;
begin
  Result:=FormatTime(fTime, FALSE, FALSE);
end;

function FormatTime(fTime : string;fShowSecs : boolean; fFormatBlank : boolean) : string;
var
  len : integer;
begin
  if fFormatBlank AND (length(fTime) = 0) then begin
    result:='00:00';
    if fShowSecs then
      result:=result + ':00';
  end else begin
    len:=Length(fTime);
    if (fShowSecs) then begin
      case len of
        1 : result:='00:00:0' + fTime;
        2 : result:='00:00:' + fTime;
        3 : result:='00:00' + fTime;
        4 : result:='00:0' + fTime;
        5 : result:='00:' + fTime;
        6 : result:='00' + fTime;
        7 : result:='0' + fTime
        else result:=fTime;
      end;
    end else begin
      case len of
        1 : result:='00:0' + fTime;
        2 : result:='00:' + fTime;
        3 : result:='00' + fTime;
        4 : result:='0' + fTime
        else result:=fTime;
      end;
    end;
  end;
end;

function GetHoursMinutes(fTime : string) : string;
var
  i, len : integer;
begin
  result:='';
  if length(fTime) = 0 then
    result:='00:00'
  else begin
    len:=CharCount(':', fTime);
    if len = 0 then
      result:=fTime + ':00'
    else if len = 1 then
      result:=fTime
    else begin
      i:=1;
      len:=1;
      while (len <= 2) do begin
        if fTime[i] = ':' then
          inc(len);
        if len <= 2 then
          Result:=Result + fTime[i];
        inc(i);
      end;
    end;
  end;
end;

function CalcAge(aDate : string;birthdate : string) : integer;
var
   b_month, b_day, b_year      : string;
   t_month, t_day, t_year      : string;
   Delta_Years, int1, int2, ec : integer;
begin
   If (Trim(aDate) <> '') AND (Trim(aDate) <> '/  /') AND (Trim(aDate) <> '00/00/0000') AND
      (Trim(birthdate) <> '') AND (Trim(birthdate) <> '/  /') AND (birthdate <> '00/00/0000') then begin
      AnsiYearMonthDay(birthdate,b_month, b_day, b_year);
      AnsiYearMonthDay(aDate,t_month, t_day, t_year);
      Val(b_year,int1,ec);
      if ec = 0 then begin
        Val(t_year,int2,ec);
        Delta_Years:=(int2 - int1)-1;
        Val(b_month,int1,ec);
        if (ec = 0) AND (int1 >= 1) AND (int1 <= 12) then begin
          Val(t_month,int2,ec);
          If int2 > int1 then
             Delta_Years:=Delta_Years + 1
          else if int2 = int1 then begin
             Val(b_day,int1,ec);
             if (ec = 0) AND (int1 >= 1) AND (int1 <= 12) then begin
               Val(t_day,int2,ec);
               If int2 >= int1 then
                  Delta_Years:=Delta_Years + 1;
             end else begin
               Result:=Delta_Years;
             end;
          end;
          Result:=Delta_Years;
        end else begin
          Result:=Delta_Years;
        end;
      end else
        Result:=0;
   end else
      Result:=0;
end;

function DateIsTodayOrLater(fDate : string) : boolean;
// takes an input date and returns true if the date is today or later and false if it is
// earlier than today.  The input date should be in the form mm/dd/yyyy.
begin
  if fDate <> '00/00/0000' then begin
    try
      result:=Trunc(StrToDate(fDate)) >= Trunc(Now);
    except
      on EConvertError do result:=false;
    end;
  end else
    result:=false;
end;

function IsFutureDate(fDate : string) : boolean;
// takes an input date and returns true if the date is in the future and false if it is
// earlier than today.  The input date should be in the form mm/dd/yyyy.
begin
  if fDate <> '00/00/0000' then begin
    try
      result:=Trunc(StrToDate(fDate)) > Trunc(Now);
    except
      on EConvertError do result:=TRUE;
    end;
  end else
    result:=FALSE;
end;

function AddDaysToDate(fDate : string;fDays : integer) : string;
begin
  try
    result:=DateToStr(Trunc(StrToDate(fDate) + fDays))
  except
    on EConvertError do result:='00/00/0000';
  end;
end;

function AddDaysToAnsiDate(fDate : string;fDays : integer) : string;
begin
  Result:=AnsiDate(AddDaysToDate(EnglishDate(fDate), fDays));
end;

function DateDiff(fDate1, fDate2 : string) : integer;
// calculates the difference in days between the two dates by subtracting the second
// date from the first (i.e. fDate1 - fDate2).
begin
  try
    result:=Trunc(StrToDate(fDate1) - StrToDate(fDate2))
  except
    on EConvertError do result:=0;
  end;
end;

function AnsiDateDiff(fDate1, fDate2 : string) : integer;
// calculates the difference in days between the two dates by subtracting the second
// date from the first (i.e. fDate1 - fDate2).
begin
  try
    result:=Trunc(StrToDate(EnglishDate(fDate1)) - StrToDate(EnglishDate(fDate2)))
  except
    on EConvertError do result:=0;
  end;
end;

function DateInRange(fDate, startDate, stopDate : string) : boolean;
// returns true if fDate falls within the range of startDate and stopDate (inclusive)
var
  d1, d2, d3 : real;
begin
  try
    d1:=StrToDate(fDate);
    d2:=StrToDate(startDate);
    d3:=StrToDate(stopDate);
    result:=(Trunc(d1-d2) >= 0) AND (Trunc(d3-d1) >= 0);
  except
    on EConvertError do result:=false;
  end;
end;

function ValidDate(d : string) : boolean;
var
  AllNumberSet, NumberSet : set of char;
begin
   NumberSet := ['1','2','3','4','5','6','7','8','9'];
   AllNumberSet := ['0','1','2','3','4','5','6','7','8','9'];
   If Trim(d) <> '' then begin
     Result:=((d[1] in AllNumberSet) OR (d[2] in NumberSet)) AND
             ((d[4] in AllNumberSet) OR (d[5] in NumberSet)) AND
             ((d[7] in NumberSet) AND (d[8] in AllNumberSet) AND
             (d[9] in AllNumberSet) AND (d[10] in AllNumberSet));
   end else
     Result:=FALSE;
end;

function ValidAnsiDate(d : string) : boolean;
var
  AllNumberSet, NumberSet : set of char;
begin
   NumberSet := ['1','2','3','4','5','6','7','8','9'];
   AllNumberSet := ['0','1','2','3','4','5','6','7','8','9'];
   If Trim(d) <> '' then begin
     Result:=((d[1] in NumberSet) AND (d[2] in AllNumberSet) AND
             (d[3] in AllNumberSet) AND (d[4] in AllNumberSet)) AND

             (((d[6] in AllNumberSet) AND (d[7] in NumberSet)) OR
              ((d[6] in NumberSet) AND (d[7] in AllNumberSet))) AND

             (((d[9] in AllNumberSet) AND (d[10] in NumberSet)) OR
              ((d[9] in NumberSet) AND (d[10] in AllNumberSet)))
   end else
     Result:=FALSE;
end;

function ValidTime(t : string) : boolean;
var
  s : string;
begin
   S:=Trim(t);
   If (S = '') OR (S[1] = ':') then
      Result:=false
   else
      Result:=true;
end;

procedure EnglishYearMonthDay(dt : string;var year, month, day : string);
var
   index : integer;
begin
   month:='';
   day:='';
   year:='';
   index:=1;
   if (length(dt) > 8) AND (length(dt) <= 10) then begin
     While (dt[index] <> '/') AND (dt[index] <> '-') AND
           (dt[index] <> '.') do begin
       month:=month + dt[index];
       inc(index);
     end;
     inc(index);

     While (dt[index] <> '/') AND (dt[index] <> '-') AND
           (dt[index] <> '.') do begin
       day:=day + dt[index];
       inc(index);
     end;
     inc(index);

     While (dt[index] <> '/') AND (dt[index] <> '-') AND
           (dt[index] <> '.') AND (index <= length(dt)) do begin
       year:=year + dt[index];
       inc(index);
     end;
   end;
end;

procedure EnglishYearMonthDay(dt : string;var year, month, day : integer);
var
  fYear, fMonth, fDay : string;
begin
  EnglishYearMonthDay(dt, fYear, fMonth, fDay);
  year:=StrToInt(fYear);
  month:=StrToInt(fMonth);
  day:=StrToInt(fDay);
end;

procedure AnsiYearMonthDay(dt : string;var year, month, day : string);
var
   index : integer;
begin
   month:='';
   day:='';
   year:='';
   index:=1;
   if (length(dt) >= 8) then begin
     While (dt[index] <> '/') AND (dt[index] <> '-') AND
           (dt[index] <> '.') do begin
       year:=year + dt[index];
       inc(index);
     end;
     inc(index);

     While (dt[index] <> '/') AND (dt[index] <> '-') AND
           (dt[index] <> '.') do begin
       month:=month + dt[index];
       inc(index);
     end;
     inc(index);

     While (dt[index] <> '/') AND (dt[index] <> '-') AND
           (dt[index] <> '.') AND (dt[index] <> ' ') AND (dt[index] <> 'T') AND
           (index <= length(dt)) do begin
       day:=day + dt[index];
       inc(index);
     end;
   end;
end;

procedure AnsiYearMonthDay(dt : string;var year, month, day : integer);
var
  fYear, fMonth, fDay : string;
begin
  AnsiYearMonthDay(dt, fYear, fMonth, fDay);
  year:=StrToInt(fYear);
  month:=StrToInt(fMonth);
  day:=StrToInt(fDay);
end;

function AnsiDateToISODate(dt : string) : string;
var
  month, day, year : string;
begin
  AnsiYearMonthDay(dt,year,month,day);
  if year = '' then
    year:='0000';
  if month = '' then
    month:='00';
  if day = '' then
    day:='00';
  result:=year + month + day;
end;

function EnglishDateToISODate(dt : string) : string;
var
  month, day, year : string;
begin
  EnglishYearMonthDay(dt,year,month,day);
  if year = '' then
    year:='0000';
  if month = '' then
    month:='00';
  if day = '' then
    day:='00';
  result:=year + month + day;
end;

function EnglishDate(dt : TDate) : string;
begin
  Result:=EnglishDate(AnsiDate(dt));
end;

function EnglishDate(dt : string) : string;
var
  month, day, year : string;
begin
  AnsiYearMonthDay(dt,year,month,day);
  if year = '' then
    year:='????';
  if month = '' then
    month:='??'
  else if length(month) = 1 then
    month:='0' + month;
  if day = '' then
    day:='??'
  else if length(day) = 1 then
    day:='0' + day;
  result:=month + '/' + day + '/' + year;
end;

function EnglishDateTime(dt : string) : string;
var
  month, day, year, time : string;
begin
  AnsiYearMonthDay(dt,year,month,day);
  if year = '' then
    year:='????';
  if month = '' then
    month:='??'
  else if length(month) = 1 then
    month:='0' + month;
  if day = '' then
    day:='??'
  else if length(day) = 1 then
    day:='0' + day;
  time:=Piece(dt, ' ', 2);
  if time = '' then
    time:=Piece(dt, '@', 2);
  if time = '' then
    time:=Piece(dt, 'T', 2);
  result:=month + '/' + day + '/' + year + ' ' + GetHoursMinutes(time);
end;

function EnglishDateTime(dt : TDateTime) : string;
begin
  Result:=DateTimeToStr(dt);
end;

function ShortEnglishDateTime(dt : TDateTime) : string;
var
  fTempDtFmt, fTempTmFmt : string;
begin
  fTempDtFmt:=ShortDateFormat;
  fTempTmFmt:=LongTimeFormat;
  ShortDateFormat:='mmm dd,yy ';
  LongTimeFormat:='hh:nn';
  Result:=DateTimeToStr(dt);
  ShortDateFormat:=fTempDtFmt;
  LongTimeFormat:=fTempTmFmt;
end;

function ShortEnglishDate(dt : TDate) : string;
var
  fTempDtFmt : string;
begin
  fTempDtFmt:=ShortDateFormat;
  ShortDateFormat:='mmm dd,yy ';
  Result:=DateToStr(dt);
  ShortDateFormat:=fTempDtFmt;
end;

function ShortEnglishDateTime(fDateTimeStr : string) : string;
begin
  Result:=ShortEnglishDateTime(StrToDateTime(fDateTimeStr));
end;

function ShortEnglishDate(fDateStr : string) : string;
begin
  Result:=ShortEnglishDate(StrToDate(fDateStr));
end;

function AnsiDateTimeToShortEnglishDateTime(ansidatetime : string) : string;
begin
  Result:=ShortEnglishDateTime(AnsiDateToDateTime(ansidatetime));
end;

function AnsiDateTimeToShortEnglishDate(ansidate : string) : string;
begin
  Result:=ShortEnglishDate(AnsiDateToDate(ansidate));
end;

function ExtractTimeFromDateTime(date_time : string) : string;
var
  l, index : integer;
  tm : string;
  EndOfString : boolean;
begin
   tm:='';
   If Trim(date_time) <> '' then begin
     l:=length(date_time);
     index:=l;
     EndOfString:=false;
     while (date_time[index] <> '@') AND (NOT EndOfString) do begin
        tm:=date_time[index] + tm;
        If index = 1 then
          EndOfString:=true
        else
          dec(index);
     end;
   end;
   result:=tm;
end;

// 1-based (1 = January, 2 = February, etc.)
function GetMonthString(fMonth : integer;fFullName : boolean) : string;
begin
  if (fMonth >= 1) AND (fMonth <= 12) then begin
    if fFullName then
      Result:=MONTHS_LONG[fMonth]
    else
      Result:=MONTHS_SHORT[fMonth];
  end else
    Result:='';
end;

function LongToShortDate(dt : string) : string;
var
   l, i, index      : integer;
   month, day, year : string;
begin
  If Trim(dt) <> '' then begin
    l:=AnsiPos('@',dt)-1;
    if l = -1 then
      l:=length(dt);
    month:='';
    index:=1;
    while dt[index] <> ' ' do begin
       month:=month + dt[index];
       inc(index);
    end;

    i:=1;
    month:=UpperCase(Month);
    While (StrLComp(PChar(month), PChar(GetMonthString(i, TRUE)), 3) <> 0) AND (i < 12) do
       inc(i);
    str(i,month);
    if length(month) = 1 then
       month:='0' + month;

    inc(index);

    day:='';
    while dt[index] <> ',' do begin
       day:=day + dt[index];
       inc(index);
    end;
    inc(index);
    year:='';
    For i:=index to l do
       year:=year + dt[i];
    year:=trim(year);

    result:=month + '/' + day + '/'+ year;
  end else
    Result:='';
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

function AnsiDateToDateTime(fAnsiDate : string) : TDateTime;
begin
  Result:=StrToDateTime(EnglishDateTime(fAnsiDate));
end;

function AnsiDateToDate(fAnsiDate : string) : TDate;
begin
  Result:=StrToDate(EnglishDate(fAnsiDate));
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

function GetMostRecentDate(list : string) : string;
// returns the most recent date from a list of MUMPS stored dates that are either in the
// inverse date format or normal date format (i.e. cyymmdd.hhmm)
var
   IENList : TStringList;
   n       : integer;
   s       : string;
begin
   // stuff the list of IEN's into a TStringList
   IENList:=TStringList.create;
   IENList.text:=list;
   n:=IENList.count;
   // sorts the strings in ascending order
   If n > 0 then begin
      IENList.sort;
      s:=IENList.strings[0];
      // check to see if the IEN's are in inverse date format
      If (s[1] = '6') OR (s[1] = '7') OR (s[1] = '8') then
         Result:=IENList.strings[0] // if inverse date format then return the topmost value
      else
         Result:=IENList.strings[n-1]; // otherwise return the bottom value
   end else
      Result:='';
   IENList.free;
end;

function GetMostRecentDateIndex(list : string) : integer;
// returns the index of the most recent date from a list of MUMPS stored dates that
// are either in the inverse date format or normal date format (i.e. cyymmdd.hhmm)
var
   IENList,
   TempList  : TStringList;
   n         : integer;
   s,temp    : string;
begin
   // stuff the list of IEN's into a TStringList
   IENList:=TStringList.create;
   IENList.text:=list;
   // a temp list will maintain the unsorted list
   TempList:=TStringList.create;
   TempList.text:=list;
   n:=IENList.count;
   // sorts the strings in ascending order
   If n > 0 then begin
      IENList.sort;
      s:=IENList.strings[0];
      // check to see if the IEN's are in inverse date format
      If (s[1] = '6') OR (s[1] = '7') OR (s[1] = '8') then
         temp:=IENList.strings[0] // if inverse date format then return the topmost value
      else
         temp:=IENList.strings[n-1]; // otherwise return the bottom value
      Result:=TempList.IndexOf(temp); // return the position of the first occurrence of temp
   end else
      Result:=-1;
   IENList.free;
   TempList.free;
end;

(************************* FILEMAN DATE FUNCTIONS *************************)
function IsFMDate(const x: string): Boolean;
var
  i: Integer;
begin
  Result := False;
  if Length(x) <> 7 then
    Exit;
  for i := 1 to 7 do
    if not (x[i] in ['0'..'9']) then
      Exit;
  Result := True;
end;

function DateTimeToFMDateTime(ADateTime: TDateTime): double;
{ converts a Delphi date/time type to a Fileman date/time (type double) }
var
  y, m, d, h, n, s, l: Word;
  DatePart,TimePart: Integer;
begin
  DecodeDate(ADateTime, y, m, d);
  DecodeTime(ADateTime, h, n, s, l);
  DatePart := ((y-1700)*10000) + (m*100) + d;
  TimePart := (h*10000) + (n*100) + s;
  Result :=  DatePart + (TimePart / 1000000);
end;

function AnsiDateTimeStrToFMDateTime(ADateTimeStr: String): double;
{ converts a date/time string to a Fileman date/time (type double) }
var
  fDT : TDateTime;
begin
  fDT:=AnsiDateToDateTime(ADateTimeStr);
  Result:=DateTimeToFMDateTime(fDT);
end;

function EnglishDateTimeStrToFMDateTime(ADateTimeStr: String): double;
{ converts a date/time string to a Fileman date/time (type double) }
var
  fDT : TDateTime;
begin
  fDT:=StrToDateTime(ADateTimeStr);
  Result:=DateTimeToFMDateTime(fDT);
end;

function FMDateTimeToDateTime(ADateTime: double): TDateTime;
{ converts a Fileman date/time (type double) to a Delphi date/time }
var
  ADate, ATime: TDateTime;
  DatePart, TimePart: string;
begin
  DatePart := Piece(FloatToStrF(ADateTime, ffFixed, 14, 6), '.', 1);
  TimePart := Piece(FloatToStrF(ADateTime, ffFixed, 14, 6), '.', 2) + '000000';
  if Length(DatePart) <> 7 then raise EFMDateTimeError.Create('Invalid Fileman Date');
  if Copy(TimePart, 1, 2) = '24' then TimePart := '23595959';
  ADate := EncodeDate(StrToInt(Copy(DatePart, 1, 3)) + 1700,
                      StrToInt(Copy(DatePart, 4, 2)),
                      StrToInt(Copy(DatePart, 6, 2)));
  ATime := EncodeTime(StrToInt(Copy(TimePart, 1, 2)),
                      StrToInt(Copy(TimePart, 3, 2)),
                      StrToInt(Copy(TimePart, 5, 2)), 0);
  Result := ADate + ATime;
end;

function FMDateTimeToAnsiDateTime(ADateTime: double): String;
{converts a Fileman date/time (type double) to a string in ANSI date/time format}
begin
  Result:=AnsiDateTime(FMDateTimeToDateTime(ADateTime));
end;

function FMDateTimeOffsetBy(ADateTime: double; DaysDiff: Integer): double;
{ adds / subtracts days from a Fileman date/time and returns the offset Fileman date/time }
var
  Julian: TDateTime;
begin
  Julian := FMDateTimeToDateTime(ADateTime);
  Result := DateTimeToFMDateTime(Julian + DaysDiff);
end;

function FormatFMDateTime(AFormat: string; ADateTime: double): string;
{ formats a Fileman Date/Time using (mostly) the same format string as Delphi FormatDateTime }
var
  x: string;
  y, m, d, h, n, s: Integer;

  function TrimFormatCount: Integer;
  { delete repeating characters and count how many were deleted }
  var
    c: Char;
  begin
    Result := 0;
    c := AFormat[1];
    repeat
      Delete(AFormat, 1, 1);
      Inc(Result);
    until CharAt(AFormat, 1) <> c;
  end;

begin {FormatFMDateTime}
  Result := '';
  if not (ADateTime > 0) then Exit;
  x := FloatToStrF(ADateTime, ffFixed, 15, 6) + '0000000';
  y := StrToIntDef(Copy(x,  1, 3), 0) + 1700;
  m := StrToIntDef(Copy(x,  4, 2), 0);
  d := StrToIntDef(Copy(x,  6, 2), 0);
  h := StrToIntDef(Copy(x,  9, 2), 0);
  n := StrToIntDef(Copy(x, 11, 2), 0);
  s := StrToIntDef(Copy(x, 13, 2), 0);
  while Length(AFormat) > 0 do
    case UpCase(AFormat[1]) of
    '"': begin                                                                 // literal
           Delete(AFormat, 1, 1);
           while not (CharAt(AFormat, 1) in [#0, '"']) do
           begin
             Result := Result + AFormat[1];
             Delete(AFormat, 1, 1);
           end;
           if CharAt(AFormat, 1) = '"' then Delete(AFormat, 1, 1);
         end;
    'D': case TrimFormatCount of                                               // day/date
         1: if d > 0 then Result := Result + IntToStr(d);
         2: if d > 0 then Result := Result + FormatFloat('00', d);
         end;
    'H': case TrimFormatCount of                                               // hour
         1: Result := Result + IntToStr(h);
         2: Result := Result + FormatFloat('00', h);
         end;
    'M': case TrimFormatCount of                                               // month
         1: if m > 0 then Result := Result + IntToStr(m);
         2: if m > 0 then Result := Result + FormatFloat('00', m);
         3: if m in [1..12] then Result := Result + MONTHS_SHORT[m];
         4: if m in [1..12] then Result := Result + MONTHS_LONG[m];
         end;
    'N': case TrimFormatCount of                                               // minute
         1: Result := Result + IntToStr(n);
         2: Result := Result + FormatFloat('00', n);
         end;
    'S': case TrimFormatCount of                                               // second
         1: Result := Result + IntToStr(s);
         2: Result := Result + FormatFloat('00', s);
         end;
    'Y': case TrimFormatCount of                                               // year
         2: if y > 0 then Result := Result + Copy(IntToStr(y), 3, 2);
         4: if y > 0 then Result := Result + IntToStr(y);
         end;
    else begin                                                                 // other
           Result := Result + AFormat[1];
           Delete(AFormat, 1, 1);
         end;
    end; {case}
end; {FormatFMDateTime}

function FormatFMDateTimeStr(const AFormat, ADateTime: string): string;
var
  FMDateTime: double;
begin
  Result := ADateTime;
  if IsFMDateTime(ADateTime) then
  begin
    FMDateTime := MakeFMDateTime(ADateTime);
    Result := FormatFMDateTime(AFormat, FMDateTime);
  end;
end;

function IsFMDateTime(x: string): Boolean;
var
  i: Integer;
begin
  Result := False;
  if Length(x) < 7 then Exit;
  for i := 1 to 7 do if not (x[i] in ['0'..'9']) then Exit;
  if (Length(x) > 7) and (x[8] <> '.') then Exit;
  if (Length(x) > 8) and not (x[9] in ['0'..'9']) then Exit;
  Result := True;
end;

function MakeFMDateTime(const AString: string): double;
begin
  Result := -1;
  if (Length(AString) > 0) and IsFMDateTime(AString) then Result := StrToFloat(AString);
end;

procedure SetListFMDateTime(AFormat: string; AList: TStringList; ADelim: Char;
                            PieceNum: Integer; KeepBad: boolean = FALSE);
var
  i: Integer;
  s, x, x1: string;

begin
  for i := 0 to AList.Count - 1 do
  begin
    s := AList[i];
    x := Piece(s, ADelim, PieceNum);
    if Length(x) > 0 then
    begin
      x1 := FormatFMDateTime(AFormat, MakeFMDateTime(x));
      if(x1 <> '') or (not KeepBad) then
        x := x1;
    end;
    SetPiece(s, ADelim, PieceNum, x);
    AList[i] := s;
  end;
end;

function DateToXSDate(fDate : TDate) : TXSDate;
var
   year, month, day     : word;
begin
  DecodeDate(fDate, year, month, day);
  Result:=TXSDate.Create;
  Result.Year:=year;
  Result.Month:=month;
  Result.Day:=day;
end;

function DateTimeToXSDateTime(fDateTime : TDateTime) : TXSDateTime;
var
   year, month, day     : word;
   hour, min, sec, msec : word;
begin
  DecodeDate(fDateTime, year, month, day);
  DecodeTime(fDateTime, hour, min, sec, msec);
  Result:=TXSDateTime.Create;
  Result.Year:=year;
  Result.Month:=month;
  Result.Day:=day;
  Result.Hour:=hour;
  Result.Minute:=min;
  Result.Second:=sec;
  Result.Millisecond:=msec;
  Result.HourOffset:=0;
  Result.MinuteOffset:=0;
end;

function XSDateToDate(fXSDate : TXSDate) : TDate;
begin
  Result:=StrToDate(EnglishDate(fXSDate.NativeToXS));
end;

function XSDateTimeToDateTime(fXSDateTime : TXSDateTime) : TDateTime;
begin
  fXSDateTime.HourOffset:=0;
  fXSDateTime.MinuteOffset:=0;
  Result:=StrToDateTime(EnglishDateTime(fXSDateTime.NativeToXS));
end;

function XSDateTimeToEnglishDateTime(fXSDateTime : TXSDateTime) : String;
var
  fXs, fDt, fTm : string;
begin
  fXSDateTime.HourOffset:=0;
  fXSDateTime.MinuteOffset:=0;
  fXs:=fXSDateTime.NativeToXS;
  fDt:=LL_StringUtils.Piece(fXs, 'T', 1);
  fTm:=LL_StringUtils.Piece(fXs, 'T', 2);
  fTm:=LL_StringUtils.Piece(fTm, '.', 1);
  Result:=EnglishDate(fDt) + ' ' + fTm;
end;

function XSDateTimeToShortEnglishDateTime(fXSDateTime : TXSDateTime) : String;
var
  fXs, fDt, fTm : string;
begin
  fXSDateTime.HourOffset:=0;
  fXSDateTime.MinuteOffset:=0;
  fXs:=fXSDateTime.NativeToXS;
  fDt:=LL_StringUtils.Piece(fXs, 'T', 1);
  fTm:=LL_StringUtils.Piece(fXs, 'T', 2);
  fTm:=LL_StringUtils.Piece(fTm, '.', 1);
  Result:=EnglishDate(fDt) + ' ' + Copy(fTm, 1, 5);
end;

{
This function checks AString for:
Legal date (accepted by StrToDate based on current SysUtils.ShortDateFormat
All numeric digits (Year assumed to be two digits)
Six-digits
Four-digits
Five-digits
Unambiguous digits consistent with the DateFormat: 13297, 23689, 72390
Ambiguous digits: 12295
Month Name
}
(*
function StrToDateEx(AString : string; AShortDateFormat : string = '') : TDateTime;
var
   DatePartSequence : array[0..2] of string[4];
   MonthIndex, DayIndex, YearIndex : System.Integer;
   RawMask : string;
   Month, Day, Year : System.Integer;

   function IsLegalDay(ADay, AMonth, AYear : Integer) : Boolean;
   begin
       if (ADay >= 1) and (ADay <= MonthDays[IsLeapYear(AYear)][AMonth]) then
           Result := True
       else
           Result := False;
   end;

   function IsLegalMonth(AMonth, AYear : Integer) : Boolean;
   begin
       if (AMonth >= 1) and (AMonth <= 12) then
           Result := True
       else
           Result := False;
   end;

   procedure LoadAndStrip(var Target : System.Integer; Start, Len : Integer);
   begin
       Target := StrToIntDef(Copy(AString, Start, Len), 0);
       Delete(AString, Start, Len);
   end;

   procedure Parse5Digits;
       {       Month, Day, Year : Integer are global to the containing proc  }

       function GrabBeginning(var Target : System.Integer; Code : string) : Boolean;
       begin
           if DatePartSequence[0] = Code then
           begin
               LoadAndStrip(Target, 1, 2);
               Result := True;
           end
           else
               Result := False;
       end;

       function GrabEnd(var Target : System.Integer; Code : string) : Boolean;
       begin
           if DatePartSequence[2] = Code then
           begin
               LoadAndStrip(Target, 4, 2);
               Result := True;
           end
           else
               Result := False;
       end;

   begin
       Month := 0;
       Day := 0;
       Year := 0;

       if not (GrabEnd(Year, 'YY') or GrabEnd(Month, 'MM') or GrabEnd(Day, 'DD')) then
           ;
       if not (GrabBeginning(Year, 'YY') or GrabBeginning(Month, 'MM') or GrabBeginning(Day, 'DD')) then
           ;
       if Length(AString) = 1 then
       begin
           if MonthIndex = 2 then
               LoadAndStrip(Month, 1, 1)
           else if DayIndex = 2 then
               LoadAndStrip(Day, 1, 1);
       end
       else if Year = 0 then
       //
       else if (DatePartSequence[0] = 'D') and (DatePartSequence[1] = 'M') then
       begin
           if IsLegalDay(StrToIntDef(Copy(AString, 1, 2), 0), StrToIntDef(Copy(AString, 3, 1), 0), Year) and
               IsLegalMonth(StrToIntDef(Copy(AString, 3, 1), 0), Year) then
           begin
               LoadAndStrip(Day, 1, 2);
               LoadAndStrip(Month, 3, 1);
           end
           else if IsLegalDay(StrToIntDef(Copy(AString, 1, 1), 0), StrToIntDef(Copy(AString, 2, 2), 0), Year) and
               IsLegalMonth(StrToIntDef(Copy(AString, 2, 2), 0), Year) then
           begin
               LoadAndStrip(Day, 1, 1);
               LoadAndStrip(Month, 2, 2);
           end;
       end
       else if (DatePartSequence[1] = 'D') and (DatePartSequence[0] = 'M') then
       begin
           if IsLegalDay(StrToIntDef(Copy(AString, 3, 1), 0), StrToIntDef(Copy(AString, 1, 2), 0), Year) and
               IsLegalMonth(StrToIntDef(Copy(AString, 1, 2), 0), Year) then
           begin
               Day := StrToIntDef(Copy(AString, 3, 1), 0);
               Month := StrToIntDef(Copy(AString, 1, 2), 0);
           end
           else if IsLegalDay(StrToIntDef(Copy(AString, 2, 2), 0), StrToIntDef(Copy(AString, 1, 1), 0), Year) and
               IsLegalMonth(StrToIntDef(Copy(AString, 1, 1), 0), Year) then
           begin
               Day := StrToIntDef(Copy(AString, 2, 2), 0);
               Month := StrToIntDef(Copy(AString, 1, 1), 0);
           end
       end;
   end;

   procedure SetIndex(var Index : System.Integer; Code : string);
   var
       Counter : System.Integer;
   begin
       for Counter := 0 to 2 do
       begin
           if DatePartSequence[Counter][1] = Code then
           begin
               Index := Counter;
               Break;
           end;
       end;
   end;

var
   Pointer, Counter : System.Integer;
   ThisString : string;
   BreakArray : TStringList;
   ThisCentury : System.Integer;
begin
   if AShortDateFormat = '' then
   try
       Result := StrToDate(AString);
       Exit;
   except
       on EConvertError do
           ;
   end;

   Result := 0.0;

   Month := 0;
   Day := 0;
   Year := 0;

   if AShortDateFormat = '' then
       RawMask := SysUtils.ShortDateFormat                                     //  from Windows LOCALE_SSHORTDATE
   else
       RawMask := AShortDateFormat;                                            //  e.g. mm/dd/yy

   while Pos(DateSeparator, RawMask) > 0 do
       Delete(RawMask, Pos(SysUtils.DateSeparator, RawMask), 1);

   Pointer := 2;
   Counter := 0;
   DatePartSequence[0] := UpCase(RawMask[1]);
   while Pointer <= Length(RawMask) do
   begin
       if RawMask[Pointer] = RawMask[Pointer - 1] then
           DatePartSequence[Counter] := DatePartSequence[Counter] + UpCase(RawMask[Pointer])
       else
       begin
           Inc(Counter);
           DatePartSequence[Counter] := UpCase(RawMask[Pointer]);
       end;
       Inc(Pointer);
   end;

   SetIndex(DayIndex, 'D');
   SetIndex(MonthIndex, 'M');
   SetIndex(YearIndex, 'Y');

   BreakArray := TStringList.Create;
   try
       BreakApart(AString, SysUtils.DateSeparator + ':/-., ', BreakArray);
       if BreakArray.Count = 3 then
       begin
           for Counter := 0 to 2 do
           begin
               if StrToIntDef(BreakArray[Counter], -1) = -1 then
               begin
                   MonthIndex := Counter;
                   if Counter = 1 then
                   begin
                       if (BreakArray[0][1] = '0') then
                       begin
                           YearIndex := 0;
                           DayIndex := 2;
                       end
                       else
                       begin
                           YearIndex := 2;
                           DayIndex := 0;
                       end
                   end
                   else if Counter = 0 then
                   begin
                       DayIndex := 1;
                       YearIndex := 2;
                   end
                   else
                   begin
                       DayIndex := 1;
                       YearIndex := 0;
                   end;
                   Break;
               end;
           end;

           for Counter := 0 to 2 do
           begin
               if Counter = YearIndex then
               begin
                   Year := StrToIntDef(BreakArray[Counter], 0);
                   if Year < 50 then
                   begin
                       ThisCentury := StrToInt(FormatDateTime('yyyy', Date));
                       Inc(Year, ((ThisCentury div 100) + 1) * 100);
                   end
                   else if Year < 100 then
                   begin
                       ThisCentury := StrToInt(FormatDateTime('yyyy', Date));
                       Inc(Year, (ThisCentury div 100) * 100);
                   end;
               end

               else if Counter = MonthIndex then
               begin
                   Month := StrToIntDef(BreakArray[Counter], 0);
                   if Month = 0 then
                   begin
                       for Pointer := 1 to 12 do
                       begin
                           if SameText(BreakArray[Counter], FormatDateTime('mmm', EncodeDate(1000, Pointer, 1))) then
                           begin
                               Month := Pointer;
                               Break;
                           end;
                           if SameText(BreakArray[Counter], FormatDateTime('mmmm', EncodeDate(1000, Pointer, 1))) then
                           begin
                               Month := Pointer;
                               Break;
                           end;
                       end;
                   end;
               end

               else if Counter = DayIndex then
                   Day := StrToIntDef(BreakArray[Counter], 0);
           end;
       end;
       AString := StickTogether(BreakArray, '');
   finally
       BreakArray.Free;
   end;

   if (Year <> 0) and (Month <> 0) and (Day <> 0) then

       //      jump to the bottom

   else if StrToIntDef(AString, 0) = 0 then
       Exit

   else if (Length(AString) = 4) then
   begin
       for Counter := 0 to 2 do
           if Counter = YearIndex then
               LoadAndStrip(Year, 1, 2)
           else if Counter = MonthIndex then
               LoadAndStrip(Month, 1, 1)
           else if Counter = DayIndex then
               LoadAndStrip(Day, 1, 1);
   end
   else if (Length(AString) = 6) then
   begin
       ThisString := AString;
       for Counter := 0 to 2 do
       begin
           if Counter = YearIndex then
               LoadAndStrip(Year, 1, 2)
           else if Counter = MonthIndex then
               LoadAndStrip(Month, 1, 2)
           else if Counter = DayIndex then
               LoadAndStrip(Day, 1, 2);
       end;

       if (not IsLegalMonth(Month, Year)) or
           (not IsLegalDay(Day, Month, Year)) or
           (DatePartSequence[YearIndex] = 'YYYY') then
       begin
           AString := ThisString;
           for Counter := 0 to 2 do
           begin
               if Counter = YearIndex then
                   LoadAndStrip(Year, 1, 4)
               else if Counter = MonthIndex then
                   LoadAndStrip(Month, 1, 1)
               else if Counter = DayIndex then
                   LoadAndStrip(Day, 1, 1);
           end
       end;
   end
   else if (Length(AString) = 8) then
   begin
       for Counter := 0 to 2 do
       begin
           if Counter = YearIndex then
               LoadAndStrip(Year, 1, 4)
           else if Counter = MonthIndex then
               LoadAndStrip(Month, 1, 2)
           else if Counter = DayIndex then
               LoadAndStrip(Day, 1, 2);
       end;
   end
   else if (Length(AString) = 5) then
       Parse5Digits;

   if (Year > 0) and (Month > 0) and (Day > 0) then
       Result := EncodeDate(Year, Month, Day)
   else
       raise EConvertError.Create(AString + ' is not a valid date');
end;
*)
end.

