package gov.va.med.lom.reports.struts.action;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.io.ByteArrayOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.Preparable;

import gov.va.med.lom.javaUtils.misc.DateUtils;
import gov.va.med.lom.javaUtils.misc.StringUtils;

import gov.va.med.lom.json.struts.action.BaseAction;
import gov.va.med.lom.json.model.BaseJson;
import gov.va.med.lom.json.util.JsonResponse;

import gov.va.med.lom.reports.model.*;

public class BaseReportsAction extends BaseAction implements ServletRequestAware, Preparable {
    
  protected static final Log log = LogFactory.getLog(BaseReportsAction.class);
  
  protected GridData gridData;
  protected List<Aggregate> aggregates;
  protected List<GridFilter> gridFiltersList;
  protected GridFilters gridFilters;
  //protected List<SearchField> searchFieldsList;
  //protected List<SearchCombo> searchCombosList;
  protected GroupInfo groupInfo;
  protected Map<String, String> searchFieldsMap;
  // request params
  protected String reportId;
  protected String stationNo;
  protected String userDuz;
  protected String format;
  protected int start;
  protected int limit;
  protected String startDate;
  protected String endDate;
  protected String searchFields;
  protected String searchOptions;
  protected String searchCombos;
  protected String groupField;
  protected String action;
  protected String query;
  
  public void prepare() throws Exception {
    super.prepare();
    initialize("json");
  }
  
  protected void initialize(String format) {
    // initialize fields
    start = -1;
    limit = -1;
    this.format = format;
    searchFieldsMap = new HashMap<String, String>();
    // create grid data components
    aggregates = new ArrayList<Aggregate>();
    gridFiltersList = new ArrayList<GridFilter>();
    gridFilters = new GridFilters(gridFiltersList);
    // create grid data and set components
    gridData = new GridData();
    gridData.setAggregates(aggregates);
    gridData.setGridFilters(gridFilters);
    groupInfo = new GroupInfo();
    // set defaults for meta data
    gridData.getMetaData().setIdProperty("id");
    gridData.getMetaData().setTotalProperty("totalCount");
    gridData.getMetaData().setSuccessProperty("success");
    gridData.getMetaData().setGroupInfo(groupInfo);
  }
  
  /*
   * Aggregates
   */
  protected List<Aggregate> getAggregates() {
    return aggregates;  
  }
  
  protected void addAggregate(Aggregate aggregate) {
    aggregates.add(aggregate);
  }
  
  protected Aggregate addAggregate(String id, String name, String total) {
    Aggregate aggregate = new Aggregate(id, name, total);
    aggregates.add(aggregate);
    return aggregate;
  }
  
  /*
   * Grid Filters
   */
  protected GridFilters getGridFilters() {
    return gridFilters;
  }
  
  protected List<GridFilter> getGridFiltersList() {
    return gridFiltersList;
  }
  
  protected void addGridFilter(GridFilter gridFilter) {
    gridFiltersList.add(gridFilter);
  }
      
  protected GridFilter addNumericGridFilter(String dataIndex) {
    GridFilter gridFilter = new GridFilter(GridFilter.NUMERIC, dataIndex);
    gridFiltersList.add(gridFilter);
    return gridFilter;
  }
  
  protected GridFilter addStringGridFilter(String dataIndex) {
    GridFilter gridFilter = new GridFilter(GridFilter.STRING, dataIndex);
    gridFiltersList.add(gridFilter);
    return gridFilter;
  }
  
  protected GridFilter addDateGridFilter(String dataIndex) {
    GridFilter gridFilter = new GridFilter(GridFilter.DATE, dataIndex);
    gridFiltersList.add(gridFilter);
    return gridFilter;
  }
  
  protected GridFilter addListGridFilter(String dataIndex, List<String> options) {
    GridFilter gridFilter = new GridFilter(dataIndex, options);
    gridFiltersList.add(gridFilter);
    return gridFilter;
  }
  
  protected GridFilter addBooleanGridFilter(String dataIndex) {
    GridFilter gridFilter = new GridFilter(GridFilter.BOOLEAN, dataIndex);
    gridFiltersList.add(gridFilter);
    return gridFilter;
  }  
  
