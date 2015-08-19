package gov.va.med.lom.avs.dao.hibernate;

import java.util.Date;
import java.util.List;
import java.util.LinkedHashMap;

import gov.va.med.lom.avs.dao.UsageLogDao;
import gov.va.med.lom.avs.model.UsageLog;

import gov.va.med.lom.jpa.foundation.dao.impl.BaseEntityDaoJpa;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Usage Log DAO Implementation
 */
@Stateless(name="gov.va.med.lom.avs.dao.UsageLogDao")
@Local(UsageLogDao.class)
public class UsageLogDaoImpl extends BaseEntityDaoJpa<UsageLog, Long> implements UsageLogDao {

  public UsageLog getLatestUserActivity(String stationNo, String userDuz, Date date) {
    
    StringBuffer sql = new StringBuffer("SELECT TOP 1 id, facilityNo, userDuz, patientDfn, locationIens, locationNames, ");
    sql.append("datetimes, action, data, serverName, dateCreated FROM ckoUsageLog ");
    sql.append("WHERE facilityNo=:facilityNo AND userDuz=:userDuz ");
    sql.append("AND dateCreated>:dateCreated ");
    sql.append("ORDER BY dateCreated DESC");
    
    Query q = entityManager.createNativeQuery(sql.toString());
    q.setParameter("facilityNo", stationNo);
    q.setParameter("userDuz", userDuz);
    q.setParameter("dateCreated", date);
    
    List<Object[]> results = q.getResultList();
    if (results.size() > 0) {
      UsageLog usageLog = new UsageLog();
      Object[] obj = results.get(0);
      usageLog.setId(Long.valueOf((Integer)obj[0]));
      usageLog.setFacilityNo((String)obj[1]);
      usageLog.setUserDuz((String)obj[2]);
      usageLog.setPatientDfn((String)obj[3]);
      usageLog.setLocationIens((String)obj[4]);
      usageLog.setLocationNames((String)obj[5]);
      usageLog.setDatetimes((String)obj[6]);
      usageLog.setAction((String)obj[7]);
      usageLog.setData((String)obj[8]);
      usageLog.setServerName((String)obj[9]);
      usageLog.setDateCreated((Date)obj[10]);
      return usageLog;
    }
    
    return null;
  }
  
  public LinkedHashMap<String, Integer> getServerUsageSummary(Date date) {
    
    StringBuffer sql = new StringBuffer("SELECT t.serverName, SUM(t.cnt) AS tot FROM ");
    sql.append("(SELECT serverName, facilityNo, userDuz, COUNT(*) AS cnt ");
    sql.append("FROM ckoUsageLog WHERE dateCreated>:dateCreated ");
    sql.append("GROUP BY serverName, facilityNo, userDuz) t ");
    sql.append("GROUP BY t.serverName ");
    sql.append("ORDER BY tot ASC");
    
    Query q = entityManager.createNativeQuery(sql.toString());
    q.setParameter("dateCreated", date);
    
    List<Object[]> results = q.getResultList();
    if (results.size() > 0) {
      LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
      for (Object[] obj : results) {
        map.put(String.valueOf((String)obj[0]), Integer.valueOf((Integer)obj[1]));
      }
      return map;
    }
    
    return null;
  }
  
  
}

