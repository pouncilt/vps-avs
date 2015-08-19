package gov.va.med.lom.avs.util;

import java.io.*;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

import gov.va.med.lom.javaUtils.misc.JdbcConnection;

public class FacilityPrefConfig {

  static final String HEADER = "<div style=\"float:right;margin:0 0 5px 20px;\">" +
  		"<img src=\"../artwork/Dept_of_VA_Affairs-greyscale.png\" width=\"205\" height=\"42\" " +
  		"alt=\"Department of Veterans Affairs\" /></div>" +
  		"<div style=\"font-size:1.8em;font-weight:bold;\">After Visit Summary</div>" +
  		"<div style=\"font-size:0.9em;font-weight:bold;\">%PATIENT_NAME%</div>" +
  		"<div style=\"font-size:0.9em;\">Visit date: %ENCOUNTER_DATE%</div>" +
  		"<div style=\"font-size:0.9em;\">Date generated: %CURRENT_DATETIME%</div>" +
  		"<div style=\"font-size:0.9em;\">%FACILITY_NAME%</div>";
  
  static final String FOOTER = "This information is meant to provide a summary of your appointment with your health care provider.  " +
  		"If you have any questions about your care including test results, medications, diagnoses or other concerns, " +
  		"please contact your health care provider.  Please bring this form to your next visit as a record of your \r\n" + 
  		"medications and alert your provider to any changes in your medications.\r\n\r\n" + 
  		"<br/><br/>\r\n" + 
  		"To contact your primary care provider, please call the Madison VA call center at ???-???-????. " +
  		"To refill a prescription, please call the pharmacy at ???-???-???? or visit www.myhealth.va.gov.  " +
  		"To speak with a nurse after normal business hours, weekends or on holidays, please call the nurse advice line at ???-???-????.\r\n\r\n" + 
  		"<br><br>\r\n" + 
  		"Access health resources.  Track your health.  Refill VA prescriptions.\r\n" + 
  		"Visit <strong>www.myhealth.va.gov</strong>!  Ask your health care team about in-person authentication and begin ordering " +
  		"medications and viewing appointments through MyHealtheVet.  After completing in-person authentication, click on " +
  		"\"Secure Messaging\" in MyHealtheVet and select \"I would like to opt in to secure messaging\" in order to " +
  		"send email messages to your providers.";
  
  static final String PVS_FOOTER = "Access health resources.  Track your health.  Refill VA prescriptions.\r\n" + 
      "Visit <strong>www.myhealth.va.gov</strong>!  Ask your health care team about in-person authentication and begin ordering " +
      "medications and viewing appointments through MyHealtheVet.  After completing in-person authentication, click on " +
      "\"Secure Messaging\" in MyHealtheVet and select \"I would like to opt in to secure messaging\" in order to " +
      "send email messages to your providers.";
  
  static final String TIU_NOTE_TEXT = "The patient was provided with a copy of an after-visit summary at the\r\n" + 
  		"conclusion of the visit.\r\n\r\n" + 
  		"The after-visit summary includes information pertaining to the patient's\r\n" + 
  		"encounter, including diagnoses, vital signs, medications, and new orders,\r\n" + 
  		"as well as a list of any any upcoming appointments and information regarding\r\n" + 
  		"the patient's ongoing care.\r\n\r\n" + 
  		"The patient's medications were reviewed with the patient by the provider\r\n" + 
  		"and were provided to the patient as an updated list of medications.  The\r\n" + 
  		"patient was instructed to inform the provider of any medication changes\r\n" + 
  		"or discrepancies that were noted.  Otherwise, the patient was instructed\r\n" + 
  		"to continue the medications as prescribed.\r\n\r\n" + 
  		"A copy of the after-visit summary provided to the patient is available in\r\n" + 
  		"VistA Imaging.";
  
  static final String FACILITY_NO = "607";
  static final String BOILERPLATE = "";
  static final String TIU_TITLE_IEN = "3203";
  static final String TIMEZONE = "US/Central";
  static final String SERVICE_DUZ = "214398";
  static final int KRAMES_ENABLED = 1;
  static final int SERVICES_ENABLED = 1;
  static final int MED_DESCRIPTIONS_ENABLED = 1;
  static final int REFRESH_FREQUENCY = 180000;
  static final int UPCOMING_APPTS_RANGE = 3;
  static final int ENCOUNTERS_RANGE = 60;
  static final int ORDER_TIME_FROM = 60;
  static final int ORDER_TIME_THRU = 180;
  static final String LANGUAGES = "English";
  
