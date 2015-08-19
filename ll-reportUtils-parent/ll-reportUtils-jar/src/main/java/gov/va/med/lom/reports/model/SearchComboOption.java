package gov.va.med.lom.reports.model;

public class SearchComboOption {
  
  private String id;
  private String name;
  
  public SearchComboOption() {
    // no arg constructor
  }
  
  public SearchComboOption(String id, String name) {
    this.id = id;
    this.name = name;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

}