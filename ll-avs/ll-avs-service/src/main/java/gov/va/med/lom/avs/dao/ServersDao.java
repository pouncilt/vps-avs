package gov.va.med.lom.avs.dao;

import java.util.List;

import gov.va.med.lom.avs.model.Server;

import gov.va.med.lom.jpa.foundation.dao.BaseEntityDao;

import javax.ejb.Local;

@Local
public interface ServersDao extends BaseEntityDao<Server, Long> {

  public static final String QUERY_FIND_BY_CLUSTER = "servers.find.byCluster";

  public abstract List<Server> getServersByCluster(String cluster);
  
}
