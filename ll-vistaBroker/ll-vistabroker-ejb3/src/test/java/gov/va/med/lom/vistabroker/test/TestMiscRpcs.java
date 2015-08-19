package gov.va.med.lom.vistabroker.test;
/*
 * TestMiscRpcs.java
 * 
 * Author: Rob Durkin (rob.durkin@med.va.gov)
 * Version 1.0 (03/10/2007)
 *  
 */

import gov.va.med.lom.vistabroker.security.ISecurityContext;
import gov.va.med.lom.vistabroker.security.SecurityContextFactory;
import gov.va.med.lom.vistabroker.service.MiscVBService;
import gov.va.med.lom.vistabroker.util.VistaBrokerServiceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestMiscRpcs {
  
  private static final Log log = LogFactory.getLog(TestMiscRpcs.class);
  
  static void printUsage() {
    System.out.println("Usage: java TestMiscRpcs AUTH_PROPS");
    System.out.println("AUTH_PROPS is the name of a properties file containing VistA connection info.");
  }
  
  public static void main(String[] args) {
      MiscVBService miscRpcs = VistaBrokerServiceFactory.getMiscVBService();

      String division = "050";
      String duz = "32";

      try {
          // Set security context
          ISecurityContext securityContext = SecurityContextFactory.createDuzSecurityContext(division, duz);

          // Get and print date/time from VistA
          System.out.println("---------- DATE/TIME ---------");

          int today = miscRpcs.fmToday(securityContext).getPayload();
          double now = miscRpcs.fmNow(securityContext).getPayload();
          System.out.println("TODAY (FM date format): " + today);
          System.out.println("NOW (FM date/time format): " + now);
      } catch (Exception e) {
          System.err.println(e.getMessage());
      }
  }
}