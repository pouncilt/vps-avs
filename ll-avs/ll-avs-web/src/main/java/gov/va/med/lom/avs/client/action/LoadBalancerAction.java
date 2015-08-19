package gov.va.med.lom.avs.client.action;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.Preparable;

import gov.va.med.lom.javaUtils.misc.StringUtils;
import gov.va.med.lom.json.struts.action.BaseAction;

import gov.va.med.lom.avs.service.ServiceFactory;
import gov.va.med.lom.avs.service.SettingsService;
import gov.va.med.lom.avs.client.model.LbHost;
import gov.va.med.lom.avs.model.Server;
import gov.va.med.lom.avs.model.UsageLog;

public class LoadBalancerAction extends BaseAction implements ServletRequestAware, Preparable {
  
  private static final long serialVersionUID = 0;
  
  private SettingsService settingsService;
  
  private String cluster;
  private String stationNo;
  private String userDuz;
  
  public void prepare() throws Exception {
    super.prepare();    
    request.getSession(true);
    this.settingsService = ServiceFactory.getSettingsService();
  }  
  
  public String getServers() throws Exception {
    List<String> allServers = new ArrayList<String>();
    
    List<Server> avsServers = null;
    if ((this.cluster == null) || this.cluster.isEmpty()) {
      avsServers = (List<Server>)this.settingsService.fetchServersByCluster("DEFAULT").getCollection();
    } else {
      avsServers = (List<Server>)this.settingsService.fetchServersByCluster(this.cluster).getCollection();
      if ((avsServers == null) || avsServers.isEmpty()) {
        avsServers = (List<Server>)this.settingsService.fetchServersByCluster("DEFAULT").getCollection();
      }
    }
    
    if ((avsServers == null) || avsServers.isEmpty()) {
      // if no avs servers configured then just return localhost
      this.cluster = "";
      allServers.add("localhost");
    } else {
      for (Server server : avsServers) {
        allServers.add(server.getHost());
      }
    }

    return writeJson(new LbHost(this.cluster, null, null, allServers));
  }
  
  public String requestServer() throws Exception {
    
    Map<String, String> serverMap = new HashMap<String, String>();
    List<String> allServers = new ArrayList<String>();
    String primaryHost = null;
    String appPath = null;
    
    GregorianCalendar cal = new GregorianCalendar();
    cal.add(Calendar.HOUR_OF_DAY, -1);
    
    List<Server> avsServers = null;
    if ((this.cluster == null) || this.cluster.isEmpty()) {
      avsServers = (List<Server>)this.settingsService.fetchServersByCluster("DEFAULT").getCollection();
    } else {
      avsServers = (List<Server>)this.settingsService.fetchServersByCluster(this.cluster).getCollection();
      if ((avsServers == null) || avsServers.isEmpty()) {
        avsServers = (List<Server>)this.settingsService.fetchServersByCluster("DEFAULT").getCollection();
      }
    }
    
    // if no avs servers configured then just return localhost
    if ((avsServers == null) || avsServers.isEmpty()) {
      allServers.add("localhost");
      return writeJson(new LbHost("", "localhost", "/avs/index.html", allServers));
    }
    
    for (Server svr : avsServers) {
      String host = svr.getHost();
      String path = svr.getPath();
      allServers.add(host);
      serverMap.put(host, path);
    }
    
    if ((this.stationNo != null) && (this.userDuz != null)) {
      // check if user has any recent AVS activity
      UsageLog usageLog = this.settingsService.getLatestUserActivity(this.stationNo, this.userDuz, cal.getTime()).getPayload();
      // if recent activity then just return the same server already being used
      if (usageLog != null) {
        // make sure server is in server map
        if (serverMap.containsKey(usageLog.getServerName())) {
          primaryHost = usageLog.getServerName();
          appPath = serverMap.get(primaryHost);
        }
      }
    }
    
    // get server load balances
    LinkedHashMap<String, Integer> serverLoadMap = this.settingsService.getServerUsageSummary(cal.getTime()).getPayload();
    if (serverLoadMap != null) {
      // iterate through server map and use that server if not in load balance map (activity of 0) 
      Iterator<String> it = serverMap.keySet().iterator();
      while (it.hasNext()) {
        String host = it.next();
        if (!serverLoadMap.containsKey(host)) {
          primaryHost = host;
          appPath = serverMap.get(primaryHost);
          break;
        }
      }    
    }
    
    // if servers list is not empty then get the first server in the load balance map 
    // (which is sorted by ascending activity level)
    if ((primaryHost == null) && (serverLoadMap != null)) {
      Set<String> set = serverLoadMap.keySet();
      Iterator<String> it = set.iterator();
      while (it.hasNext()) {
        String host = it.next();
        // make sure the host is in the server map
        if (serverMap.containsKey(host)) {
          primaryHost = host;
          appPath = serverMap.get(primaryHost);
          break;
        }          
      }
    }
    
    // if no servers have any recent activity, then select first server in list
    if (primaryHost == null) {
      Iterator<String> it = serverMap.keySet().iterator();
      while (it.hasNext()) {
        primaryHost = it.next();
        appPath = serverMap.get(primaryHost);
        break;
      }
    }
      
    return writeJson(new LbHost(this.cluster, primaryHost, appPath, allServers));
  }
  
  public String getUserDuz() {
    return userDuz;
  }

  public void setUserDuz(String userDuz) {
    this.userDuz = userDuz;
  }

  public String getStationNo() {
    return stationNo;
  }

  public void setStationNo(String stationNo) {
    this.stationNo = stationNo;
  }

  public String getCluster() {
    return cluster;
  }

  public void setCluster(String cluster) {
    this.cluster = cluster;
  }

}
