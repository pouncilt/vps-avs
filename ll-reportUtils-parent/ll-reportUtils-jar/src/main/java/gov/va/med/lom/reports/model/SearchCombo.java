package gov.va.med.lom.reports.model;

import java.util.List;
import java.util.ArrayList;

public class SearchCombo {
  
  private String id;
  private String xtype;
  private String fieldLabel;
  private Boolean hideLabel;
  private List<SearchComboOption> options;
  private String value;

  // factory methods
  public static SearchCombo createSearchCombo(String id, String fieldLabel) {
    return new SearchCombo(id, fieldLabel);
  }
  
  public static SearchCombo createSearchCombo(String id, String fieldLabel, String defaultOptionId) {
    return new SearchCombo(id, fieldLabel, defaultOptionId);
  }  
  
  // constructors
  public SearchCombo() {
    this.options = new ArrayList<SearchComboOption>();
    this.xtype = "combo";
  }
  
  public SearchCombo(String id, String fieldLabel) {
    this();
    this.id = id;
    this.fieldLabel = fieldLabel;
    this.hideLabel = false;
  }  
  
  public SearchCombo(String id, String fieldLabel, String value) {
    this(id, fieldLabel);
    this.value = value;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Boolean isHideLabel() {
    return hideLabel;
  }

  public void setHideLabel(Boolean hideLabel) {
    this.hideLabel = hideLabel;
  }

  public String getXtype() {
    return xtype;
  }

  public void setXtype(String xtype) {
    this.xtype = xtype;
  }

  public String getFieldLabel() {
    return fieldLabel;
  }

  public void setFieldLabel(String fieldLabel) {
    this.fieldLabel = fieldLabel;
  }

  public Boolean getHideLabel() {
    return hideLabel;
  }

  public void addOption(String id, String name) {
    SearchComboOption option = new SearchComboOption();
    option.setId(id);
    option.setName(name);
    options.add(option);
  }
  
  public List<SearchComboOption> getOptions() {
    return options;
  }

  public void setOptions(List<SearchComboOption> options) {
    this.options = options;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
  
}
