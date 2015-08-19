package gov.va.med.lom.reports.struts.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import com.opensymphony.xwork2.Preparable;

import gov.va.med.lom.reports.dashboard.web.util.WebUtils;

public class BaseDashboardReportsAction extends BaseReportsAction implements ServletRequestAware, Preparable {
  
  protected String station;
  protected String userDuz;
  protected String filters;
  protected String format;
  protected String title;
  protected boolean refresh;
  protected String cacheKey;
  protected List<String> filtersList;
  
  public void prepare() throws Exception {      
    super.prepare();
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
  
  protected List<?> mapJsonObjects(List<?> oList, Class<?> cl) {
    Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
    List<Object> ojList = new ArrayList<Object>();
    for (Object o : oList) {
      Object obj = mapper.map(o, cl);
      ojList.add(obj);
    }
    return ojList;
  }
    
  protected Object mapJsonObject(Object o, Class<?> cl) {
    Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
    return mapper.map(o, cl);
  }
  
    /* Getter/Setter Methods */
  public String getFilters() {
    return filters;
  }

  public void setFilters(String filters) {
    this.filters = filters;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }     
    
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
    super.setTitle(title);
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