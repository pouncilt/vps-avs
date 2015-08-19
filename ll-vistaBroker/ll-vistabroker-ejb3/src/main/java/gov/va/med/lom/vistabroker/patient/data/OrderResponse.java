package gov.va.med.lom.vistabroker.patient.data;

import java.io.Serializable;

public class OrderResponse implements Serializable {
 
  private String ien;
  private String promptIen;
  private String promptId;
  private int instance;
  private String iValue;
  private String eValue;
  
  public OrderResponse() {
    this.iValue = "";
    this.eValue = "";
    this.instance = 1;
  }
  
  public String getPromptIen() {
    return promptIen;
  }
  public void setPromptIen(String promptIen) {
    this.promptIen = promptIen;
  }
  public String getPromptId() {
    return promptId;
  }
  public void setPromptId(String promptId) {
    this.promptId = promptId;
  }
  public int getInstance() {
    return instance;
  }
  public void setInstance(int instance) {
    this.instance = instance;
  }
  public String getiValue() {
    return iValue;
  }
  public void setiValue(String iValue) {
    this.iValue = iValue;
  }
  public String geteValue() {
    return eValue;
  }
  public void seteValue(String eValue) {
    this.eValue = eValue;
  }
  public String getIen() {
    return ien;
  }
  public void setIen(String ien) {
    this.ien = ien;
  }
  
}
