package gov.va.med.lom.avs.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ckoEncounterProvider")
public class EncounterProvider extends BaseModel implements Serializable {

  private Long encounterId;
  private String providerDuz;
  private String providerName;
  private String providerTitle;
  
  public EncounterProvider() {}
  
  public EncounterProvider(String providerDuz, String providerName, String providerTitle) {
    this.providerDuz = providerDuz;
    this.providerName = providerName;
    this.providerTitle = providerTitle;
  }
  
  public String getProviderDuz() {
    return providerDuz;
  }
  public void setProviderDuz(String providerDuz) {
    this.providerDuz = providerDuz;
  }
  public String getProviderName() {
    return providerName;
  }
  public void setProviderName(String providerName) {
    this.providerName = providerName;
  }
  public String getProviderTitle() {
    return providerTitle;
  }
  public void setProviderTitle(String providerTitle) {
    this.providerTitle = providerTitle;
  }
  public Long getEncounterId() {
    return encounterId;
  }
  public void setEncounterId(Long encounterId) {
    this.encounterId = encounterId;
  }
  
}