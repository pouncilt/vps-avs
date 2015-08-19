package gov.va.med.lom.vistabroker.patient.data;

import java.io.Serializable;

public class CancelledAppointment implements Serializable {

  private String dfn;
  private String location;
  private double fmDatetime;
  private boolean cancelled;
  private int errorCode;
  private String errorMessage;
  private int commentCode;
  private String comment;
  
  public CancelledAppointment() {
    this.dfn = null;
    this.location = null;
    this.fmDatetime = 0.0;
    this.cancelled = false;
    this.errorCode = 0;
    this.errorMessage = null;
    this.commentCode = 0;
    this.comment = null;
  }
  
  public String getDfn() {
    return dfn;
  }

  public void setDfn(String dfn) {
    this.dfn = dfn;
  }

  public String getLocation() {
    return location;
  }
  
  public void setLocation(String location) {
    this.location = location;
  }
  
  public double getFmDatetime() {
    return fmDatetime;
  }

  public void setFmDatetime(double fmDatetime) {
    this.fmDatetime = fmDatetime;
  }

  public boolean isCancelled() {
    return cancelled;
  }

  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public int getCommentCode() {
    return commentCode;
  }

  public void setCommentCode(int commentCode) {
    this.commentCode = commentCode;
  }
  
}
