package gov.va.med.lom.reports.dashboard.web.util;

import java.util.Date;
import java.util.List;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Hashtable;

import gov.va.med.lom.javaUtils.misc.StringUtils;

public class WebUtils {
  
  private static final Long TIMEOUT = 60 * 1000L;
  private static Hashtable<String,CachedObject> cachedObjects = new Hashtable<String,CachedObject>();

  public static List<String> getFiltersList(String filters) {
    
    List<String> fList = new ArrayList<String>();
    if (filters != null) {
      String[] fArr = StringUtils.pieceList(filters, ',');
      for (int i = 0; i < fArr.length; i++) {
        String name = StringUtils.piece(fArr[i], '_', 1);
        String value = StringUtils.piece(fArr[i], '_', 2);
        if (name.trim().length() > 0) {
          fList.add(name + "=" + value);
        }
      }
    }
    return fList;
    
  }
  
  public static boolean containsFilter(List<String> filtersList, String filter) {
    for (String f : filtersList) {
      if (f.startsWith(filter)) {
        return true;
      }
    }
    return false;
  }
  
  public static List<String> removeFilter(List<String> filtersList, String filter) {
    int i = 0;
    for (String f : filtersList) {
      if (f.startsWith(filter)) {
        filtersList.remove(i);
        break;
      } else {
        i++;
      }
    }
    return filtersList;
  }
  
  public static void setCachedObject(String key, Object obj) {
    if (cachedObjects.containsKey(key)) {
      CachedObject cachedObj = cachedObjects.get(key);
      cachedObj.setTimestamp(new Date());
      cachedObj.setObj(obj);
    } else {
      CachedObject cachedObj = new CachedObject(obj);
      cachedObjects.put(key, cachedObj);
    }
  }
  
  public static Object getCachedObject(String key) {
    Object obj = null;
    CachedObject cachedObj = cachedObjects.get(key);
    if (cachedObj != null) {
      cachedObj.setTimestamp(new Date());
      obj = cachedObj.getObj();
    }
    return obj;
  }
  
  public static void cleanCachedObjects() {
    long now = new Date().getTime();
    Collection<CachedObject> col = cachedObjects.values();
    Iterator<CachedObject> it = col.iterator();
    while (it.hasNext()) {
      CachedObject co = it.next();
      if ((now - co.getTimestamp().getTime()) >  TIMEOUT) {
        it.remove();
      }
    }
  }
  
  public static boolean isCachedObject(String key) {
    //cleanCachedObjects();
    return cachedObjects.containsKey(key);
  }
  
}
