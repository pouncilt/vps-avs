package gov.va.med.lom.avs.dao;

import javax.ejb.Local;
import gov.va.med.lom.jpa.foundation.dao.BaseEntityDao;

import gov.va.med.lom.avs.model.Encounter;

@Local
public interface EncounterDao extends BaseEntityDao<Encounter, Long> {

}
