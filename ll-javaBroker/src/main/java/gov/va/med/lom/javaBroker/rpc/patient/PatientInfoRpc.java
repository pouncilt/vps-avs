package gov.va.med.lom.javaBroker.rpc.patient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.ParseException;

import gov.va.med.lom.javaBroker.rpc.*;
import gov.va.med.lom.javaBroker.rpc.patient.models.*;
import gov.va.med.lom.javaBroker.util.DateUtils;
import gov.va.med.lom.javaBroker.util.FMDateUtils;
import gov.va.med.lom.javaBroker.util.StringUtils;

public class PatientInfoRpc extends AbstractRpc {
	
  // FIELDS
  private PatientInfo patientInfo;
  
  // CONSTRUCTORS
  public PatientInfoRpc() throws BrokerException {
    super();
  }
  
  public PatientInfoRpc(RpcBroker rpcBroker) throws BrokerException {
    super(rpcBroker);
  }
  
  // RPC API
  // Returns the current location of the patient
/*  public synchronized PatientLocation getCurrentPatientLocation(String dfn) throws BrokerException {
    if (setContext("OR CPRS GUI CHART")) {
      setDfn(dfn);
      String x = sCall("ORWPT INPLOC", dfn);
      PatientLocation patientLocation = new PatientLocation();
      if (returnRpcResult)
        patientLocation.setRpcResult(x);
      if (x.length() > 0) {
        patientLocation.setIen(StringUtils.piece(x, 1));
        patientLocation.setName(StringUtils.piece(x, 2));
        patientLocation.setService(StringUtils.piece(x, 3));
      }
      return patientLocation;
    } else
      throw getCreateContextException("OR CPRS GUI CHART");
  } */ 
  
  /*public synchronized MeansTestStatus getMeansTestRequired(String dfn) throws BrokerException {
    if (setContext("OR CPRS GUI CHART")) {
      setDfn(dfn);
      ArrayList list = lCall("DG CHK PAT/DIV MEANS TEST", dfn);
      MeansTestStatus meansTestStatus = new MeansTestStatus();
      if (returnRpcResult) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < list.size(); i++)
          sb.append((String)list.get(i) + "\n");
        meansTestStatus.setRpcResult(sb.toString().trim());
      }
      String x = (String)list.get(0);
      meansTestStatus.setRequired(x.equals("1"));
      list.remove(0);
      StringBuffer sb = new StringBuffer();
      for(int i = 0; i < list.size(); i++)
        sb.append((String)list.get(0) + '\n');
      meansTestStatus.setMessage(sb.toString());
      return meansTestStatus;
    } else
      throw getCreateContextException("OR CPRS GUI CHART");
  }*/
    
	public synchronized PatientInfo getPatientInfo(String dfn) throws BrokerException {
      //if (setContext("OR CPRS GUI CHART")) {
      if (setContext("VPS AVS INTERFACE")) {
      setDfn(dfn);
      patientInfo = new PatientInfo();
      // Pieces: SSN[1]^DOB[2]^SEX[3]^VET[4]^SC%[5]^WARD[6]^RM-BED[7]^NAME[8] 
  		//String x = sCall("ORWPT ID INFO", getDfn());
  		String x = sCall("VPS ID INFO", getDfn());
      StringBuffer sb = null;
      if (returnRpcResult) {
        sb = new StringBuffer();
        sb.append(x + "\n");
      }
  		if (!StringUtils.piece(x, 1).equals("-1")) {
        patientInfo.setDfn(getDfn());
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
          GregorianCalendar gc = new GregorianCalendar();
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
  	    //x = sCall("ORWPT DIEDON", getDfn());
  	    x = sCall("VPS DIEDON", getDfn());
        if (returnRpcResult) {
          sb.append(x);
          patientInfo.setRpcResult(x);
        }
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
      return patientInfo;
    } else
      //throw getCreateContextException("OR CPRS GUI CHART");
      throw getCreateContextException("VPS AVS INTERFACE");
  }
  
/*  public synchronized String getPatientIcn(String dfn) throws BrokerException {
    if (setContext("ALS INTERNET RPCS")) {
	    setDfn(dfn);
	    return sCall("ALSI ICN LOOKUP", getDfn());
	    //return "";
    } else
	  throw getCreateContextException("ALS INTERNET RPCS");
  }*/	
  
  
  /*
   Given a patient DFN, ICN, or EDIPI, this Remote Procedure Call returns a 
   list of Treating Facilities, including SOURCE ID, station number, and 
   IDENTIFIER STATUS.
   
   The patient identifier will either be the PATIENT file (#2) IEN (aka 
   DFN), Integration Control Number (aka ICN) or the DOD Identifier (aka 
   EDIPI).  Following this format:
 
   Id^IdType^AssigningAuthority^AssigningFacility
 
   Examples:
 
   ICN example:   1008520438V882204^NI^USVHA^200M
   DFN example:   100000511^PI^USVHA^500
   EDIPI example: 852043888^NI^USDOD^200DOD
   
   This will return a list of treating facilities in the following format:
 
   ID^IDTYPE^AssigningAuthority^AssigningFacility^IDStatus
   */
/*  public synchronized ArrayList getCorrespondingPatientIds(String id, String idType, 
      String assigningAuthority, String assigningFacility) throws BrokerException {
    if (setContext("VAFCTF RPC CALLS")) {
      Object[] params = {id, idType, assigningAuthority, assigningFacility};
      return lCall("VAFC LOCAL GETCORRESPONDINGIDS", params); 
    } else
    throw getCreateContextException("OR CPRS GUI CHART");
  } */  
  
}
