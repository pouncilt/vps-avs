/*
 * ExampleVistaSignonRpc.java
 * 
 * Author: Rob Durkin (rob.durkin@med.va.gov)
 * Version 1.3 (07/05/2005)
 *  
 * Connects the Java client broker to the server and signs the user on.
 *
 * Usage: java ExampleVistaSignonRpc AUTH_PROPS
 * where AUTH_PROPS is the name of a properties file containing VistA sign-on info.
 * 
 * Required Libraries:
 *   javaBroker.jar 
 *   
 */
import java.util.ResourceBundle;
 
import gov.va.med.lom.javaBroker.rpc.user.*;
import gov.va.med.lom.javaBroker.rpc.user.models.*;
import gov.va.med.lom.javaBroker.rpc.RpcBroker;
import gov.va.med.lom.javaBroker.rpc.BrokerException;

public class ExampleVistaSignonRpc {
  
  /*
   * Connects to the broker server and signs the user on to VistA.
   */
  public static VistaSignonRpc doVistaSignon(String server, int port, 
                                             String accessCode, String verifyCode) throws BrokerException {
    // Create an RPC broker and connect to the server
    
    RpcBroker rpcBroker = new RpcBroker();
    rpcBroker.connect(server, port);
    // Do the signon setup 
    VistaSignonSetupRpc vistaSignonSetupRpc = new VistaSignonSetupRpc(rpcBroker);
    vistaSignonSetupRpc.doVistaSignonSetup();
    // Do signon 
    VistaSignonRpc vistaSignonRpc = new VistaSignonRpc(rpcBroker);
    vistaSignonRpc.setReturnRpcResult(true);
    vistaSignonRpc.doVistaSignon(accessCode, verifyCode);
    return vistaSignonRpc;     
  }
  
  public static void main(String[] args) {
    // If user didn't pass the name of the auth properties file, then print usage and exit
    String server = null;
    int port = 0;
    String access = null;
    String verify = null;
    if (args.length != 1) {
      System.out.println("Usage: java ExampleVistaSignonRpc AUTH_PROPS");
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
      // Get the vista signon result object
      VistaSignonResult vistaSignonResult = vistaSignonRpc.getVistaSignonResult();  
      if (vistaSignonResult.getSignonSucceeded()) {
        // Print the properties of the vista signon result object  
        System.out.println("---------- VISTA SIGN-ON INFO ----------");  
        System.out.println("DUZ: " + vistaSignonResult.getDuz());
        System.out.println("Device Locked: " + vistaSignonResult.getDeviceLocked());
        System.out.println("Change Verify Code: " + vistaSignonResult.getChangeVerifyCode());
        System.out.println("Signon Succeeded: " + vistaSignonResult.getSignonSucceeded());
        System.out.println("Greeting: " + vistaSignonResult.getGreeting());
        System.out.println("Message: " + vistaSignonResult.getMessage());
        System.out.println("RPC Result: " + vistaSignonResult.getRpcResult());
      } else {
        // Signon failed, print reason
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