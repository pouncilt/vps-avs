/*
 * TestDdrLister.java
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ResourceBundle;
 
import gov.va.med.lom.javaBroker.rpc.user.*;
import gov.va.med.lom.javaBroker.rpc.user.models.*;
import gov.va.med.lom.javaBroker.rpc.ddr.*;
import gov.va.med.lom.javaBroker.rpc.BrokerException;
import gov.va.med.lom.javaBroker.rpc.MiscRPCs;
import gov.va.med.lom.javaBroker.rpc.Params;
import gov.va.med.lom.javaBroker.rpc.Mult;
import gov.va.med.lom.javaBroker.rpc.RpcBroker;
import gov.va.med.lom.javaBroker.util.DateUtils;
import gov.va.med.lom.javaBroker.util.StringUtils;

public class TestDdrLister {
  
  /*
   * Tests the DDR Lister class. 
   */
  public static void main(String[] args) {
    // If user didn't pass the name of the auth properties file, then print usage and exit
    String server = null;
    int port = 0;
    String access = null;
    String verify = null;
    if (args.length != 1) {
      System.out.println("Usage: java TestDdrLister AUTH_PROPS");
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
        try {
          
          DdrLister query = null;
          List<String> response = null;
          
          /*
          // Populates security keys lookup table (used with security keys list below) 
          Hashtable<String, String> lookupTable = new Hashtable<String, String>();
          query = DdrLister.buildIenNameQuery(rpcBroker, "19.1");
          response = query.execute();
          for (String field : response) {
            lookupTable.put(StringUtils.piece(field, 1), StringUtils.piece(field, 2));
          }
        
          // Retrieves and prints a list of the user's security keys
          System.out.println("User Security Keys");
          query = new DdrLister(rpcBroker);
          query.setFile("200.051");
          query.setIens("," + userDuz + ",");
          query.setFields(".01;1;2;3");
          query.setFlags("IP");
          response = query.execute();
          String CAPRI_CONTEXT = "DVBA CAPRI GUI";
          rpcBroker.createContext(CAPRI_CONTEXT);
          for (String line : response) {
            String[] fields = StringUtils.pieceList(line, '^');
            String arg = "$P($G(^DIC(19.1," + fields[0] + ",0)),U,2)";
            Params p = new Params();
            p.addParameter(arg, Params.REFERENCE);
            String securityKeyName = MiscRPCs.getVariableValue(rpcBroker, arg);
            System.out.println(securityKeyName + ": Key ID=" + fields[1] + ", Name=" + lookupTable.get(fields[1]) + 
                               ", Creator ID=" + fields[2] + 
                               ", Creation Date=" + DateUtils.toEnglishDateTime(DateUtils.fmDateTimeToDateTime(Double.valueOf(fields[3])).getTime()));
          }
          */
          
          /*
          // Get user SSN
          String arg = "@\"^VA(200," + rpcBroker.getUserDuz() + ",1)\"";
          String rtn = MiscRPCs.getVariableValue(rpcBroker, arg);
          System.out.println("\nUser SSN = " + StringUtils.piece(rtn, 9));
          
          */
          
          /*
          // Get user title
          String duz = "2880";
          String arg = "@\"^VA(200," + duz + ",0)\"";
          String rtn = MiscRPCs.getVariableValue(rpcBroker, arg);
          String titleIen = StringUtils.piece(rtn, 9);
          System.out.println("Title IEN=" + titleIen);
          arg = "@\"^DIC(3.1," + titleIen + ",0)\"";
          rtn = MiscRPCs.getVariableValue(rpcBroker, arg);
          System.out.println(rtn);
          */
          
          // Get location name
          /*
          String locationIen = "2638";
          String arg = "@\"^SC(" + locationIen + ",0)\"";
          String rtn = MiscRPCs.getVariableValue(rpcBroker, arg);
          System.out.println(StringUtils.piece(rtn, 1));
          */
          
          String locationIen = "2";
          String arg = "@\"^DIC(42," + locationIen + ",0)\"";
          String rtn = MiscRPCs.getVariableValue(rpcBroker, arg);
          System.out.println(StringUtils.piece(rtn, 1));          
          
          /*
          // Gets a list of the user's divisions
          System.out.println("\nUser Divisions");
          query = new DdrLister(rpcBroker);
          query.setFile("200.02P");
          query.setIens("," + userDuz + ",");
          query.setFields(".01E;1");
          query.setFlags("IP");
          query.setXref("#");
          query.setScreen("I $D(^DIC(4,$P(^(0),U,1),0))'=0");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          }
          */
          
          /*
          // Gets a list of the user classes for a particular user
          System.out.println("\nUser Classes for DUZ: " + userDuz);
          query = new DdrLister(rpcBroker);
          query.setFile("8930.3P");
          //query.setIens("," + userDuz + ",");
          query.setFields(".02;.02E");
          query.setFlags("IP");
          query.setXref("#");
          query.setScreen("I $P(^(0),U,1)=\"9276\"");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          }
          */

          /*
          // prints all user classes
          System.out.println("User Classes  (File #201)");
          query = new DdrLister(rpcBroker);
          query.setFile("8930.3");
          query.setFields(".01;.01E;.02;.02E");
          query.setFlags("IP");
          query.setXref("#");
          query.setMax("20");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          } 
          */
          /*
          System.out.println("PATIENT TEAM ASSIGNMENT  (File #404.42)");
          query = new DdrLister(rpcBroker);
          query.setFile("404.42");
          query.setFields(".01;.02;.01E;.03;.03E;.15");
          query.setFlags("IP");
          query.setXref("B");
          query.setFrom(adjustForNumericSearch("10271861"));
          query.setPart("10271861");
          //query.setScreen("I $P($G(^(0)),U,1)=\"10181514\"");
          query.setMax("20");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          } 
          */
          /*
          // prints all teams
          List<String> teamList = new ArrayList<String>();
          //44^MOD 4 - PACT TEAM A^PRIMARY CARE^ZZACOS/ZZAMBULATORYCARE^265
          System.out.println("Teams  (File #404.51)");
          query = new DdrLister(rpcBroker);
          query.setFile("404.51");
          query.setFields(".01;.03E;.06E;201");
          query.setFlags("IP");
          query.setXref("B");
          response = query.execute();
          for (String line : response) {
            String name = StringUtils.piece(line,  1);
            teamList.add(name);
          } 
          System.out.println("# Positions: " + teamList.size());
          */

          /*
          System.out.println("PATIENT TEAM POSITION ASSIGNMENT  (File #404.43)");
          query = new DdrLister(rpcBroker);
          query.setFile("404.43");
          query.setFields(".01E;.02E");
          query.setFlags("IP");
          query.setXref("B");
          query.setMax("20");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          } 
          */
          
          /*
          HashMap<String, String> positionsMap = new HashMap<String, String>();
          List<String> teamPositions = new ArrayList<String>();
          System.out.println("TEAM POSITION  (File #404.57)");
          query = new DdrLister(rpcBroker);
          query.setFile("404.57");
          query.setFields(".01;.03;.04;.12;303;302");
          query.setFlags("IP");
          query.setXref("C");
          //for (String team : teamList) {
            query.setScreen("I $P($G(^(0)),U,2)=44,+$$ACTTP^SCMCTPU(Y)=1");
            response = query.execute();
            for (String line : response) {
              String ien = StringUtils.piece(line, 1);
              String name = StringUtils.piece(line, 2).trim();
              System.out.println(line);
              //positionsMap.put(name, name);
              //teamPositions.add(ien);
            }
          //}
          */
            /*
          Set<String> teams = positionsMap.keySet();
          Iterator<String> it = teams.iterator();
          while (it.hasNext()) {
            System.out.println(it.next());
          }
          */
          
          /*
           622^PHYSICIAN^44^MOD 4 - PACT TEAM A^PHYSICIAN-ATTENDING
           623^CARE MANAGER^44^MOD 4 - PACT TEAM A^NURSE (RN)
           624^LVN 01^44^MOD 4 - PACT TEAM A^NURSE (LPN)
           625^LVN 02^44^MOD 4 - PACT TEAM A^NURSE (LPN)
           626^SCHEDULER^44^MOD 4 - PACT TEAM A^MAS CLERK
           627^CLINICAL PHARMACIST 01^44^MOD 4 - PACT TEAM A^CLINICAL PHARMACIST
           628^CLINICAL PHARMACIST 02^44^MOD 4 - PACT TEAM A^CLINICAL PHARMACIST
           629^SOCIAL WORKER^44^MOD 4 - PACT TEAM A^SOCIAL WORKER
           630^DIETITIAN^44^MOD 4 - PACT TEAM A^DIETITIAN
          */
            /*
          System.out.println("\nPOSITION ASSIGNMENT HISTORY  (File #404.52)");
          query = new DdrLister(rpcBroker);
          query.setFile("404.52");
          query.setFields(".01E;.02E;.03E;.04;.05E;.091;.11");
          query.setFlags("IP");
          query.setXref("B");
          //for (String teamPositionIen : teamPositions) {
            query.setScreen("I $P($G(^(0)),U,1)=625");
            response = query.execute();
            for (String line : response) {
              //1794^DIETITIAN^MAY 16, 2011^TRIVEDI,BANSARI^ACTIVE
              String status = StringUtils.piece(line, 5);
              if (status.equals("1")) {
                //String name = StringUtils.piece(line, 4);
                //String position = StringUtils.piece(line, 2);
                //System.out.println(name + " - " + position);
                System.out.println(line);
              }
            }
          //}
          */
          /*
          System.out.println("Medical Center Divisions  (File #40.8)");
          query = new DdrLister(rpcBroker);
          query.setFile("40.8");
          query.setFields(".01;.07;1");
          query.setFlags("IP");
          query.setXref("#");
          query.setScreen("I $P($G(^(0)),U,1)'=\"\"");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          }
          */
          
          /*
          query = new DdrLister(rpcBroker);
          query.setFile("200");
          query.setIens("," + rpcBroker.getUserDuz() + ",");
          query.setFields(".13;2");
          query.setFlags("IP");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          }
          
          // Gets latest orders (across all patients)
          System.out.println("\nSystem-Wide Orders (last 20)");
          query = new DdrLister(rpcBroker);
          query.setFile("100");
          query.setFields(".02;1;3;4;5;6;7;8.1;10;11;21;22;23;35");
          query.setFlags("IP");
          query.setXref("#");
          query.setMax("20");
          response = query.execute();
          for (String line : response) {
            String[] fields = StringUtils.pieceList(line, '^');
            System.out.println("ID=" + fields[0] + ", Patient ID=" + fields[1] + ", Provider ID=" + fields[2] + 
                               ", Creator ID=" + fields[3] + ", Date/Time=" + 
                               DateUtils.toEnglishDateTime(DateUtils.fmDateTimeToDateTime(Double.valueOf(fields[4])).getTime()) + 
                               ", Status= " + fields[5] + ", Patient Location ID=" + fields[6]);
          }
          
        */
          /*
          // prints patient list
          System.out.println("\nPatients (File #40.7)");
          query = new DdrLister(rpcBroker);
          query.setFile("2");
          query.setFields(".01;.02;.03;.09;.111;.112;.113;.114;.115E;.116;.117;.131;991.01;991.02");
          query.setFlags("IP");
          query.setXref("B");
          query.setMax("20");
          response = query.execute();
          for (String line : response) {
            //String[] fields = StringUtils.pieceList(line, '^');
            System.out.println(line);
          }
          */
          
          /*
          // prints clinic stop codes
          System.out.println("\nClinic Stop Codes (File #40.7)");
          query = new DdrLister(rpcBroker);
          query.setFile("40.7");
          query.setFields(".01;1");
          query.setFlags("IP");
          query.setXref("#");
          //query.setMax("20");
          response = query.execute();
          for (String line : response) {
            String[] fields = StringUtils.pieceList(line, '^');
            System.out.println("Name=" + fields[0] + ", Code=" + fields[1]);
          }
          */
          
          /*
          // prints all clinics
          int zzClinics = 0;
          int totClinics = 0;
          System.out.println("Clinics (File #44)");
          query = new DdrLister(rpcBroker);
          query.setFile("44");
          query.setFields(".01;1;3;3E;3.5;3.5E;7");
          query.setFlags("IP");
          query.setXref("#");
          query.setMax("50");
          query.setScreen("I + Y=5661");
          //query.setScreen("I $P($G(^(0)),U,1)']\"ZZ\"");
          response = query.execute();
          for (String line : response) {
            //String[] fields = StringUtils.pieceList(line, '^');
            //System.out.println("Name=" + fields[0] + ", Code=" + fields[1]);
            totClinics++;
            if (StringUtils.piece(line, 2).startsWith("ZZ")) {
              zzClinics++;
            }
            System.out.println(line);
          }
          System.out.println("Tot # Clinics = " + totClinics + ", # ZZ Clinics = " + zzClinics + ", # Non-ZZ Clinics = " + (totClinics - zzClinics));
          */
          
          /*
          query = new DdrLister(rpcBroker);
          query.setFile("40.8");
          query.setFields(".01;.07;1");
          query.setFlags("IP");
          query.setXref("AD");
          query.setScreen("I $P($G(^(0)),U,7)=\"605.8\"");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          }
          */
          
          /*
          // get hospital locations associated with a particular clinic stop code
          System.out.println("\nHospital Locations (File #44)");
          query = new DdrLister(rpcBroker);
          query.setFile("44");
          query.setMax("20");
          query.setFields(".01;1;8");
          //query.setFields(".01;1;23;3.5;7;8;9;9.5;10");
          //query.setFields(".01;1;2;2.1;3;3E;3.5;4;6;7;8;9;9.5;10;42;99;1916");
          query.setFlags("IP");
          query.setXref("#");
          //query.setScreen("I $P($G(^(0)),U,7)=407");
          query.setScreen("I $D(^SC(\"AST\",157,Y))");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          }
          */
          
          /*
          // prints all ward locations
          System.out.println("Wards (File #42)");
          query = new DdrLister(rpcBroker);
          query.setFile("42");
          // fields: name (.01), division (.015), bed section (.02), service (.03), primary location (.15)
          // example:
          // 102^3SE-NEURO^1^NEUROLOGY^NE^3SE-NEU
          // 103^3SE-MED^1^MEDICINE^M^3SE-MED
          // note that first piece is IEN
          query.setFields(".01;.015;.02;.03;.15");
          query.setFlags("IP");
          query.setXref("#");
          //query.setScreen("I $P($G(^(0)),U,1)=\"NEUROLOGY\"");
          //query.setScreen("I + Y=1");
          response = query.execute();
          for (String line : response) {
            //String[] fields = StringUtils.pieceList(line, '^');
            //System.out.println("Name=" + fields[0] + ", Code=" + fields[1]);
            System.out.println(line);
          }
          */
          
          /*
          query = new DdrLister(rpcBroker);
          query.setFile("44");
          query.setMax("20");
          query.setFields(".01;1;8");
          //query.setFields(".01;1;23;3.5;7;8;9;9.5;10");
          //query.setFields(".01;1;2;2.1;3;3E;3.5;4;6;7;8;9;9.5;10;42;99;1916");
          query.setFlags("IP");
          query.setXref("B");
          query.setScreen("I $P($G(^(0)),U,1)=997");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          }
          */

          /*
          System.out.println("QUICK ORDERS  (File #101.44)");
          query = new DdrLister(rpcBroker);
          query.setFile("101.44");
          query.setFields(".01E");
          query.setFlags("IP");
          query.setXref("B");
          //query.setScreen("I $P($G(^(0)),U,1)=3684");
          //query.setMax("20");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          } 
          */
          
          /*
          System.out.println("SITE DIVISIONS (File #4)");
          query = new DdrLister(rpcBroker);
          query.setFile("4");
          query.setFields("99;.01;13;100");
          query.setFlags("IP");
          query.setXref("C");
          query.setFrom("LOMA LINCZ~");
          query.setPart("LOMA LINDA");
          //query.setFrom("LOMA LINCZ~");
          //query.setPart("LOMA LINDA");
          //query.setScreen("I $P(^(99),U,4)'=1");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          }
          */

          /*
          DdrLister query2 = new DdrLister(rpcBroker);
          query2.setFile("52.41311");
          query2.setFields(".01");
          query2.setFlags("IP");
          query2.setXref("#");
          System.out.println("PENDING OUTPATIENT ORDERS (File #52.41)");
          query = new DdrLister(rpcBroker);
          query.setFile("52.41");
          //1;1E;2;2E;6E;
          query.setFields(".01;11E");
          query.setFlags("IP");
          query.setXref("P");
          query.setFrom("10188524");
          query.setPart("10188525");
          response = query.execute();
          for (String result : response) {
            System.out.println(result);
            System.out.println("DIAGNOSIS (File #52.41311)");
            String ien = StringUtils.piece(result, 1);
            query2.setIens("," + ien + ",");
            response = query2.execute();
            for (String line : response) {
              System.out.println(line);
            }
          }
          */
          
          /*
          System.out.println("PENDING OUTPATIENT ORDERS (File #52.41)");
          DdrGetsEntry getEntry = new DdrGetsEntry(rpcBroker);
          getEntry.setFile("52.41");
          // ien;placer number;patient;order type(int);order type(ext);drug
          getEntry.setFields(".01;1;2;6;11;24*");
          getEntry.setFlags("IE");
          getEntry.setIens("24,");
          response = getEntry.execute();
          for (String line : response) {
            System.out.println(line);
          } 
          */

          /*
          DdrGetsEntry getEntry = new DdrGetsEntry(rpcBroker);
          getEntry.setFile("200");
          // ien;placer number;patient;order type(int);order type(ext);drug
          getEntry.setFields(".01;.132;747.32");
          getEntry.setFlags("IEZ");
          getEntry.setIens("2880,");
          response = getEntry.execute();
          for (String line : response) {
            System.out.println(line);
          }
          */
          
          /*
          System.out.println("PHARMACY (File #52)");
          query = new DdrLister(rpcBroker);
          query.setFile("52");
          // ien;rx#;placer order #;patient;ndc;drug(int);drug(ext);status(int);status(ext)
          query.setFields(".01;6E;39.3;39.4;");
          query.setFlags("IP");
          query.setXref("B");
          query.setFrom(adjustForNumericSearch("42328179"));
          query.setPart("42328179");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          } 
          */         
          
          /*
          System.out.println("PHARMACY (File #52)");
          query = new DdrLister(rpcBroker);
          query.setFile("52");
          // ien;rx#;placer order #;patient;ndc;drug(int);drug(ext);status(int);status(ext)
          query.setFields(".01;39.3;1E;2E;27;6I;6E;100;100E;12;16E");
          query.setFlags("IP");
          query.setXref("APL");
          query.setFrom(adjustForNumericSearch("42328179"));
          query.setPart("42328179");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          }
          */
          
          /*
          System.out.println("PHARMACY FILE ICD-9 DIAGNOSES (File #52 FIELD #52311)");
          DdrGetsEntry getEntry = new DdrGetsEntry(rpcBroker);
          getEntry.setFile("52");
          getEntry.setFields(".01;6;10.2*");
          getEntry.setFlags("IE");
          getEntry.setIens("19577482,");
          response = getEntry.execute();
          for (String line : response) {
            System.out.println(line);
          } 
          */
          
          /*
          System.out.println("TIU DOCUMENT HEADERS (File #8925)");
          query = new DdrLister(rpcBroker);
          query.setFile("8925");
          // ien   ^document type^patient ^visit  ^status^entry date/time^author^clinic^visit location^reference date
          //3582762^1541         ^10188525^6254164^7     ^3030804.120525 ^12903 ^      ^337           ^3030804.1205
          //query.setFields(".01;.02;.03;.05;.07;1201;1202;1203;1211;1301");
          query.setFields(".05E;.07E;1201;1202E;1203E;1211E;1301E");
          query.setFlags("IP");
          query.setXref("C");
          query.setFrom("10174024");
          query.setPart("10174025");
          query.setScreen("I $P($G(^(0)),U,7)>3130801.00");
          //query.setMax("25");
          response = query.execute();
          for (String result : response) {
            System.out.println(result);
          }
          */

          /*
          // prints clinic stop codes
          System.out.println("\nICD-9 Codes (File #80)");
          query = new DdrLister(rpcBroker);
          query.setFile("80");
          query.setFields(".01;3");
          query.setFlags("IP");
          query.setXref("#");
          query.setMax("20");
          response = query.execute();
          StringBuffer sb = new StringBuffer();
          for (String line : response) {
            String[] fields = StringUtils.pieceList(line, '^');
            //sb.append(fields[1] + "\t" + fields[2] + "\n");
            System.out.println(fields[1] + "\t" + fields[2] + "\n");
          }
          BufferedWriter out = new BufferedWriter(new FileWriter("C:\\wc\\docs\\VistA\\icd-9.txt"));
          out.write(sb.toString());
          out.flush();
          out.close();
          */

          /*
          System.out.println("Order Status (File #100.01)");
          query = new DdrLister(rpcBroker);
          query.setFile("100.01");
          query.setFields(".001;.01;.02;.1");
          query.setFlags("IP");
          query.setXref("#");
          query.setMax("20");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          }
          */
          
          /*
          // prints all Devices
          System.out.println("Devices (File #3.5)");
          query = new DdrLister(rpcBroker);
          query.setFile("3.5");
          query.setFields(".01;.02;65");
          query.setFlags("IP");
          query.setXref("#");
          //query.setMax("10");
          //query.setScreen("I + Y=1523");
          response = query.execute();
          for (String line : response) {
            String ipAddress = StringUtils.piece(line, 4);
            if (!ipAddress.isEmpty()) {
              System.out.println(line);
            }
          }
          */
          
          /*
          System.out.println("Vital Sign Types (File #120.51)");
          query = new DdrLister(rpcBroker);
          query.setFile("120.51");
          query.setFields(".01;1");
          query.setFlags("IP");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          }
          */
          
          /*
          System.out.println("Laboratory Tests (File #60)");
          query = new DdrLister(rpcBroker);
          query.setFile("60");
          query.setFields(".01");
          query.setFlags("IP");
          query.setMax("10");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          }
          */
          
          /*
          System.out.println("IMAGING WINDOWS SESSIONS (File #2006.82)");
          query = new DdrLister(rpcBroker);
          query.setFile("2006.82");
          query.setFields(".01;.04E;1E;2E;3E;5;6;8");
          query.setFlags("IP");
          query.setXref("E");
          query.setScreen("I $P($G(^(0)),U,9)'=\"\"");
          query.setMax("50");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          }
          */
          
          /*
          System.out.println("DISPLAY GROUPS (File #100.98)");
          query = new DdrLister(rpcBroker);
          query.setFile("100.98");
          query.setFields(".01");
          query.setFlags("IP");
          query.setXref("B");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          }
          */

          /*
          Mult mult = new Mult();
          mult.setMultiple("FILE", "100.98");
          mult.setMultiple("FIELDS",".01");
          mult.setMultiple("FLAGS","IP");
          mult.setMultiple("XREF","B");
          Object[] params = {mult};
          rpcBroker.createContext("");
          String x = rpcBroker.call("SC LISTER", params);
          System.out.println(x);
          */
          
          /*
          query = new DdrLister(rpcBroker);
          query.setFile("2006.03");
          query.setMax("20");
          query.setFields(".001;.01;1E;3E;4;5E;6;7;9");
          //Number;Queue Name;User;Request Date/Time;Completion Status;Completion Date/Time;Image Pointer;Queue Type;Filepath
          query.setFlags("IP");
          query.setXref("B");
          query.setScreen("I $P($G(^(0)),U,1)=\"76052210\"");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          } 
          */         
        
          /*
          String trkId = "\"ANN;605_12754_3041210_071936\"";
          String arg = "$O(^MAG(2006.82,\"E\"," + trkId + ",\"\"),-1)";
          String snum = MiscRPCs.getVariableValue(rpcBroker, arg);
          System.out.println("SNUM=" + snum);

          String i = "1";
          while (true) {
            arg = "$O(^MAG(2006.82," + snum + ",\"ACT\"," + i + "))";
            i = MiscRPCs.getVariableValue(rpcBroker, arg);
            System.out.println("I=" + i);
            if (!i.equals("")) {
              arg = "$G(^MAG(2006.82," + snum + ",\"ACT\"," + i + ",0))";
              String val = MiscRPCs.getVariableValue(rpcBroker, arg);
              System.out.println("TYPE:" + val);
              
              arg = "$G(^MAG(2006.82," + snum + ",\"ACT\"," + i + ",1))";
              String x = MiscRPCs.getVariableValue(rpcBroker, arg);
              System.out.println("X=" + x);
              String y = StringUtils.piece(x, ':' , 1);
              if (y.equals("(PXIEN)")) {
                String pxien = StringUtils.piece(x, ':' , 2);
                System.out.println("PXIEN=" + pxien.trim());
                break;
              }
            } else {
              break;
            }
          }
          */
          
          /*
          System.out.println("RECALL REMINDERS FILE  (File #403.5)");
          query = new DdrLister(rpcBroker);
          query.setFile("403.5");
          query.setFields(".01;3E;4E;4.5E;5E");
          query.setFlags("IP");
          query.setXref("B");
          query.setFrom(adjustForNumericSearch("10053"));
          query.setPart("10053");
          //query.setMax("10");
          response = query.execute();
          for (String line : response) {
            String dfn = StringUtils.piece(line, 2);
            if (dfn.equals("10053")) {
              System.out.println(line);
            }
          }               
          */
          
          /*
          System.out.println("PATIENT/IHS (File #9000001)");
          query = new DdrLister(rpcBroker);
          query.setFile("9000001");
          // ien;rx#;placer order #;patient;ndc;drug(int);drug(ext);status(int);status(ext)
          query.setFields(".01;.01E");
          query.setFlags("IP");
          query.setXref("B");
          query.setMax("10");
          //query.setFrom(adjustForNumericSearch("23558510"));
          //query.setPart("23558510");
          query.setScreen("I $P($G(^(0)),U,1)=\"23558510\"");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          } 
          */           
          
          /*
          System.out.println("V HEALTH FACTORS (File #9000010.23)");
          DdrLister finder = new DdrLister(rpcBroker);
          finder.setFile("9000010.23");
          finder.setFields(".01;.01E;.02;.02E;.03;.03E;.04;80102;81101");
          finder.setFlags("IPB");
          finder.setXref("C");
          finder.setMax("25");
          finder.setFrom(adjustForNumericSearch("151464"));
          finder.setPart("151464");          
          finder.setScreen("I $P(^(0),U,2)'=\"\"");
          response = finder.execute();
          for (String line : response) {
            System.out.println(line);
          } 
          */
          
          /*
          // Gets a list of the allergies for a patient
          System.out.println("\nAllergies");
          query = new DdrLister(rpcBroker);
          query.setFile("120.8");
          query.setFields(".02;3.1;20");
          query.setFlags("IP");
          query.setXref("B");
          query.setFrom(adjustForNumericSearch("10043495"));
          query.setPart("10043495");    
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          }          
          */
          
          /*
          System.out.println("Appointment Types (File #409.1)");
          query = new DdrLister(rpcBroker);
          query.setFile("409.1");
          query.setFields(".001;.01");
          query.setFlags("IP");
          query.setXref("B");
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          }
          */
          
          /* *********************************************************
          Date start = new Date();
          query = new DdrLister(rpcBroker);
          query.setFile("130");
          query.setFields(".09;10"); //.01;.01E;.09;.09E;10     .14;.04E;26;612;21
          query.setFlags("IP");
          query.setXref("AC");
          query.setFrom("3140904.5959");
          query.setPart("315"); 
          response = query.execute();
          for (String line : response) {
            System.out.println(line);
          }
          System.out.println("# results=" + response.size());
          System.out.println("Elapsed Time (msec)=" + (new Date().getTime() - start.getTime()));
         */
          
          /*
          String x = "10315458";
          String arg = "$G(^DPT(" + x + ",0))";
          x = MiscRPCs.getVariableValue(rpcBroker, arg);
          System.out.println(x);
          */
          
          /*
          System.out.println("SURGERY WAITING LIST (File #133.8)");
          query = new DdrLister(rpcBroker);
          query.setFile("133.8");
          query.setFields(".01;.01E");
          query.setFlags("IP");
          query.setXref("B");
          DdrLister query2 = new DdrLister(rpcBroker);
          query2.setFile("133.801");
          query2.setFields(".01E;2E");
          query2.setFlags("IP");
          query2.setXref("#");
          response = query.execute();
          for (String result : response) {
            System.out.println(result);
            String ien = StringUtils.piece(result, 1);
            query2.setIens("," + ien + ",");
            response = query2.execute();
            for (String line : response) {
              System.out.println("    " + line);
            }
          } 
          */         
          
          /*
          query = new DdrLister(rpcBroker);
          query.setFile("133.8");
          query.setFields(".01;.01E");
          //Case #^DFN^Patient Name^Surgical Specialty^Date Requested^Operative Procedure^Order #
          query.setFlags("IP");
          query.setXref("B");
          //query.setIens(",10089454,");
          response = query.execute();
          
          query.setFile("133.801");
          query.setFields(".01;.01E;1;2E;6E");
          query.setFlags("IP");
          query.setXref("#");
          for (String line : response) {
            System.out.println(line);
            String ien = StringUtils.piece(line, 1);
            query.setIens( "," + ien + ",");
            List<String> list = query.execute();
            for (String x : list) {
              System.out.println(" --> " + x);
            } 
            
          }
          */
          /*
SURGERY (File #130)
3^1^GENERAL(OR WHEN NOT DEFINED BELOW)
12^2^GYNECOLOGY
5^3^NEUROSURGERY
6^4^OPHTHALMOLOGY
4^5^ORTHOPEDICS
8^6^OTORHINOLARYNGOLOGY (OPT) (ENT)
9^7^PLASTIC SURGERY (INCLUDES HEAD AND NECK)
10^8^PROCTOLOGY
1^10^UROLOGY
2^11^ORAL SURGERY (DENTAL)
7^12^PODIATRY
11^13^PERIPHERAL VASCULAR           
           */
          /*
          DdrGetsEntry getEntry = new DdrGetsEntry(rpcBroker);
          getEntry.setFile("133.8");
          // ien;placer number;patient;order type(int);order type(ext);drug
          getEntry.setFields(".01;.01E");
          getEntry.setFlags("IE");
          getEntry.setIens("8,");
          response = getEntry.execute();
          for (String line : response) {
            System.out.println(line);
          } 
          */
          
          /*
          String srss = "0";
          String srs = null;
          String srsdate = "0";
          String srwl = "0";
          for (;;) {
            String arg = "$O(^SRO(133.8,\"AWL\"," + srss + "))";
            srss = MiscRPCs.getVariableValue(rpcBroker, arg);
            System.out.println(srss);
            if (srss.isEmpty()) {
              srss = "0";
              break;
            }            
            arg = "$G(^SRO(133.8," + srss + ",0))";
            srs = MiscRPCs.getVariableValue(rpcBroker, arg);
            System.out.println("    " + srs);
            for (;;) {
              arg = "$O(^SRO(133.8,\"AWL\"," + srss + "," + srsdate + "))";
              srsdate = MiscRPCs.getVariableValue(rpcBroker, arg);
              System.out.println("        " + srsdate);
              if (srsdate.isEmpty()) {
                srsdate = "0";
                break;
              }         
              for (;;) {
                arg = "$O(^SRO(133.8,\"AWL\"," + srss + "," + srsdate + "," + srwl + "))"; 
                srwl = MiscRPCs.getVariableValue(rpcBroker, arg);
                System.out.println("        " + srwl);
                if (srwl.isEmpty()) {
                  srwl = "0";
                  break;
                }      
                arg = "$G(^SRO(133.8," + srss + ",1," + srwl + ",0))"; 
                String dfn = MiscRPCs.getVariableValue(rpcBroker, arg);
                System.out.println("        " + dfn);
              }
            }
          }
          */
          
        } catch(Exception e) {
          e.printStackTrace();
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

  public static String adjustForNumericSearch(String target) {
    int iTarget = Integer.valueOf(target);
    return String.valueOf(iTarget - 1);
  }
  
}