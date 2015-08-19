package gov.va.med.lom.avs.dao;

import javax.ejb.Local;

import gov.va.med.lom.jpa.foundation.dao.BaseEntityDao;

import gov.va.med.lom.avs.model.MedDescription;

@Local
public interface MedDescriptionsDao extends BaseEntityDao<MedDescription, Long> {
  
  public static final String QUERY_FIND_BY_NDC = "medDescriptions.findByNdc";

  public abstract MedDescription findByNdc(String ndc);
	
}
