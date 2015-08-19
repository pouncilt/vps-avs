package gov.va.med.lom.charts.struts.action;

import java.util.List;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.Preparable;

import gov.va.med.lom.reports.dashboard.web.util.WebUtils;

import gov.va.med.lom.charts.model.Chart;

public class BaseDashboardChartsAction extends BaseChartsAction implements ServletRequestAware, Preparable {
  
  protected String station;
  protected String userDuz;
  protected String filters;
  protected String title;
  protected boolean refresh;
  protected String cacheKey;
  protected List<String> filtersList;
  
  public void prepare() throws Exception {        
    super.prepare();
    // set export properties
    setExportEnabled(1);
    setExportHandler("w/s/FCExporter");
    setExportAtClient(0);
    setExportAction(Chart.DOWNLOAD);
    setExportTargetWindow("_blank");
    setExportFormat(Chart.PDF);
    setShowExportDialog(1);    
  }  
  
  protected Object getCachedData() {
    if (WebUtils.isCachedObject(cacheKey) && !refresh) {
      return WebUtils.getCachedObject(cacheKey);
    } else {
      filtersList = WebUtils.getFiltersList(filters);
      return null;
    }
  }
  
  protected void setCachedData(Object obj) {
    WebUtils.setCachedObject(cacheKey, obj);
  }
  
    /* Getter/Setter Methods */
  public String getFilters() {
    return filters;
  }

  public void setFilters(String filters) {
    this.filters = filters;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
    super.setCaption(title);
  }

  public boolean isRefresh() {
    return refresh;
  }

  public void setRefresh(boolean refresh) {
    this.refresh = refresh;
  }  
  
  protected void setCacheKey(String cacheKey) {
    this.cacheKey = cacheKey + filters; 
  }
  
  protected String getCacheKey() {
    return this.cacheKey;
  }

  public String getStation() {
    return station;
  }

  public void setStation(String station) {
    this.station = station;
  }

  public String getUserDuz() {
    return userDuz;
  }

  public void setUserDuz(String userDuz) {
    this.userDuz = userDuz;
  }
}