  /*
   * Grid Data Properties
   */
  protected GridData getGridData() {
    return gridData;
  }
  
  protected void setTotalCount(int totalCount) {
    gridData.setTotalCount(totalCount);
  }
  
  protected void setRoot(Collection root) {
    gridData.setRoot(root);
    gridData.setTotalCount(root.size());
  }
  
  protected void setSuccess(boolean success) {
    gridData.setSuccess(success);
  }
  
  /*
   * Grid Meta Data
   */
  protected GridMetaData getGridMetaData() {
    return gridData.getMetaData();
  }
  
  protected void setGridMetaData(GridMetaData gridMetaData) {
    gridData.setMetaData(gridMetaData);
  }
  
  protected GridMetaData setGridMetaData(String idProperty, String title, String totalProperty, 
                                         String successProperty, int autoExpandColumn) {
    gridData.getMetaData().setIdProperty(idProperty);
    gridData.getMetaData().setTitle(title);
    gridData.getMetaData().setTotalProperty(totalProperty);
    gridData.getMetaData().setSuccessProperty(successProperty);
    gridData.getMetaData().setAutoExpandColumn(autoExpandColumn);
    return gridData.getMetaData();
  }
  
  protected void setTitle(String title) {
    gridData.getMetaData().setTitle(title);
  }
  
  protected void setSubtitle(String subtitle) {
    gridData.getMetaData().setSubtitle(subtitle);
  }
  
  protected void setAutoExpandColumn(int autoExpandColumn) {
    gridData.getMetaData().setAutoExpandColumn(autoExpandColumn);
  }
  
  protected void setPagingStart(int start) {
    gridData.getMetaData().setStart(start);
  }
  
  protected void setPagingLimit(int limit) {
    gridData.getMetaData().setLimit(limit);
  }
  
  protected void setPrint(String print) {
    gridData.getMetaData().setPrint(print);
  }
  
  protected void setGroupByField(String groupField) {
    gridData.getMetaData().getGroupInfo().setGroupField(groupField);
  }
  
  protected void addField(String dataIndex) {
    gridData.getMetaData().addField(dataIndex);
  }
  
  /*
   * Sort Info
   */
  protected void setSortInfo(List<SortInfo> sortInfo) {
    gridData.getMetaData().setSortInfo(sortInfo);
  }
  
  protected List<SortInfo> addSortInfo(String field, String direction) {
    List<SortInfo> sortInfoList = gridData.getMetaData().getSortInfo();
    SortInfo sortInfo = new SortInfo(field, direction);
    sortInfoList.add(sortInfo);
    setSortInfo(sortInfoList);
    return sortInfoList;
  }
  
  /*
   * Grid Columns
   */
  protected List<GridColumn> getGridColumns() {
    return gridData.getColumns();
  }

  protected void setGridColumns(List<GridColumn> columns) {
    gridData.setColumns(columns);
  }
  
  protected void addGridColumn(GridColumn gridColumn) {
    gridData.getColumns().add(gridColumn);
  }
  
  protected GridColumn addGridColumn(String header, String dataIndex, boolean sortable, 
                                     boolean menuDisabled, int width, String align) {
    GridColumn gridColumn = new GridColumn(header, dataIndex, sortable, menuDisabled, width, align);
    gridData.getColumns().add(gridColumn);
    return gridColumn;
  }
    
  protected GridColumn addGridColumn(String header, String dataIndex, String xtype, String format, 
                                     boolean sortable, boolean menuDisabled, int width, String align) {
    GridColumn gridColumn = new GridColumn(header, dataIndex, xtype, format, sortable, menuDisabled, width, align);
    gridData.getColumns().add(gridColumn);
    return gridColumn;
  }
  
  /*
   * Data Response Methods
   */
  protected String sendPdf(ByteArrayOutputStream baos) {
    JsonResponse.flushPdf(getServletResponse(), baos);
    return SUCCESS;
  }
  
  protected String sendPdf(ByteArrayOutputStream baos, String filename) {
    JsonResponse.flushPdf(getServletResponse(), filename, baos);
    return SUCCESS;
  }
  
  protected String sendCsv(String text, String filename) {
    JsonResponse.flushCsv(getServletResponse(), filename, text);
    return SUCCESS;
  }
  
