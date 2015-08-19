package gov.va.med.lom.avs.dao.hibernate;

import javax.ejb.Local;
import javax.ejb.Stateless;

import gov.va.med.lom.jpa.foundation.dao.impl.BaseEntityDaoJpa;

import gov.va.med.lom.avs.dao.EncounterProviderDao;
import gov.va.med.lom.avs.model.EncounterProvider;

@Stateless(name="gov.va.med.lom.avs.dao.EncounterProviderDao")
@Local(EncounterProviderDao.class)
public class EncounterProviderDaoImpl extends BaseEntityDaoJpa<EncounterProvider, Long> implements EncounterProviderDao {

}

