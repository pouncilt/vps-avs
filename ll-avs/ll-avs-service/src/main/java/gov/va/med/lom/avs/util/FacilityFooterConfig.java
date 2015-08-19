package gov.va.med.lom.avs.util;

import java.io.*;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import gov.va.med.lom.javaUtils.misc.JdbcConnection;

public class FacilityFooterConfig {

  static final String FOOTER =  
      "This information is meant to provide a summary of your appointment with your health care provider. " + 
      "If you have any questions about your care including test results, medications, diagnoses or other " +
      "concerns, please contact your health care provider. Please bring this form to your next visit as " +
      "a record of your medications and alert your provider to any changes in your medications."+
      "<br/><br/>" +
      "To contact your primary care provider, please call the VA Palo Alto call center at 800-455-0057. " +
      "To refill a prescription, please call the pharmacy at 800-311-2511 or visit www.myhealth.va.gov. " +
      "To speak with a nurse after normal business hours, weekends or on holidays, please call the nurse " +
      "advice line at 800-455-0057." +
      "<br><br>" +
      "Access health resources.  Track your health.  Refill VA prescriptions. " +
      "Visit <strong>www.myhealth.va.gov</strong>!  Ask your health care team about in-person authentication " +
      "and begin ordering medications and begin ordering medications and viewing appointments through MyHealtheVet.  " +
      "After completing in-person authentication, click on \"Secure Messaging\" in MyHealtheVet and select " +
      "\"I would like to opt in to secure messaging\" in order to send email messages to your providers.";
  
  public static void main(String[] args) throws IOException {

    List<String> stations = new ArrayList<String>();
    stations.add("640");
    stations.add("6409AA");
    stations.add("6409AB");
    stations.add("6409AC");
    stations.add("640A0");
    stations.add("640A4");
    stations.add("640BU");
    stations.add("640BY");
    stations.add("640GA");
    stations.add("640GB");
    stations.add("640HA");
    stations.add("640HB");
    stations.add("640HC");
    stations.add("640PA");
    stations.add("640PB");
    stations.add("640SCI");
    
    try {
      String dbUrl = "jdbc:" + AvsProps.DB_DRIVER_TYPE + ":" + AvsProps.DB_DB_TYPE + "://" + 
          AvsProps.DB_SERVER + ":" + AvsProps.DB_PORT + ";DatabaseName=" + AvsProps.DB_NAME;
      JdbcConnection jdbcConnection = new JdbcConnection(dbUrl, AvsProps.DB_DRIVER);
      jdbcConnection.connect(AvsProps.DB_USER, AvsProps.DB_PASS);
      
      StringBuilder sql = new StringBuilder("UPDATE ckoFacilityPrefs SET footer=? WHERE facilityNo=?");
      
      Connection connection = jdbcConnection.getConnection();
      PreparedStatement pstmt = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
      
      pstmt.setString(1, FOOTER);
      
      for (String station : stations) {
        pstmt.setString(2, station);
        pstmt.executeUpdate();
      }
      
    } catch(Exception e) {
      System.err.println("JDBC Error, exiting: " + e.getMessage());
      System.exit(1);
    }
    
  }
  
}
