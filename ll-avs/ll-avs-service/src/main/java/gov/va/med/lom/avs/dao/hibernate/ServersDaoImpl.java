package gov.va.med.lom.avs.dao.hibernate;

import java.util.List;

import gov.va.med.lom.avs.dao.ServersDao;
import gov.va.med.lom.avs.model.Server;

import gov.va.med.lom.jpa.foundation.dao.impl.BaseEntityDaoJpa;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless(name="gov.va.med.lom.avs.dao.ServersDao")
@Local(ServersDao.class)
public class ServersDaoImpl extends BaseEntityDaoJpa<Server, Long> implements ServersDao {

  public List<Server> getServersByCluster(String cluster) {
    
    Query query = super.entityManager.createNamedQuery(QUERY_FIND_BY_CLUSTER);
    query.setParameter("cluster", cluster);
    
    return query.getResultList();
    
  }
  
}