  protected String sendText(String filename, String text) {
    JsonResponse.flushText(getServletResponse(), filename, text);
    return SUCCESS;
  }
  
  protected String sendJson() {
    gridData.buildFieldList();
    setJson(gridData);
    return SUCCESS;
  }
  
  protected String sendJson(Collection col) {
    setJson(new BaseJson(col));
    return SUCCESS;    
  }
  
  protected String sendJsonNoRoot() {
    gridData.buildFieldList();
    writeJson(gridData);
    return SUCCESS;
  }
  
  protected String sendJsonNoRoot(Collection col) {
    writeJson(col);
    return SUCCESS;
  }
  
  /*
   * Misc Utility Methods
   */
  protected String formatDatesForTitle(Date startDate, Date endDate) {
    try {
      return DateUtils.formatDate(startDate, "MMM d, yyyy") + " to " + 
             DateUtils.formatDate(endDate, "MMM d, yyyy");
    } catch (Exception e) {
      return "";
    }  
  }
  
  public Date getQueryStartDate() {
    try {
      startDate +=  " 00:00:00"; 
      return DateUtils.convertEnglishDatetimeStr(startDate);
    } catch (Exception e) {
      return new Date();
    }        
  }
  
  public Date getQueryEndDate() {
    try {
      endDate +=  " 23:59:59"; 
      return DateUtils.convertEnglishDatetimeStr(endDate);
    } catch (Exception e) {
      return new Date();
    }     
  }
  
  /*
   * Struts Getter/Setters
   */
  public String getReportId() {
    return reportId;
  }

  public void setReportId(String reportId) {
    this.reportId = reportId;
  }
  
  public String getStationNo() {
    return stationNo;
  }

  public void setStationNo(String stationNo) {
    this.stationNo = stationNo;
  }

  public String getUserDuz() {
    return userDuz;
  }

  public void setUserDuz(String userDuz) {
    this.userDuz = userDuz;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public int getStart() {
    return start;
  }

  public void setStart(int start) {
    this.start = start;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }
  
  public String getEndDate() {
    return this.endDate;
  }

  public String getGroupField() {
    return groupField;
  }

  public void setGroupField(String groupField) {
    this.groupField = groupField;
    setGroupByField(groupField);
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public String getSearchFields() {
    return searchFields;
  }

  public String getSearchOptions() {
    return searchOptions;
  }
  
  public String getSearchCombos() {
    return searchCombos;
  }

  public void setSearchFields(String searchFields) {
    this.searchFields = searchFields;
    if ((searchFields != null) && (searchFields.trim() != "")) {
       String[] fields = StringUtils.pieceList(searchFields, '|');
       for (int i = 0; i < fields.length; i++) {
         String id = StringUtils.piece(fields[i], '=', 1);
         String value = StringUtils.piece(fields[i], '=', 2);
         if (!id.trim().equals("") && !value.trim().equals("")) {
           searchFieldsMap.put(id, value);
         }
       }
    }
  }

  public void setSearchCombos(String searchCombos) {
    this.searchCombos = searchCombos;
    if ((searchCombos != null) && (searchCombos.trim() != "")) {
       String[] options = StringUtils.pieceList(searchCombos, '|');
       for (int i = 0; i < options.length; i++) {
         String id = StringUtils.piece(options[i], '=', 1);
         String value = StringUtils.piece(options[i], '=', 2);
         if (!id.trim().equals("") && value.trim().equals("true")) {
           searchFieldsMap.put(id, value);
         }
       }
    }
  }   
  
  public void setSearchOptions(String searchOptions) {
    this.searchOptions = searchOptions;
    if ((searchOptions != null) && (searchOptions.trim() != "")) {
       String[] options = StringUtils.pieceList(searchOptions, '|');
       for (int i = 0; i < options.length; i++) {
         String id = StringUtils.piece(options[i], '=', 1);
         String value = StringUtils.piece(options[i], '=', 2);
         if (!id.trim().equals("") && value.trim().equals("true")) {
           searchFieldsMap.put(id, value);
         }
       }
    }
  }  
  
  public Map<String, String> getSearchFieldsMap() {
    return this.searchFieldsMap;
  }
  
}
