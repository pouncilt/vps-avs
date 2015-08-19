package gov.va.med.lom.avs.dao;

import gov.va.med.lom.avs.model.EncounterCache;

import gov.va.med.lom.jpa.foundation.dao.BaseEntityDao;

import javax.ejb.Local;

import java.util.List;
import java.util.Date;

@Local
public interface EncounterCacheDao extends BaseEntityDao<EncounterCache, Long> {

  public static final String QUERY_FIND_CACHE_BY_ID= "encounterCache.findById";
  public static final String QUERY_FIND_CACHE_BY_DATES = "encounterCache.findByDates";

  public abstract EncounterCache findById(Long id);
	public abstract EncounterCache find(String facilityNo, String patientDfn,  List<String> locationIens, 
      List<Double> datetimes, String docType);
	public abstract List<EncounterCache> findByDates(String facilityNo, Date beginDate, Date endDate);
	
}
