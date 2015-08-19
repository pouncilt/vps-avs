package gov.va.med.lom.reports.model;

import java.util.ArrayList;
import java.util.List;

public class GridMetaData {

  private String idProperty;
  private String root;
  private String totalProperty;
  private String successProperty;
  private Integer autoExpandColumn;
  private Integer start;
  private Integer limit;
  private String title;
  private String subtitle;
  private String print;
  private List<SortInfo> sortInfo;
  private GroupInfo groupInfo;

  private List<String> fields;
  
  public GridMetaData() {
    this.root = "root";
    sortInfo = new ArrayList<SortInfo>();
  }
  
  public String getIdProperty() {
    return idProperty;
  }
  
  public void setIdProperty(String idProperty) {
    this.idProperty = idProperty;
  }
  
  public String getRoot() {
    return root;
  }
  
  public void setRoot(String root) {
    this.root = root;
  }
  
  public Integer getAutoExpandColumn() {
    return autoExpandColumn;
  }
  
  public void setAutoExpandColumn(Integer autoExpandColumn) {
    this.autoExpandColumn = autoExpandColumn;
  }
  
  public String getTitle() {
    return title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getPrint() {
    return print;
  }
  
  public void setPrint(String print) {
    this.print = print;
  }
  
  public void buildFieldList(List<GridColumn> columns){
    if (fields == null) {
      fields = new ArrayList<String>();
      for(GridColumn rc : columns){
        fields.add(rc.getDataIndex());
      }
    }
  }

  public String getTotalProperty() {
    return totalProperty;
  }

  public void setTotalProperty(String totalProperty) {
    this.totalProperty = totalProperty;
  }

  public String getSuccessProperty() {
    return successProperty;
  }

  public void setSuccessProperty(String successProperty) {
    this.successProperty = successProperty;
  }

  public Integer getStart() {
    return start;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public List<SortInfo> getSortInfo() {
    return sortInfo;
  }

  public void setSortInfo(List<SortInfo> sortInfo) {
    this.sortInfo = sortInfo;
  }

  public List<String> getFields() {
    return fields;
  }

  public void setFields(List<String> fields) {
    this.fields = fields;
  }

  public void addField(String dataIndex) {
    if (fields == null) {
      fields = new ArrayList<String>();
    }
    fields.add(dataIndex);
  }
  
  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public GroupInfo getGroupInfo() {
    return groupInfo;
  }

  public void setGroupInfo(GroupInfo groupInfo) {
    this.groupInfo = groupInfo;
  }
  
}
