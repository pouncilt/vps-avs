package gov.va.med.lom.avs.dao;

import javax.ejb.Local;
import gov.va.med.lom.jpa.foundation.dao.BaseEntityDao;

import gov.va.med.lom.avs.model.EncounterProvider;

@Local
public interface EncounterProviderDao extends BaseEntityDao<EncounterProvider, Long> {

}
