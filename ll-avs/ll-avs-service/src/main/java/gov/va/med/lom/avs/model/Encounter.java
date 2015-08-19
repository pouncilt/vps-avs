package gov.va.med.lom.avs.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ckoEncounter")
public class Encounter extends BaseModel implements Serializable {

  private Long encounterCacheId;
  private List<EncounterProvider> providers;
  private double encounterDatetime;
  private String encounterNoteIen;
  private String visitString;
  private String locationIen;
  private String locationName;
  
  @OneToMany(mappedBy="encounterId", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
  public List<EncounterProvider> getProviders() {
    return providers;
  }

  public void setProviders(List<EncounterProvider> providers) {
    this.providers = providers;
  }

  public double getEncounterDatetime() {
    return encounterDatetime;
  }

  public void setEncounterDatetime(double encounterDatetime) {
    this.encounterDatetime = encounterDatetime;
  }

  public String getVisitString() {
    return visitString;
  }

  public void setVisitString(String visitString) {
    this.visitString = visitString;
  }

  public String getEncounterNoteIen() {
    return encounterNoteIen;
  }

  public void setEncounterNoteIen(String encounterNoteIen) {
    this.encounterNoteIen = encounterNoteIen;
  }

  public String getLocationIen() {
    return locationIen;
  }

  public void setLocationIen(String locationIen) {
    this.locationIen = locationIen;
  }

  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }

  public Long getEncounterCacheId() {
    return encounterCacheId;
  }

  public void setEncounterCacheId(Long encounterCacheId) {
    this.encounterCacheId = encounterCacheId;
  }

}