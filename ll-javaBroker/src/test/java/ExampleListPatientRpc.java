/*
 * ExampleListPatientRpc.java
 * 
 * Author: Rob Durkin (rob.durkin@med.va.gov)
 * Version 1.0 (10/04/2005)
 *  
 * Lists the patients by ward, clinic, team, etc
 *
 * Usage: java ExampleListPatientRpc AUTH_PROPS
 * where AUTH_PROPS is the name of a properties file containing VistA sign-on info.
 * 
 * Required Libraries:
 *   javaBroker.jar 
 *   
 */
import java.util.ResourceBundle;

import gov.va.med.lom.javaBroker.rpc.user.*;
import gov.va.med.lom.javaBroker.rpc.user.models.*;
import gov.va.med.lom.javaBroker.rpc.lists.LocationsRpc;
import gov.va.med.lom.javaBroker.rpc.lists.models.Location;
import gov.va.med.lom.javaBroker.rpc.lists.models.LocationsList;
import gov.va.med.lom.javaBroker.rpc.patient.*;
import gov.va.med.lom.javaBroker.rpc.patient.models.*;
import gov.va.med.lom.javaBroker.rpc.BrokerException;
import gov.va.med.lom.javaBroker.rpc.RpcBroker;

import gov.va.med.lom.javaBroker.util.Console;

public class ExampleListPatientRpc {
  
  /*
   * Lists all of the wards
   */
/*  public static void listWards(RpcBroker rpcBroker) throws BrokerException {
    // Create a locations rpc object
    LocationsRpc locationsRpc = new LocationsRpc(rpcBroker);
    LocationsList locationsList;
    Location[] locations;
    // Get and print the list of wards
    locationsList = locationsRpc.listAllWards();
    locations = locationsList.getLocations();
    for(int i = 0; i < locations.length; i++) {
      System.out.println(locations[i].getName() + " (IEN: " + locations[i].getIen() + ")");
    }
  }  */
  
  /*
   * Prints the dfn, name, ssn, and birthdate for the first patient 
   * matching the specified ssn, or for all patients with the matching 'last 5'. 
   */
  public static void main(String[] args) {
    // If user didn't pass the patient's ssn/last5 and name of the auth properties file, then print usage and exit
    String server = null;
    int port = 0;
    String access = null;
    String verify = null;
    if (args.length != 1) {
      System.out.println("Usage: java ExampleListPatientRpc AUTH_PROPS");
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
        // Print the list the wards, prompt user for ward ien
        //listWards(rpcBroker);
        String wardIen = Console.readLine("\nWard IEN: "); 
        // Create a patient selection rpc object
        PatientSelectionRpc patientSelectionRpc = new PatientSelectionRpc(rpcBroker); 
        patientSelectionRpc.setReturnRpcResult(true);
        PatientList patientList = null;
        PatientListItem[] patientListItems = null;
       //patientList = patientSelectionRpc.listPtByWard(wardIen);
        patientList = null;
        patientListItems = patientList.getPatientListItems();
        if (patientListItems.length > 0) {
          for(int i = 0; i < patientListItems.length; i++) {
            // Print patient list results
            System.out.println("DFN: " + patientListItems[i].getDfn());
            System.out.println("Name: " + patientListItems[i].getName());
            System.out.println("SSN: " + patientListItems[i].getSsn());
            System.out.println("Location: " + patientListItems[i].getLocation());
          }
          System.out.println("RPC Result: " + patientList.getRpcResult());
        } else {
          System.out.println("No matching patient for the specified ssn.");
        }
      } else {
        System.out.println(vistaSignonResult.getMessage());
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

}