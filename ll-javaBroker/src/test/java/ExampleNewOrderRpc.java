import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

import gov.va.med.lom.javaBroker.rpc.user.*;
import gov.va.med.lom.javaBroker.rpc.user.models.*;
import gov.va.med.lom.javaBroker.rpc.patient.*;
import gov.va.med.lom.javaBroker.rpc.patient.models.*;
import gov.va.med.lom.javaBroker.rpc.BrokerException;
import gov.va.med.lom.javaBroker.rpc.RpcBroker;
import gov.va.med.lom.javaBroker.util.FMDateUtils;

public class ExampleNewOrderRpc {
  
  public static void main(String[] args) throws Exception {
    // If user didn't pass the patient's ssn and name of the auth properties file, then print usage and exit
    String server = null;
    int port = 0;
    String access = null;
    String verify = null;
    if (args.length != 1) {
      System.out.println("Usage: java ExampleNewOrderRpc AUTH_PROPS");
      System.out.println("where AUTH_PROPS is the name of a properties file containing VistA sign-on info.");
      System.exit(1);
    } else {
      ResourceBundle res = ResourceBundle.getBundle(args[0]);
      server = res.getString("server");
      port = Integer.valueOf(res.getString("port")).intValue();
      access = res.getString("accessCode");
      verify = res.getString("verifyCode");
    }
    try {
      // Call the static signon method and get an instance of the vista signon rpc
      VistaSignonRpc vistaSignonRpc = ExampleVistaSignonRpc.doVistaSignon(server, port, access, verify);
      // Get the vista signon result and check if signon was successful
      VistaSignonResult vistaSignonResult = vistaSignonRpc.getVistaSignonResult(); 
      if (vistaSignonResult.getSignonSucceeded()) {
        RpcBroker rpcBroker = vistaSignonRpc.getRpcBroker();
        OrdersRpc ordersRpc = new OrdersRpc(rpcBroker);
        
        // Order a lab test
        NewOrder newOrder = null;
        OrderInfo orderInfo = null;
        
        /*
        LabTest labTest = ordersRpc.getLabTestOrderData("1769"); 
        newOrder = new NewOrder();
        newOrder.setOrderId("202");
        newOrder.setDialogName("LR OTHER LAB TESTS");
        newOrder.setFillerId("LR");
        newOrder.setOrderable(new OrderField(labTest.getTestId(), labTest.getTestName()));
        newOrder.setPatientDfn("10108339");
        newOrder.setEncProvider("13243");
        newOrder.setEncLocation("396");
        newOrder.setPatientSex("M");
        newOrder.setPatientAge(67);
        newOrder.setScPercent(0);
        orderInfo = ordersRpc.putNewOrder(newOrder);
        System.out.println(orderInfo.getId());
        */
        
        /*
        // text order
        newOrder = new NewOrder();
        newOrder.setOrderId("49");
        newOrder.setDialogName("OR GXTEXT WORD PROCESSING ORDER");
        newOrder.setFillerId("OR");
        newOrder.setPatientDfn("10128436");
        newOrder.setEncProvider("13243");
        newOrder.setEncLocation("5542");
        newOrder.setPatientSex("M");
        newOrder.setPatientAge(63);
        //newOrder.setScPercent(0);
        newOrder.setComment(new OrderField(OrdersRpc.TX_WPTYPE, "This patient was seen for eyeglasses on 11/09/2012 10:45 and will be seen" +
        		"again on 05/03/2013 10:30.  A new consult is NOT necessary.  Please have the patient call the Eye Care Center directly at  909-825-7084 x5324 to" +
        		"schedule their glasses appointment."));
        newOrder.setStart(new OrderField("NOW","NOW"));
        newOrder.setStop(new OrderField("T+30","T+30"));
        orderInfo = ordersRpc.putNewOrder(newOrder);
        System.out.println(orderInfo.getId());
        */
        
        /*
        // medication order
        newOrder = new NewOrder();
        newOrder.setOrderId("147");
        newOrder.setDialogName("PSO OERR");
        newOrder.setFillerId("PSO");
        newOrder.setPatientDfn("10108339");
        newOrder.setEncProvider("13243");
        newOrder.setEncLocation("396");
        newOrder.setPatientSex("M");
        newOrder.setPatientAge(67);
        newOrder.setScPercent(0);
        newOrder.setOrderable(new OrderField("3239", "SIMVASTATIN TAB"));
        newOrder.setInstr(new OrderField("5MG", "5MG"));
        newOrder.setDrug(new OrderField("4346", ""));
        newOrder.setDose(new OrderField("5&MG&1&TABLET&5MG&4346&5&MG", ""));
        newOrder.setStrength(new OrderField("5MG", "5MG"));
        newOrder.setRoute(new OrderField("1", "PO"));
        newOrder.setSchedule(new OrderField("QHS", "QHS"));
        newOrder.setUrgency(new OrderField("9", ""));
        newOrder.setSupply(new OrderField("30", "30"));
        newOrder.setQty(new OrderField("1", "1"));
        newOrder.setRefills(new OrderField("2", "2"));
        newOrder.setSc(new OrderField("",""));
        newOrder.setPickup(new OrderField("M", ""));
        newOrder.setPi(new OrderField(OrdersRpc.TX_WPTYPE, "FOR HIGH CHOLESTEROL"));
        newOrder.setSig(new OrderField(OrdersRpc.TX_WPTYPE, "TAKE ONE TABLET BY MOUTH AT BEDTIME"));
        orderInfo = ordersRpc.putNewOrder(newOrder);
        System.out.println(orderInfo.getId());
        */
        
        /*
        // consult order
        newOrder = new NewOrder();
        newOrder.setOrderId("548");
        newOrder.setDialogName("GMRCOR CONSULT");
        newOrder.setFillerId("GMRC");
        newOrder.setPatientDfn("10108339");
        newOrder.setEncProvider("13243");
        newOrder.setEncLocation("396");
        newOrder.setPatientSex("M");
        newOrder.setPatientAge(67);
        newOrder.setScPercent(0);
        newOrder.setOrderable(new OrderField("100", "Cardiology"));
        newOrder.setUrgency(new OrderField("9", "ROUTINE"));
        newOrder.setSetting(new OrderField("0", "OUTPATIENT"));
        newOrder.setPlace(new OrderField("C", "Consultant's Choice"));
        newOrder.setEarliest(new OrderField("TODAY", "TODAY"));
        newOrder.setComment(new OrderField(OrdersRpc.TX_WPTYPE, "This is a test."));
        orderInfo = ordersRpc.putNewOrder(newOrder);
        System.out.println(orderInfo.getId());
        */
        
        String patientDfn = "10125641";
        String userDuz = "13243";
        String specialtyIen = "41";
        
        //Encounter encounter = getCurrentEncounter(rpcBroker, new Date(), patientDfn);
        Encounter encounter = null;
        double encDatetime = FMDateUtils.dateTimeToFMDateTime(encounter.getDatetime());
        ConstructOrder constructOrder = new ConstructOrder();
        constructOrder.setDialogName("OR GXMISC GENERAL");
        constructOrder.setDgroup("83");
        constructOrder.setOrderItem("3684");
        constructOrder.setSpecialty(specialtyIen);
        List<OrderResponse> orList = new ArrayList<OrderResponse>();
        OrderResponse or = new OrderResponse();
        or.setPromptId("");
        or.setPromptIen("4");
        or.setInstance(1);
        or.setiValue("6413");
        orList.add(or);
        or = new OrderResponse();
        or.setPromptId("");
        or.setPromptIen("6");
        or.setInstance(1);
        or.setiValue("NOW");
        orList.add(or);
        constructOrder.setResponseList(orList);
        //orderInfo = ordersRpc.putNewOrder(patientDfn, userDuz, encounter.getLocationIen(), specialtyIen,
        //    encDatetime, constructOrder, "", "");
        orderInfo = null;
        System.out.println("ORDER INFO");
        if (orderInfo != null) {
          System.out.println(orderInfo.getText());
        }
        
        
      }
      // Close the connection to the broker
      vistaSignonRpc.disconnect();
    } catch(BrokerException be) {
      System.err.println(be.getMessage());
      System.err.println("Action: " + be.getAction());
      System.err.println("Code: " + be.getCode());
      System.err.println("Mnemonic: " + be.getMnemonic());
    }      
  }
  
/*  private static Encounter getCurrentEncounter(RpcBroker rpcBroker, Date startDate, String patientDfn) throws Exception {
    
    PatientEncounterRpc patientEncounterRpc = new PatientEncounterRpc(rpcBroker);
    patientEncounterRpc.setReturnRpcResult(true);
    
    // Get the patient's current inpatient admission encounter
    GregorianCalendar startTime = new GregorianCalendar();
    startTime.setTime(startDate);
    startTime.add(Calendar.DAY_OF_YEAR, -2);
    GregorianCalendar endTime = new GregorianCalendar();
    endTime.setTime(new Date());
    endTime.add(Calendar.DAY_OF_YEAR, 1);
    EncounterAppointmentsList encounterAppointmentsList = 
        patientEncounterRpc.getInpatientEncounters(patientDfn, startTime, endTime);
    EncounterAppointment[] encounters = encounterAppointmentsList.getEncounterAppointments();
    // Get most recent inpatient admission
    EncounterAppointment inpatientVisit = null;
    long mostRecentDate = 0;
    for (EncounterAppointment encounter : encounters) {
      if (encounter.getType().equals("I")) {
        if (encounter.getDatetime().getTime() >= mostRecentDate) {
          inpatientVisit = encounter;
          mostRecentDate = encounter.getDatetime().getTime();
        }
      }
    }
    // Get encounter details
    if (inpatientVisit != null) {
      Encounter encounter = new Encounter();
      encounter.setDfn(patientDfn);
      encounter.setDatetime(inpatientVisit.getDatetime());
      encounter.setDatetimeStr(inpatientVisit.getDatetimeStr());
      encounter.setLocationIen(inpatientVisit.getLocationIen());
      return patientEncounterRpc.getEncounterDetails(encounter, false);
    } else
      return null;
  }  
*/
}