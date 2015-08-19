package gov.va.med.lom.avs.util;

import java.util.ResourceBundle;

import gov.va.med.lom.javaUtils.misc.StringUtils;

public class AvsProps {

  // Pre-Visit Summary settings
  public static boolean PVS_ENABLED;
  
  // Database properties (for use with setup utils)
  public static String DB_SERVER;
  public static String DB_PORT;
  public static String DB_NAME;
  public static String DB_DRIVER_TYPE;
  public static String DB_DRIVER;
  public static String DB_DB_TYPE;
  public static String DB_USER;
  public static String DB_PASS;
  public static String DB_URL;
  
  // AVS demo settings
  public static boolean IS_DEMO;
  public static String DEMO_STATION_NO;
  public static String DEMO_PT_DFN;
  public static String DEMO_PT_NAME;
  public static double DEMO_PT_DOB_DM;
  public static String DEMO_PT_DOB;
  public static int DEMO_PT_AGE;
  public static String DEMO_PT_SSN;  
  
  // VistA RPC Broker account settings
  public static String VISTA_SERVER;
  public static int VISTA_PORT;
  public static String VISTA_AV;
  
  // Medication Image Library settings
  public static boolean MIL_ENABLED;
  public static String MIL_DB_TYPE;
  public static String MIL_DB_SERVER;
  public static String MIL_DB_PORT;
  public static String MIL_DB_NAME;
  public static String MIL_DOMAIN;
  public static String MIL_USERNAME;
  public static String MIL_PASSWORD;
  public static String MIL_DRIVER_TYPE;
  public static String MIL_DRIVER;
  public static String MIL_IMAGES_DIR;
  
  // VistA Imaging settings
  public static String VI_LOCAL_PATH;
  public static String VI_REMOTE_PATH;
  public static String VI_USERNAME;
  public static String VI_PASSWORD;
  public static String VI_STSCB;
  public static String VI_IXTYPE;
  public static String VI_GDESC;
  public static String VI_TTYPE;
  public static String VI_PXPKG;
  public static String VI_DFLG;
  
  // Krames On Demand settings
  public static String KRAMES_LOCAL_WEB;
  public static String KRAMES_LOCAL_CSS;
  public static String KRAMES_LOCAL_IMG;
  public static String KRAMES_BASE_URL;
  public static String KRAMES_SEARCH_PATH;
  public static String KRAMES_CONTENT_PATH;
  
  static {
    PVS_ENABLED = StringUtils.strToBool(System.getProperty("avs.pvs.enabled"), "true");
    
    DB_SERVER = System.getProperty("avs.db.server");
    DB_PORT = System.getProperty("avs.db.port");
    DB_NAME = System.getProperty("avs.db.name");
    DB_DRIVER_TYPE = System.getProperty("avs.db.drivertype");
    DB_DB_TYPE = System.getProperty("avs.db.database");
    DB_USER = System.getProperty("avs.db.username");
    DB_PASS = System.getProperty("avs.db.password");
    DB_DRIVER = System.getProperty("avs.db.driver");
    
    VISTA_SERVER = System.getProperty("avs.vista.vistaServer");
    VISTA_PORT = Integer.valueOf(System.getProperty("avs.vista.vistaPort")).intValue();
    VISTA_AV = System.getProperty("avs.vista.vistaAV");
  
    //IS_DEMO = StringUtils.strToBool(System.getProperty("avs.demo"), "true");
    IS_DEMO = StringUtils.strToBool(System.getProperty("avs.demo"), "true");
    DEMO_STATION_NO = System.getProperty("avs.demo.stationNo");
    DEMO_PT_DFN = System.getProperty("avs.demo.ptDfn");
    DEMO_PT_NAME = System.getProperty("avs.demo.ptName");
    DEMO_PT_DOB_DM = Double.valueOf(System.getProperty("avs.demo.ptDobDm")).doubleValue();
    DEMO_PT_DOB = System.getProperty("avs.demo.ptDob");
    DEMO_PT_AGE = Integer.valueOf(System.getProperty("avs.demo.ptAge")).intValue();
    DEMO_PT_SSN = System.getProperty("avs.demo.ptSsn");      
  
    MIL_ENABLED = StringUtils.strToBool(System.getProperty("avs.mil.enabled"), "true");
    MIL_DB_TYPE =  System.getProperty("avs.mil.dbtype");
    MIL_DB_SERVER = System.getProperty("avs.mil.dbserver");
    MIL_DB_PORT = System.getProperty("avs.mil.dbport");
    MIL_DB_NAME = System.getProperty("avs.mil.dbname");
    MIL_DOMAIN = System.getProperty("avs.mil.domain");
    MIL_USERNAME = System.getProperty("avs.mil.username");
    MIL_PASSWORD = System.getProperty("avs.mil.password");
    MIL_DRIVER_TYPE = System.getProperty("avs.mil.drivertype");
    MIL_DRIVER = System.getProperty("avs.mil.driver");
    MIL_IMAGES_DIR = System.getProperty("avs.mil.images");
    
    VI_LOCAL_PATH = System.getProperty("avs.vi.localpath");
    VI_REMOTE_PATH = System.getProperty("avs.vi.remotepath");
    VI_USERNAME = System.getProperty("avs.vi.username");
    VI_PASSWORD = System.getProperty("avs.vi.password");
    VI_STSCB = System.getProperty("avs.vi.stscb");
    VI_IXTYPE = System.getProperty("avs.vi.ixtype");
    VI_GDESC = System.getProperty("avs.vi.gdesc");
    VI_TTYPE = System.getProperty("avs.vi.ttype");
    VI_PXPKG = System.getProperty("avs.vi.pxpkg");
    VI_DFLG = System.getProperty("avs.vi.dflg");
    
    KRAMES_LOCAL_WEB = System.getProperty("avs.krames.localweb");
    KRAMES_LOCAL_CSS = System.getProperty("avs.krames.localcss");
    KRAMES_LOCAL_IMG = System.getProperty("avs.krames.localimg");
    KRAMES_BASE_URL = System.getProperty("avs.krames.baseurl");
    KRAMES_SEARCH_PATH = System.getProperty("avs.krames.searchpath");
    KRAMES_CONTENT_PATH = System.getProperty("avs.krames.contentpath");
    
  }
  
}
