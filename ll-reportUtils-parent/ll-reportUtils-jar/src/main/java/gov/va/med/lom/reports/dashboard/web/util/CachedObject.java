package gov.va.med.lom.reports.dashboard.web.util;

import java.util.Date;

public class CachedObject {
    
  private Object obj;
  private Date timestamp;
  
  public CachedObject(Object obj) {
    this.obj = obj;
    this.timestamp = new Date();
  }
  
  public Object getObj() {
    return obj;
  }
  public void setObj(Object obj) {
    this.obj = obj;
  }
  public Date getTimestamp() {
    return timestamp;
  }
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
}
