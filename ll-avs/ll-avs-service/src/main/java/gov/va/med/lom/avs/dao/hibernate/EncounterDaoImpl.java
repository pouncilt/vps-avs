package gov.va.med.lom.avs.dao.hibernate;

import javax.ejb.Local;
import javax.ejb.Stateless;

import gov.va.med.lom.jpa.foundation.dao.impl.BaseEntityDaoJpa;

import gov.va.med.lom.avs.dao.EncounterDao;
import gov.va.med.lom.avs.model.Encounter;

@Stateless(name="gov.va.med.lom.avs.dao.EncounterDao")
@Local(EncounterDao.class)
public class EncounterDaoImpl extends BaseEntityDaoJpa<Encounter, Long> implements EncounterDao {

}

