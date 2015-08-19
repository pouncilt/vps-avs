package gov.va.med.lom.avs.service;

import gov.va.med.lom.foundation.service.response.CollectionServiceResponse;
import gov.va.med.lom.foundation.service.response.ServiceResponse;

import gov.va.med.lom.avs.enumeration.DisclaimerTypeEnum;
import gov.va.med.lom.avs.enumeration.SortDirectionEnum;
import gov.va.med.lom.avs.enumeration.SortEnum;
import gov.va.med.lom.avs.enumeration.TranslationTypeEnum;
import gov.va.med.lom.avs.model.Division;
import gov.va.med.lom.avs.model.FacilityHealthFactor;
import gov.va.med.lom.avs.model.FacilityPrefs;
import gov.va.med.lom.avs.model.Language;
import gov.va.med.lom.avs.model.PvsClinicalReminder;
import gov.va.med.lom.avs.model.Service;
import gov.va.med.lom.avs.model.StringResource;
import gov.va.med.lom.avs.model.Translation;
import gov.va.med.lom.avs.model.UsageLog;
import gov.va.med.lom.avs.model.UserSettings;
import gov.va.med.lom.avs.model.VistaPrinter;
import gov.va.med.lom.avs.model.Server;
import gov.va.med.lom.avs.service.BaseService;
import gov.va.med.lom.avs.util.FilterProperty;
import gov.va.med.lom.avs.util.SheetConfig;

import gov.va.med.lom.vistabroker.lists.data.ListItem;
import gov.va.med.lom.vistabroker.patient.data.Patient;
import gov.va.med.lom.vistabroker.security.ISecurityContext;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ejb.Local;

@Local
public interface SettingsService extends BaseService {

	// AGGREGATE SHEET CONFIG
	public abstract ServiceResponse<SheetConfig> getSheetConfig(ISecurityContext securityContext, 
			String facilityNo, String locationIen, String providerDuz);

	// HEADER/FOOTER
	public abstract CollectionServiceResponse<String> getHeaderAndFooter(ISecurityContext securityContext, String facilityNo);
	public abstract ServiceResponse<Boolean> saveHeaderAndFooter(ISecurityContext securityContext, 
			String facilityNo, String language, String header, String footer);
	
	// FACILITY PREFS 
	public abstract ServiceResponse<FacilityPrefs> getFacilityPrefs(ISecurityContext securityContext, String facilityNo);
	public abstract ServiceResponse<String> getTiuTitleIenForFacility(ISecurityContext securityContext, String facilityNo);
	public abstract ServiceResponse<String> getTiuNoteTextForFacilty(ISecurityContext securityContext, String facilityNo);
	public abstract ServiceResponse<Boolean> saveTiuNoteTextForFacility(ISecurityContext securityContext, String facilityNo, String text);
	public abstract ServiceResponse<String> getServiceDuz(ISecurityContext securityContext, String facilityNo);
	public abstract ServiceResponse<Integer> getUpcomingAppointmentsRange(ISecurityContext securityContext, String facilityNo);
	public abstract ServiceResponse<Boolean> saveFacilitySetting(ISecurityContext securityContext, String facilityNo, String setting, String value);
	
	// TRANSLATIONS
	public abstract CollectionServiceResponse<Translation> fetchTranslations(ISecurityContext securityContext, 
			String facilityNo, String language, Integer start, Integer limit, SortEnum sort, 
			SortDirectionEnum dir, List<FilterProperty> filters);
	public abstract ServiceResponse<Long> fetchTranslationsCount(ISecurityContext securityContext, 
			String facilityNo, String language, List<FilterProperty> filters);
	public abstract ServiceResponse<Translation> updateTranslation(ISecurityContext securityContext, 
			String facilityNo, Long id, String translation);
	public abstract CollectionServiceResponse<String> translateStrings(ISecurityContext securityContext, 
			String facilityNo, String language, TranslationTypeEnum type, List<String> stringsToTranslate);

