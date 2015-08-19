package gov.va.med.lom.avs.dao;

import java.util.Date;
import java.util.LinkedHashMap;

import gov.va.med.lom.avs.model.UsageLog;

import gov.va.med.lom.jpa.foundation.dao.BaseEntityDao;

import javax.ejb.Local;

/**
 * Usage Log DAO object
 */
@Local
public interface UsageLogDao extends BaseEntityDao<UsageLog, Long> {

  public abstract UsageLog getLatestUserActivity(String stationNo, String userDuz, Date date);
  public abstract LinkedHashMap<String, Integer> getServerUsageSummary(Date date);
  
}
