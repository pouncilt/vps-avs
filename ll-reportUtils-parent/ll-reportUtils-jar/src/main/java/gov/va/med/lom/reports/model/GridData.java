package gov.va.med.lom.reports.model;

import java.util.ArrayList;
import java.util.List;

import gov.va.med.lom.json.model.BaseJson;

public class GridData extends BaseJson {

  @SuppressWarnings("rawtypes")
  private GridMetaData metaData;
  private List<GridColumn> columns;
  private List<Aggregate> aggregates;
  private GridFilters gridFilters;

  
  public GridData() {
    super();
    metaData = new GridMetaData();
    columns = new ArrayList<GridColumn>();
  }
  
  public GridMetaData getMetaData() {
    return metaData;
  }
  
  public void setMetaData(GridMetaData metaData) {
    this.metaData = metaData;
  }
  
  public List<GridColumn> getColumns() {
    return columns;
  }
  
  public void setColumns(List<GridColumn> columns) {
    this.columns = columns;
  }
  
  public void addColumn(GridColumn column) {
    columns.add(column);
  }
  
  public List<Aggregate> getAggregates() {
    return aggregates;
  }
  
  public void setAggregates(List<Aggregate> aggregates) {
    this.aggregates = aggregates;
  }
  
  public void buildFieldList(){
    metaData.buildFieldList(columns);
  }

  public GridFilters getGridFilters() {
    return gridFilters;
  }

  public void setGridFilters(GridFilters gridFilters) {
    this.gridFilters = gridFilters;
  }

}
