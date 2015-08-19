package gov.va.med.lom.avs.dao.hibernate;

import gov.va.med.lom.jpa.foundation.dao.impl.BaseEntityDaoJpa;

import gov.va.med.lom.avs.dao.EncounterCacheDao;
import gov.va.med.lom.avs.model.Encounter;
import gov.va.med.lom.avs.model.EncounterCache;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import javax.persistence.Query;

/**
 * Encounter Cache DAO Implementation
 */
@Stateless(name="gov.va.med.lom.avs.dao.EncounterCacheDao")
@Local(EncounterCacheDao.class)
public class EncounterCacheDaoImpl extends BaseEntityDaoJpa<EncounterCache, Long> implements EncounterCacheDao {

  public EncounterCache findById(Long id) {
    
    Query query = super.entityManager.createNamedQuery(QUERY_FIND_CACHE_BY_ID);
    query.setParameter("id", id);
    query.setMaxResults(1);
    
    @SuppressWarnings("unchecked")
    List<EncounterCache> list = query.getResultList();
    
    if (list == null || list.size() == 0) {
      return null;
    }
    
    initResults(list);
    
    return list.get(0);
    
  }
  
  /**
   * Looks up encounter cache by facility, patient, location, and encounter date/time
   * 
   * @param facilityNo The facility's unique identifier
   * @param patientDfn The patient's unique identifier
   * @param locationIen The location's unique identifier
   * @param datetime The encounter date/time in FM format
   * @return Clinic preferences object
   */
  public EncounterCache find(String facilityNo, String patientDfn,  
      List<String> locationIens, List<Double> datetimes, String docType){
    	
    StringBuffer sql = new StringBuffer("SELECT encounterCache ")
        .append("FROM EncounterCache encounterCache ")
        .append("JOIN encounterCache.encounters encounters ")
        .append("WHERE encounterCache.facilityNo = :facilityNo ")
        .append("AND encounterCache.patientDfn = :patientDfn ")
        .append("AND encounterCache.docType = :docType ")
        .append("AND encounterCache.active = 1 ");
    
/*    System.out.println("Inside EncounterCacheDaoImpl find method");
    System.out.println("facilityNo = " + facilityNo);
    System.out.println("patientDfn = " + patientDfn);
    System.out.println("docType = " + docType);
    System.out.println("sql = " + sql.toString());*/
    
    /*  
    if (locationIens != null && locationIens.size() > 0) {
      sql.append(" AND (");
      Integer index = 0;
      for (String locationIen : locationIens) {
        if (index > 0) {
          sql.append(" OR ");
        }
        sql.append("encounters.locationIen=").append(locationIen);
        System.out.println("locationIen = " + locationIen);
        index++;
      }
      sql.append(")");
    }
    */
    
    if (datetimes != null && datetimes.size() > 0) {
      sql.append(" AND (");
      Integer index = 0;
      for (Double datetime : datetimes) {
        if (index > 0) {
          sql.append(" OR ");
        }
        sql.append(" ( ");
        sql.append("encounters.encounterDatetime>=").append(Math.floor(datetime));
        sql.append(" AND ");
        sql.append("encounters.encounterDatetime<=").append(Math.ceil(datetime));
        sql.append(" ) ");
//        sql.append("encounters.encounterDatetime=").append(datetime);
       // System.out.println("datetime = " + datetime);
        index++;
      }
      sql.append(")");
    }      
    
    Query query = super.entityManager.createQuery(sql.toString());
    query.setParameter("facilityNo", facilityNo);
    query.setParameter("patientDfn", patientDfn);
    query.setParameter("docType", docType);
    query.setMaxResults(1);
    
		@SuppressWarnings("unchecked")
    List<EncounterCache> list = query.getResultList();
		
		if (list == null || list.size() == 0) {
			return null;
		}
		
		initResults(list);
		
		return list.get(0);
		
		//EncounterCache enc = rearrangeEncounterCache(list);

	     //return enc;

  }
  
  private EncounterCache rearrangeEncounterCache(List<EncounterCache> list) {
	    EncounterCache enc = list.get(0);
	    List<Encounter> ens = enc.getEncounters();

	    int index = 0;
	    for (EncounterCache encounterCache : list) {
	    	if (encounterCache.getEncounters() != null) {
	        	Encounter en = ens.get(index);
	            for (Encounter encounter : encounterCache.getEncounters()) {
	            	en.setLocationIen(encounter.getLocationIen());
	            	en.setLocationName(encounter.getLocationName());
	            	en.setEncounterDatetime(encounter.getEncounterDatetime());
	            	en.setEncounterNoteIen(encounter.getEncounterNoteIen());
	            	en.setVisitString(encounter.getVisitString());
	            	//System.out.println("sql locationname=" + encounter.getLocationName());
	            }
	            index++;
	        }
	    }
		      
	    return enc;
  }


  
  @SuppressWarnings("unchecked")  
  public List<EncounterCache> findByDates(String facilityNo, Date startDate, Date endDate) {
    
    Query query = super.entityManager.createNamedQuery(QUERY_FIND_CACHE_BY_DATES);
    query.setParameter("facilityNo", facilityNo);
    query.setParameter("startDate", startDate);
    query.setParameter("endDate", endDate);
    
    List<EncounterCache> list = query.getResultList();
    initResults(list);
    
    return list;
    
  }
  
  private static void initResults(List<EncounterCache> list) {
    
    if (list != null) {
      for (EncounterCache encounterCache : list) {
        if (encounterCache.getEncounters() != null) {      
          for (Encounter encounter : encounterCache.getEncounters()) {
            encounter.getProviders().size();   
          }
        }
      }
    }
  }
  
}

