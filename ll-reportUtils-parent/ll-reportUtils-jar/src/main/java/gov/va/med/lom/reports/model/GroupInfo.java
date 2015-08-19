package gov.va.med.lom.reports.model;

public class GroupInfo {
  
  private String groupField;
  private Boolean hideGroupedColumn;
  private Boolean startCollapsed;
  private String groupMode;
  private String groupDir;
  private Boolean collapsible;
  
  public static final String ASC = "ASC";
  public static final String DESC = "DESC";
  
  public GroupInfo() {
    this.hideGroupedColumn = false;
    this.startCollapsed = false;
    this.groupMode = "value";
    this.groupDir = ASC;
    this.collapsible = true;
  }

  public String getGroupField() {
    return groupField;
  }

  public void setGroupField(String groupField) {
    this.groupField = groupField;
  }

  public Boolean isHideGroupedColumn() {
    return hideGroupedColumn;
  }

  public void setHideGroupedColumn(Boolean hideGroupedColumn) {
    this.hideGroupedColumn = hideGroupedColumn;
  }

  public Boolean isStartCollapsed() {
    return startCollapsed;
  }

  public void setStartCollapsed(Boolean startCollapsed) {
    this.startCollapsed = startCollapsed;
  }

  public String getGroupMode() {
    return groupMode;
  }

  public void setGroupMode(String groupMode) {
    this.groupMode = groupMode;
  }

  public String getGroupDir() {
    return groupDir;
  }

  public void setGroupDir(String groupDir) {
    this.groupDir = groupDir;
  }

  public Boolean getCollapsible() {
    return collapsible;
  }

  public void setCollapsible(Boolean collapsible) {
    this.collapsible = collapsible;
  }
  
}
