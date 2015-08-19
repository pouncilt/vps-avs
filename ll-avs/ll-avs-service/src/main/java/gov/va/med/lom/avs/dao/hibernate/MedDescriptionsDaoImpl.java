package gov.va.med.lom.avs.dao.hibernate;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import gov.va.med.lom.jpa.foundation.dao.impl.BaseEntityDaoJpa;

import gov.va.med.lom.avs.dao.MedDescriptionsDao;
import gov.va.med.lom.avs.model.MedDescription;

@Stateless(name="gov.va.med.lom.avs.dao.MedDescriptionsDao")
@Local(MedDescriptionsDao.class)
public class MedDescriptionsDaoImpl extends BaseEntityDaoJpa<MedDescription, Long> implements MedDescriptionsDao {

  public MedDescription findByNdc(String ndc) {
   
    Query query = super.entityManager.createNamedQuery(QUERY_FIND_BY_NDC);
    query.setParameter("ndc", ndc);

    
    @SuppressWarnings("unchecked")
    List<MedDescription> list = query.getResultList();
    
    if (list == null || list.size() == 0) {
      return null;
    }

    return list.get(0);
  }
  
}