  // SERVICES
  public abstract CollectionServiceResponse<Service> fetchServices(ISecurityContext securityContext, String facilityNo);
  public abstract ServiceResponse<Long> fetchServicesCount(ISecurityContext securityContext, String facilityNo);
  public abstract ServiceResponse<Service> addService(ISecurityContext securityContext, 
      String facilityNo, String name, String location, String hours, String phone, String comment);
  public abstract ServiceResponse<Service> updateService(ISecurityContext securityContext, 
      String facilityNo, Long id, String name, String location, String hours, String phone, String comment);
  public abstract void deleteService(Long id);

	// DISCLAIMERS
	public abstract ServiceResponse<String> fetchDisclaimers(ISecurityContext securityContext, 
			String facilityNo, DisclaimerTypeEnum type, String ien);
	public abstract ServiceResponse<Boolean> saveDisclaimers(ISecurityContext securityContext, 
			String facilityNo, DisclaimerTypeEnum type, String ien, String text);
	public abstract CollectionServiceResponse<ListItem> searchForClinics(
			ISecurityContext securityContext, String startFrom);
	
  // PRINTERS
	public abstract CollectionServiceResponse<VistaPrinter> getVistaPrinters(
	    ISecurityContext securityContext, String facilityNo, String userDuz);
	
	// PATIENTS
  public CollectionServiceResponse<Patient> listPatientsByWard(ISecurityContext securityContext, String wardIen);
  public CollectionServiceResponse<Patient> listPatientsByClinic(ISecurityContext securityContext, String clinicIen, 
                                                                 Date startDate, Date endDate);
	
	// DIVISIONS
	public abstract CollectionServiceResponse<Division> getDivisions(ISecurityContext securityContext, String facilityNo);
	public abstract ServiceResponse<String> getFacilityNoForDivision(ISecurityContext securityContext, String divisionNo);
	public abstract ServiceResponse<Division> getDefaultDivisionForUser(ISecurityContext securityContext, String facilityNo, String userDuz);
	
	// USER CLASSES
	 //public abstract CollectionServiceResponse<String> getUserClasses(ISecurityContext securityContext, String facilityNo, String userDuz);
	
	// USER SETTINGS
	public abstract ServiceResponse<UserSettings> getUserSettings(ISecurityContext securityContext, 
	    String facilityNo, String userDuz);
	public abstract ServiceResponse<UserSettings> saveUserSettings(UserSettings userSettings);
	public abstract ServiceResponse<UserSettings> updateUserSettings(UserSettings userSettings);
	
  // STRING RESOURCES
	public abstract CollectionServiceResponse<StringResource> getAllStringResources();
  public abstract CollectionServiceResponse<StringResource> getStringResources(ISecurityContext securityContext, String facilityNo, String language);
  public abstract ServiceResponse<StringResource> getStringResource(ISecurityContext securityContext, String facilityNo, String name, String language);
  public abstract ServiceResponse<StringResource> saveStringResource(StringResource stringResource);  
  public abstract ServiceResponse<StringResource> updateStringResource(StringResource stringResource);	
	
  // LANGUAGE
  public abstract CollectionServiceResponse<Language> getLanguages();
  public abstract ServiceResponse<Language> getLanguageByAbbreviation(String abbreviation);  
  
  // HEALTH FACTORS
  public abstract CollectionServiceResponse<FacilityHealthFactor> getHealthFactorsForFacility(String facilityNo);
  public abstract CollectionServiceResponse<FacilityHealthFactor> getHealthFactorsByType(String facilityNo, String type);
  
	// USAGE LOG
	public abstract void saveUsageLog(UsageLog usageLog);
    public void saveUsageLog(String facilityNo, String userDuz, String patientDfn, String locationIens,String locationNames,
                             String dateTime, String action, String data, String serverName);



    public abstract ServiceResponse<UsageLog> getLatestUserActivity(String stationNo, String userDuz, Date date);
  public abstract ServiceResponse<LinkedHashMap<String, Integer>> getServerUsageSummary(Date date);
	
  /* Pre-Visit Summary */
  public abstract CollectionServiceResponse<PvsClinicalReminder> findClinicalRemindersByStation(String stationNo);
  
  // SERVERS
  public abstract CollectionServiceResponse<Server> fetchServers();
  public abstract CollectionServiceResponse<Server> fetchServersByCluster(String cluster);

}
