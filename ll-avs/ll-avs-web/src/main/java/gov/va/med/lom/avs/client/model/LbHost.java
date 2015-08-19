package gov.va.med.lom.avs.client.model;

import java.util.List;

public class LbHost {

  private String cluster;
  private String primaryHost;
  private String appPath;
  private List<String> allHosts;
  
  public LbHost(String cluster, String primaryHost, String appPath, List<String> allHosts) {
    this.cluster = cluster;
    this.primaryHost = primaryHost;
    this.appPath = appPath;
    this.allHosts = allHosts;
  }

  public String getPrimaryHost() {
    return primaryHost;
  }

  public void setPrimaryHost(String primaryHost) {
    this.primaryHost = primaryHost;
  }

  public List<String> getAllHosts() {
    return allHosts;
  }

  public void setAllHosts(List<String> allHosts) {
    this.allHosts = allHosts;
  }

  public String getAppPath() {
    return appPath;
  }

  public void setAppPath(String appPath) {
    this.appPath = appPath;
  }

  public String getCluster() {
    return cluster;
  }

  public void setCluster(String cluster) {
    this.cluster = cluster;
  }
  
}
