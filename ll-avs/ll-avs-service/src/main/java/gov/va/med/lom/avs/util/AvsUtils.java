package gov.va.med.lom.avs.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gov.va.med.lom.javaUtils.misc.StringUtils;

public class AvsUtils {

  public static String adjustForNumericSearch(String target) {
    if (target.indexOf(';') > 0) {
      target = StringUtils.piece(target, ';', 1);
    }
    target = target.replaceAll("[^\\d.]", "");
    long iTarget = Long.valueOf(target);
    return String.valueOf(iTarget - 1);
  }  
  
  public static String delimitDoubles(List<Double> nums, boolean space) {
    Double[] d = nums.toArray(new Double[nums.size()]);
    String[] s = new String[d.length];
    for (int i = 0; i < d.length; i++) {
      s[i] = String.valueOf(d[i]);
    }
    return delimitString(s, space);
  }  
  
  public static String delimitString(List<String> lines, boolean space) {
    return delimitString(lines.toArray(new String[lines.size()]), space);
  }
  
  public static String delimitString(List<String> lines, char delim, boolean space) {
    return delimitString(lines.toArray(new String[lines.size()]), delim, space);
  }
  
  public static String delimitString(String[] lines) {
    return delimitString(lines, ',', false);
  }
  
  public static String delimitString(String[] lines, boolean postDelimSpace) {
    return delimitString(lines, ',', postDelimSpace);
  }
  
  public static String delimitString(String[] lines, char delim, boolean postDelimSpace) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0;i < lines.length;i++) {
      sb.append(lines[i]);
      if (i < lines.length-1) {
        sb.append(delim);
        if (postDelimSpace) {
          sb.append(" ");
        }
      }
    }
    return sb.toString();
  }
  
  public static List<String> delimitedStringToList(String str, char delim) {
    String[] arr = StringUtils.pieceList(str, delim);
    if ((arr.length == 1) && (arr[0].length() == 0)) {
      return new ArrayList<String>();
    } else {
      return Arrays.asList(arr);
    }
  }  
}
