package gov.va.med.lom.reports.model;

import java.util.List;
import java.util.ArrayList;

public class GridColumn {
  
  private String plugin;
  private String header;
  private String dataIndex;
  private Boolean sortable;
  private Integer width;
  private Boolean hidden;
  private Boolean menuDisabled;
  private Boolean groupable;
  private String align;
  private Boolean resizable;
  private String tooltip;
  private Boolean editable;
  private Double flex;
  private String xtype;
  private String format; // for use with date and number columns
  private String falseText; // for use with Boolean columns
  private String trueText; // for use with Boolean columns
  private String tpl; // for use with template columns
  private String summaryType; // for use with group summary
  private String summaryRenderer; // for use with group summary
  private List<GridColumn> columns;
  
  // Column X Types
  public static final String GRID_COLUMN = "gridcolumn";
  public static final String BOOLEAN_COLUMN = "booleancolumn";
  public static final String NUMBER_COLUMN = "numbercolumn";
  public static final String DATE_COLUMN = "datecolumn";
  public static final String TEMPLATE_COLUMN = "templatecolumn";
  
  // Column plugins
  public static final String CHECKBOX_SELECTION = "CheckboxSelectionModel";
  public static final String ROW_EXPANDER = "RowExpander";
  public static final String ROW_NUMBERER = "RowNumberer";
  
  // Cell alignments
  public static final String LEFT = "left";
  public static final String CENTER = "center";
  public static final String RIGHT = "right";
  
  // Summary Types
  public static final String SUM = "sum";
  public static final String COUNT = "count";
  public static final String MAX = "max";
  public static final String MIN = "min";
  public static final String AVERAGE = "average";  
  
  public GridColumn() {
    this.xtype = GRID_COLUMN;
    this.groupable = true;
    this.sortable = true;
    this.hidden = false;
    this.menuDisabled = false;
    this.groupable = true;
    this.resizable = true;
    this.editable = true;
  }
  
  public GridColumn(String plugin) {
    this();
    this.plugin = plugin;
  }
  
  public GridColumn(String header, String dataIndex, Boolean sortable, Boolean menuDisabled, int width, String align) {
    this();
    this.header = header;
    this.dataIndex = dataIndex;
    this.sortable = sortable;
    this.menuDisabled = menuDisabled;
    this.width = width;
    this.align = align;
    this.resizable = true;
    this.hidden = false;
  }
  
  public GridColumn(String header, String dataIndex, String xtype, String format, 
                    Boolean sortable, Boolean menuDisabled, int width, String align) {
    this(header, dataIndex, sortable, menuDisabled, width, align);
    this.xtype = xtype;
    this.format = format;
  }
  
  public GridColumn(String header, String dataIndex, Boolean sortable, Boolean menuDisabled, 
                    int width, String align,String tooltip, Boolean resizable, Boolean hidden) {
    this(header, dataIndex, sortable, menuDisabled, width, align);
    this.tooltip = tooltip;
    this.resizable = resizable;
    this.hidden = hidden;
  }
  
  public String getHeader() {
    return header;
  }
  
  public void setHeader(String header) {
    this.header = header;
  }
  
  public String getDataIndex() {
    return dataIndex;
  }
  
  public void setDataIndex(String dataIndex) {
    this.dataIndex = dataIndex;
  }
  
  public Boolean isSortable() {
    return sortable;
  }
  
  public void setSortable(Boolean sortable) {
    this.sortable = sortable;
  }
  
  public Integer getWidth() {
    return width;
  }
  
  public void setWidth(Integer width) {
    this.width = width;
  }
  
  public Boolean isHidden() {
    return hidden;
  }
  
  public void setHidden(Boolean hidden) {
    this.hidden = hidden;
  }
  
  public Boolean isMenuDisabled() {
    return menuDisabled;
  }
  
  public void setMenuDisabled(Boolean menuDisabled) {
    this.menuDisabled = menuDisabled;
  }

  public String getAlign() {
    return align;
  }

  public void setAlign(String align) {
    this.align = align;
  }

  public Boolean isResizable() {
    return resizable;
  }

  public void setResizable(Boolean resizable) {
    this.resizable = resizable;
  }

  public String getTooltip() {
    return tooltip;
  }

  public void setTooltip(String tooltip) {
    this.tooltip = tooltip;
  }

  public String getPlugin() {
    return plugin;
  }

  public void setPlugin(String plugin) {
    this.plugin = plugin;
  }

  public Boolean isEditable() {
    return editable;
  }

  public void setEditable(Boolean editable) {
    this.editable = editable;
  }

  public String getXtype() {
    return xtype;
  }

  public void setXtype(String xtype) {
    this.xtype = xtype;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public String getFalseText() {
    return falseText;
  }

  public void setFalseText(String falseText) {
    this.falseText = falseText;
  }

  public String getTrueText() {
    return trueText;
  }

  public void setTrueText(String trueText) {
    this.trueText = trueText;
  }

  public String getTpl() {
    return tpl;
  }

  public void setTpl(String tpl) {
    this.tpl = tpl;
  }

  public Boolean isGroupable() {
    return groupable;
  }

  public void setGroupable(Boolean groupable) {
    this.groupable = groupable;
  }

  public String getSummaryType() {
    return summaryType;
  }

  public void setSummaryType(String summaryType) {
    this.summaryType = summaryType;
  }

  public String getSummaryRenderer() {
    return summaryRenderer;
  }

  public void setSummaryRenderer(String summaryRenderer) {
    this.summaryRenderer = summaryRenderer;
  }
  
  public void addChildColumn(GridColumn column) {
    if (columns == null) {
      columns = new ArrayList<GridColumn>();
    }
    columns.add(column);
  }
  
  public List<GridColumn> getColumns() {
    return columns;
  }

  public void setColumns(List<GridColumn> columns) {
    this.columns = columns;
  }

  public Double getFlex() {
    return flex;
  }

  public void setFlex(Double flex) {
    this.flex = flex;
  }
  
}
