package gov.va.med.lom.reports.model;

import java.util.List;

public class GridFilters {

	private Boolean autoReload;
	private Boolean encode;
	private String filterCls;
	private Boolean local;
	private String menuFilterText;
	private String paramPrefix;
	private Boolean showMenu;
	private String stateId;
	private Integer updateBuffer;
	private List<GridFilter> filters;
	
	public GridFilters() {
	  this.autoReload = true;
	  this.encode = false;
	  this.filterCls = "ux-filterd-column";
	  this.local = true;
	  this.menuFilterText = "Filters";
	  this.paramPrefix = "filter";
	  this.showMenu = true;
	  this.updateBuffer = 500; 
	}
	
	public GridFilters(List<GridFilter> filters) {
	  this();
	  this.filters = filters;
	}

  public Boolean isAutoReload() {
    return autoReload;
  }

  public void setAutoReload(Boolean autoReload) {
    this.autoReload = autoReload;
  }

  public String getFilterCls() {
    return filterCls;
  }

  public void setFilterCls(String filterCls) {
    this.filterCls = filterCls;
  }

  public Boolean isLocal() {
    return local;
  }

  public void setLocal(Boolean local) {
    this.local = local;
  }

  public String getMenuFilterText() {
    return menuFilterText;
  }

  public void setMenuFilterText(String menuFilterText) {
    this.menuFilterText = menuFilterText;
  }

  public String getParamPrefix() {
    return paramPrefix;
  }

  public void setParamPrefix(String paramPrefix) {
    this.paramPrefix = paramPrefix;
  }

  public Boolean isShowMenu() {
    return showMenu;
  }

  public void setShowMenu(Boolean showMenu) {
    this.showMenu = showMenu;
  }

  public String getStateId() {
    return stateId;
  }

  public void setStateId(String stateId) {
    this.stateId = stateId;
  }

  public Integer getUpdateBuffer() {
    return updateBuffer;
  }

  public void setUpdateBuffer(Integer updateBuffer) {
    this.updateBuffer = updateBuffer;
  }

  public List<GridFilter> getFilters() {
    return filters;
  }

  public void setFilters(List<GridFilter> filters) {
    this.filters = filters;
  }

  public Boolean isEncode() {
    return encode;
  }

  public void setEncode(Boolean encode) {
    this.encode = encode;
  }
	
}
