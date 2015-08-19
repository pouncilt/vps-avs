package gov.va.med.lom.reports.model;

public class SortInfo {
  
  private String field;
  private String direction;
  
  public static final String ASC = "ASC";
  public static final String DESC = "DESC";
  
  public SortInfo() {
    this.field = "id";
    this.direction = ASC;
  }
  
  public SortInfo(String field, String direction) {
    this.field = field;
    this.direction = direction;
  }
  
  public String getField() {
    return field;
  }
  
  public void setField(String field) {
    this.field = field;
  }
  
  public String getDirection() {
    return direction;
  }
  
  public void setDirection(String direction) {
    this.direction = direction;
  }
  
}
