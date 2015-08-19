package gov.va.med.lom.reports.model;

import java.util.List;

public class GridFilter {
  
  private String type;
  private String dataIndex;
  private boolean disabled;
  private List<String> options;

  public static final String NUMERIC = "numeric";
  public static final String STRING = "string";
  public static final String DATE = "date";
  public static final String LIST = "list";
  public static final String BOOLEAN = "boolean";
  
  public GridFilter() {
    this.type = STRING;
  }
  
  public GridFilter(String type, String dataIndex) {
    this.type = type;
    this.dataIndex = dataIndex;
    this.disabled = false;
  }
  
  public GridFilter(String type, String dataIndex, boolean disabled) {
    this(type, dataIndex);
    this.disabled = disabled;
  }
  
  public GridFilter(String dataIndex, List<String> options) {
    this(LIST, dataIndex);
    this.options = options;
  }
  
  public GridFilter(String dataIndex, List<String> options, boolean disabled) {
    this(dataIndex, options);
    this.disabled = disabled;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDataIndex() {
    return dataIndex;
  }

  public void setDataIndex(String dataIndex) {
    this.dataIndex = dataIndex;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public List<String> getOptions() {
    return options;
  }

  public void setOptions(List<String> options) {
    this.options = options;
  }
  
}
