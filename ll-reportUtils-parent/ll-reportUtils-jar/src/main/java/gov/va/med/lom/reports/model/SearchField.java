package gov.va.med.lom.reports.model;

public class SearchField {
  
  private String id;
  private String fieldLabel;
  private String action;
  private String value;

  // factory methods
  public static SearchField createSearchField(String id, String fieldLabel) {
    return new SearchField(id, fieldLabel);
  }
  
  public static SearchField createSearchField(String id, String fieldLabel, String action) {
    return new SearchField(id, fieldLabel, action);
  }
  
  public static SearchField createSearchField(String id, String fieldLabel, String action, String defaultValue) {
    return new SearchField(id, fieldLabel, action, defaultValue);
  }
  
  // constructors
  public SearchField() {
  }
  
  public SearchField(String id, String fieldLabel) {
    this.id = id;
    this.fieldLabel = fieldLabel;
  }
  
  public SearchField(String id, String fieldLabel, String action) {
    this(id, fieldLabel);
    this.action = action;
  }  
  
  public SearchField(String id, String fieldLabel, String action, String value) {
    this(id, fieldLabel, action);
    this.value = value;
  }   

  public String getFieldLabel() {
    return fieldLabel;
  }

  public void setFieldLabel(String fieldLabel) {
    this.fieldLabel = fieldLabel;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
