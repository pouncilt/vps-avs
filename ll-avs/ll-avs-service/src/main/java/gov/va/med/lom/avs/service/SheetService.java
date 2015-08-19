package gov.va.med.lom.avs.service;

import gov.va.med.lom.foundation.service.response.CollectionServiceResponse;
import gov.va.med.lom.foundation.service.response.ServiceResponse;

import gov.va.med.lom.avs.enumeration.DGroupSeq;
import gov.va.med.lom.avs.model.BasicDemographics;
import gov.va.med.lom.avs.model.EncounterCache;
import gov.va.med.lom.avs.model.EncounterInfo;
import gov.va.med.lom.avs.model.EncounterProvider;
import gov.va.med.lom.avs.model.FacilityHealthFactor;
import gov.va.med.lom.avs.model.HealthFactor;
import gov.va.med.lom.avs.model.MedDescription;
import gov.va.med.lom.avs.model.MedicationRdv;
import gov.va.med.lom.avs.model.PceData;
import gov.va.med.lom.avs.model.Procedure;
import gov.va.med.lom.avs.service.BaseService;

import gov.va.med.lom.vistabroker.patient.data.AllergiesReactants;
import gov.va.med.lom.vistabroker.patient.data.Appointment;
import gov.va.med.lom.vistabroker.patient.data.ClinicalReminder;
import gov.va.med.lom.vistabroker.patient.data.Encounter;
import gov.va.med.lom.vistabroker.patient.data.Medication;
import gov.va.med.lom.vistabroker.patient.data.Problem;
import gov.va.med.lom.vistabroker.patient.data.VitalSignMeasurement;
import gov.va.med.lom.vistabroker.security.ISecurityContext;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.SortedMap;

import javax.ejb.Local;

@Local
public interface SheetService extends BaseService {

	public abstract ServiceResponse<BasicDemographics> getBasicDemographics(
			ISecurityContext securityContext, EncounterInfo encounterInfo);
	
	public abstract CollectionServiceResponse<Encounter> getOutpatientEncounters(
			ISecurityContext securityContext, EncounterInfo encounterInfo);
	
	public abstract CollectionServiceResponse<VitalSignMeasurement> getVitals(
			ISecurityContext securityContext, EncounterInfo encounterInfo);
	
	public abstract ServiceResponse<LinkedHashMap<DGroupSeq, SortedMap<String,List<String>>>> getTodaysOrders(
			ISecurityContext securityContext, EncounterInfo encounterInfo);
	
	public abstract ServiceResponse<AllergiesReactants> getAllergies(
			ISecurityContext securityContext, EncounterInfo encounterInfo);

	public abstract CollectionServiceResponse<AllergiesReactants> getRemoteAllergies(ISecurityContext securityContext, 
	    EncounterInfo encounterInfo);
	
	public abstract CollectionServiceResponse<LinkedHashMap<Double, LinkedHashMap<Double, Appointment>>> getAppointments(
			ISecurityContext securityContext, EncounterInfo encounterInfo, Integer months);
	
	public abstract CollectionServiceResponse<Medication> getMedications(
			ISecurityContext securityContext, EncounterInfo encounterInfo);
	
	public abstract CollectionServiceResponse<MedicationRdv> getNonVAMedicationsRdv(ISecurityContext securityContext, 
	    EncounterInfo encounterInfo, Date startDate);
	
  public abstract CollectionServiceResponse<MedicationRdv> getRemoteOutpatientMedicationsRdv(
      ISecurityContext securityContext, EncounterInfo encounterInfo, Date startDate);
  
  public abstract CollectionServiceResponse<MedicationRdv> getRemoteNonVaMedicationsRdv(
      ISecurityContext securityContext, EncounterInfo encounterInfo, Date startDate);
  
  public abstract CollectionServiceResponse<Procedure> getProcedures(ISecurityContext securityContext, 
      EncounterInfo encounterInfo);
  
  public abstract CollectionServiceResponse<ClinicalReminder> getClinicalReminders(
      ISecurityContext securityContext, EncounterInfo encounterInfo);
  
  public abstract ServiceResponse<String> getClinicalReminderDetail(ISecurityContext securityContext, 
      EncounterInfo encounterInfo, String ien);
  
	public abstract CollectionServiceResponse<PceData> getPceData(ISecurityContext securityContext, EncounterInfo encounterInfo);
/*	public abstract ServiceResponse<String> saveHealthFactor(ISecurityContext securityContext, 
      String patientDfn, double fmDatetime, String locationIen, String noteIen, 
      String hfIen, String hfName);*/
	
  public abstract ServiceResponse<String> getLabResults(
	      ISecurityContext securityContext, EncounterInfo encounterInfo, int daysBack);
  
  public abstract CollectionServiceResponse<Problem> getPatientProblems(
      ISecurityContext securityContext, EncounterInfo encounterInfo);
  
  public abstract ServiceResponse<EncounterCache> getEncounterCacheById(Long id); 
  public abstract ServiceResponse<EncounterCache> getEncounterCache(String facilityNo, String patientDfn, 
      List<String> locationIens, List<Double> datetimes, String docType);
  public abstract CollectionServiceResponse<EncounterCache> getEncounterCacheByDates(String facilityNo, Date startDate, Date endDate);
  public abstract ServiceResponse<EncounterCache> saveEncounterCache(EncounterCache encounterCache);
  public abstract ServiceResponse<EncounterCache> updateEncounterCache(EncounterCache encounterCache);
  public abstract ServiceResponse<EncounterProvider> saveEncounterProvider(EncounterProvider encounterProvider);
  public abstract void deleteEncounterCache(EncounterCache encounterCache);
  
  public abstract CollectionServiceResponse<MedDescription> getMedicationDescriptions(List<String> medNdcs);

  public abstract CollectionServiceResponse<HealthFactor> 
      getPatientHealthFactors(ISecurityContext securityContext, EncounterInfo encounterInfo);
  
  public abstract CollectionServiceResponse<FacilityHealthFactor> getFacilityHealthFactors(String stationNo);
  
  //public abstract ISecurityContext getServiceSecurityContext(EncounterInfo encounterInfo);
  
  public abstract CollectionServiceResponse<String> getPatientTeamMembers(ISecurityContext securityContext, 
      EncounterInfo encounterInfo, String primaryCareTeam);
  
}