  public static void main(String[] args) throws IOException {

    List<String> stations = new ArrayList<String>();
    stations.add("607");
    stations.add("607GC");
    stations.add("607GD");
    stations.add("607GE");
    stations.add("607GF");
    stations.add("607GG");
    stations.add("585GD");
    stations.add("607HA");
    try {
      JdbcConnection jdbcConnection = new JdbcConnection(AvsProps.DB_URL, AvsProps.DB_DRIVER);
      jdbcConnection.connect(AvsProps.DB_USER, AvsProps.DB_PASS);
      
      StringBuilder sql = null;
      PreparedStatement pstmt = null;
      Connection connection = jdbcConnection.getConnection();
      
      /*
      sql = new StringBuilder("SELECT timeZone, boilerplate, header, footer, pvsFooter, tiuTitleIen, ")
      .append("tiuNoteText, serviceDuz, kramesEnabled, servicesEnabled, medDescriptionsEnabled, refreshFrequency, ")
      .append("upcomingAppointmentRange, encountersRange, orderTimeFrom, orderTimeThru, languages ")
      .append("FROM ckoFacilityPrefs WHERE facilityNo = ?");
      pstmt = connection.prepareStatement(sql.toString());
      pstmt.setString(1, FACILITY_NO);
      ResultSet rs = pstmt.executeQuery();
      */
      
      sql = new StringBuilder("INSERT INTO ckoFacilityPrefs (facilityNo, timeZone, boilerplate, header, footer, pvsFooter, ");
      sql.append("tiuTitleIen, tiuNoteText, serviceDuz, kramesEnabled, servicesEnabled, medDescriptionsEnabled, refreshFrequency, ");
      sql.append("upcomingAppointmentRange, encountersRange, orderTimeFrom, orderTimeThru, languages) ");
      sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
      pstmt = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
      
      /*
      if (rs.next()) {
        pstmt.setString(2, rs.getString(1));
        pstmt.setString(3, rs.getString(2));
        pstmt.setString(4, rs.getString(3));
        pstmt.setString(5, rs.getString(4));
        pstmt.setString(6, rs.getString(5));
        pstmt.setString(7, rs.getString(6));
        pstmt.setString(8, rs.getString(7));
        pstmt.setString(9, rs.getString(8));
        pstmt.setInt(10, rs.getInt(9));
        pstmt.setInt(11, rs.getInt(10));
        pstmt.setInt(12, rs.getInt(11));
        pstmt.setInt(13, rs.getInt(12));
        pstmt.setInt(14, rs.getInt(13));
        pstmt.setInt(15, rs.getInt(14));
        pstmt.setInt(16, rs.getInt(15));
        pstmt.setInt(17, rs.getInt(16));
        pstmt.setString(18, rs.getString(17));
      }
      */
      
      pstmt.setString(2, TIMEZONE);
      pstmt.setString(3, BOILERPLATE);
      pstmt.setString(4, HEADER);
      pstmt.setString(5, FOOTER);
      pstmt.setString(6, PVS_FOOTER);
      pstmt.setString(7, TIU_TITLE_IEN);
      pstmt.setString(8, TIU_NOTE_TEXT);
      pstmt.setString(9, SERVICE_DUZ);
      pstmt.setInt(10, KRAMES_ENABLED);
      pstmt.setInt(11, SERVICES_ENABLED);
      pstmt.setInt(12, MED_DESCRIPTIONS_ENABLED);
      pstmt.setInt(13, REFRESH_FREQUENCY);
      pstmt.setInt(14, UPCOMING_APPTS_RANGE);
      pstmt.setInt(15, ENCOUNTERS_RANGE);
      pstmt.setInt(16, ORDER_TIME_FROM);
      pstmt.setInt(17, ORDER_TIME_THRU);
      pstmt.setString(18, LANGUAGES);
      
      for (String station : stations) {
        pstmt.setString(1, station);
        pstmt.executeUpdate();
      }
      
    } catch(Exception e) {
      System.err.println("JDBC Error, exiting: " + e.getMessage());
      System.exit(1);
    }
    
  }
  
}
