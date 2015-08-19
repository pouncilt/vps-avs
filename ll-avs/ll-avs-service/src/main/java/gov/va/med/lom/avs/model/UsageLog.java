package gov.va.med.lom.avs.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ckoUsageLog")
public class UsageLog extends BaseModel implements Serializable {	

	private static final long serialVersionUID = 0;

	private String facilityNo;
	private String userDuz;
	private String patientDfn;
	private String locationIens;
	private String locationNames;
	private String datetimes;
	private String action;
	private String data;
	private String serverName;
	
  public String getFacilityNo() {
    return facilityNo;
  }
  public void setFacilityNo(String facilityNo) {
    this.facilityNo = facilityNo;
  }
  public String getPatientDfn() {
    return patientDfn;
  }
  public void setPatientDfn(String patientDfn) {
    this.patientDfn = patientDfn;
  }
  public String getLocationIens() {
    return locationIens;
  }
  public void setLocationIens(String locationIens) {
    this.locationIens = locationIens;
  }
  public String getAction() {
    return action;
  }
  public void setAction(String action) {
    this.action = action;
  }
  public String getData() {
    return data;
  }
  public void setData(String data) {
    this.data = data;
  }
  public String getUserDuz() {
    return userDuz;
  }
  public void setUserDuz(String userDuz) {
    this.userDuz = userDuz;
  }
  public String getLocationNames() {
    return locationNames;
  }
  public void setLocationNames(String locationNames) {
    this.locationNames = locationNames;
  }
  public String getDatetimes() {
    return datetimes;
  }
  public void setDatetimes(String datetimes) {
    this.datetimes = datetimes;
  }
  public String getServerName() {
    return serverName;
  }
  public void setServerName(String serverName) {
    this.serverName = serverName;
  }
	
}
