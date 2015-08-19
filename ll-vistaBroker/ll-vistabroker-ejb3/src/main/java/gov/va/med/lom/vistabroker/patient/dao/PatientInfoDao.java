package gov.va.med.lom.vistabroker.patient.dao;

import gov.va.med.lom.javaUtils.misc.DateUtils;
import gov.va.med.lom.javaUtils.misc.StringUtils;
import gov.va.med.lom.vistabroker.dao.BaseDao;
import gov.va.med.lom.vistabroker.exception.VistaBrokerException;
import gov.va.med.lom.vistabroker.patient.data.MeansTestStatus;
import gov.va.med.lom.vistabroker.patient.data.PatientInfo;
import gov.va.med.lom.vistabroker.patient.data.PatientLocation;
import gov.va.med.lom.vistabroker.util.FMDateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PatientInfoDao extends BaseDao {
	
  // FIELDS
  private PatientInfo patientInfo;
  
  // CONSTRUCTORS
  public PatientInfoDao() {
    super();
  }
  
  public PatientInfoDao(BaseDao baseDao) {
    super(baseDao);
  }
  
  protected static final Log log = LogFactory.getLog(PatientInfoDao.class);
  
  // RPC API
  // Returns the current location of the patient
/*  public PatientLocation getCurrentPatientLocation(String dfn) throws Exception {
    setDefaultContext("OR CPRS GUI CHART");
    setDefaultRpcName("ORWPT INPLOC");
    String x = sCall(dfn);
    PatientLocation patientLocation = new PatientLocation();
    if (x.length() > 0) {
      patientLocation.setIen(StringUtils.piece(x, 1));
      patientLocation.setName(StringUtils.piece(x, 2));
      patientLocation.setService(StringUtils.piece(x, 3));
    }
    return patientLocation;
  }  */
  
/*  public MeansTestStatus getMeansTestRequired(String dfn) throws Exception {
    setDefaultContext("OR CPRS GUI CHART");
    setDefaultRpcName("DG CHK PAT/DIV MEANS TEST");
    List<String> list = lCall(dfn);
    MeansTestStatus meansTestStatus = new MeansTestStatus();
    String x = (String)list.get(0);
    meansTestStatus.setRequired(x.equals("1"));
    list.remove(0);
    StringBuffer sb = new StringBuffer();
    for(int i = 0; i < list.size(); i++)
      sb.append((String)list.get(0) + '\n');
    meansTestStatus.setMessage(sb.toString().trim());
    return meansTestStatus;
  }*/
    
	public PatientInfo getPatientInfo(String dfn) throws Exception {
		
	//System.out.println("Inside public PatientInfo getPatientInfo(String dfn)");
    //setDefaultContext("OR CPRS GUI CHART");
    setDefaultContext("VPS AVS INTERFACE");
    //setDefaultRpcName("ORWPT ID INFO");
    setDefaultRpcName("VPS ID INFO");
    patientInfo = new PatientInfo();
    // Pieces: SSN[1]^DOB[2]^SEX[3]^VET[4]^SC%[5]^WARD[6]^RM-BED[7]^NAME[8] 
		String x = sCall(dfn);
		if (!StringUtils.piece(x, 1).equals("-1")) {
      patientInfo.setDfn(dfn);
      patientInfo.setName(StringUtils.mixedCase(StringUtils.piece(x, 8)));		
      patientInfo.setSsn(StringUtils.formatSSN(StringUtils.piece(x, 1)));
      patientInfo.setSex(StringUtils.piece(x, 3));
      String dob = StringUtils.piece(x, 2);
      if (dob.length() > 0) {
        patientInfo.setDob(FMDateUtils.fmDateTimeToDate(StringUtils.toDouble(dob, 0.0)));
        try {
          patientInfo.setDobStr(DateUtils.toEnglishDate(patientInfo.getDob()));
        } catch(ParseException pe) {}
      } else {
        Calendar gc = new GregorianCalendar();
        gc.set(Calendar.YEAR, 0);
        gc.set(Calendar.MONTH, 0);
        gc.set(Calendar.DAY_OF_MONTH, 0);
        patientInfo.setDob(gc.getTime());
        patientInfo.setDobStr("");
      }
      patientInfo.setAge(DateUtils.calcAge(patientInfo.getDob()));
      patientInfo.setVeteran(StringUtils.strToBool(StringUtils.piece(x, 4), "Y"));
      patientInfo.setScPct(StringUtils.toInt(StringUtils.piece(x, 5), 0));
      patientInfo.setLocation(StringUtils.piece(x, 6));
      patientInfo.setRoomBed(StringUtils.piece(x, 7));
      patientInfo.setInpatient(patientInfo.getLocation().length() > 0);
      //setDefaultRpcName("ORWPT DIEDON");
      setDefaultRpcName("VPS DIEDON");
	    x = sCall(dfn);
      if (x.length() > 0) {
  			patientInfo.setDeceasedDate(FMDateUtils.fmDateTimeToDate(Double.valueOf(x).doubleValue()));
        try {
          patientInfo.setDeceasedDateStr(DateUtils.toEnglishDate(patientInfo.getDeceasedDate()));
        } catch(ParseException pe) {}      
      } else {
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(Calendar.YEAR, 0);
        gc.set(Calendar.MONTH, 0);
        gc.set(Calendar.DAY_OF_MONTH, 0);
        patientInfo.setDeceasedDate(gc.getTime());
        patientInfo.setDeceasedDateStr("");
      }
		}
	
/*	System.out.println("patientInfo.name = " + patientInfo.getName());
	System.out.println("patientInfo.age = " + patientInfo.getAge());
	System.out.println("patientInfo.DOB = " + patientInfo.getDobStr());*/
	
    return patientInfo;
  }
	
  public List<String> getPatientDivisions()
  {
	  List <String> results = new ArrayList <String> ();
	  setDefaultContext("VPS AVS INTERFACE");
	  setDefaultRpcName("VPS SC408");

	  try 
	  {
		  //System.out.println("Calling lcall to execute VPS SC408 RPC");
		  results = lCall();
	  } catch (VistaBrokerException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  }
	  
	  return results;
	    
  }
  
  public List<String> getPceData(String instNo)
  {
	  List <String> results = new ArrayList <String> ();
	  setDefaultContext("VPS AVS INTERFACE");
	  setDefaultRpcName("VPS SC408 INSTITUTION");

	  try 
	  {
		  //System.out.println("Calling lcall to execute VPS SC408 INSTITUTION");
		  results = lCall(instNo);
	  } catch (VistaBrokerException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  }
	  
/*	  if( results != null)
	  {
		  System.out.println("The size of results list = " + results.size());
		  
		  for( String eachResult : results)
		  {
			  System.out.println("Each result = " + eachResult);
		  }
		  
	  }
	  else
	  {
		  System.out.println("results is null");
	  }*/

	  return results;
	    
  }
  
  public List<String> getUserClasses()
  {
	  List <String> results = new ArrayList <String> ();
	  setDefaultContext("VPS AVS INTERFACE");
	  setDefaultRpcName("VPS USR CLASS");

	  try 
	  {
		  //System.out.println("Calling lcall to execute VPS USR CLASS");
		  results = lCall();
	  } catch (VistaBrokerException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  }
	  
/*	  if( results != null)
	  {
		  //System.out.println("The size of results list = " + results.size());
		  
		  for( String eachResult : results)
		  {
			  System.out.println("Each result = " + eachResult);
		  }
		  
	  }
	  else
	  {
		  System.out.println("results is null");
	  }*/

	  return results;
	    
  }
  
  public String isUserAdmin(String userDuz)
  {
	  String result = "";
	  setDefaultContext("VPS AVS INTERFACE");
	  setDefaultRpcName("VPS AVS ADMIN");

	  try 
	  {
		  //System.out.println("Calling scall to execute VPS AVS ADMIN");
		  result = sCall(userDuz);
	  } catch (VistaBrokerException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  }
	  
/*	  if( result != null)
	  {
			  System.out.println("result = " + result);
	  }
	  else
	  {
		  System.out.println("result is null");
	  }*/

	  return result;
	    
  }
  
  public List<String> getPatientTeamMembers(String patientDfn, String primaryCareTeam)
  {
	  List<String> results = new ArrayList<String>();
	  setDefaultContext("VPS AVS INTERFACE");
	  setDefaultRpcName("VPS PROVIDER TEAM POSITION");
	  
	  String[] params = new String[2];
	  
	  params[0] = patientDfn;
	  params[1] = primaryCareTeam;

	  try 
	  {
		  //System.out.println("Calling lcall to execute VPS PROVIDER TEAM POSTION");
		  results = lCall(params);
	  } catch (VistaBrokerException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  }
	  
	  if( results != null && results.get(0).contains("0^"))
	  {
		  log.error(results.get(0));
	  }
	  
	  results.remove(0);
  
	  return results;
	    
  }
  
}
