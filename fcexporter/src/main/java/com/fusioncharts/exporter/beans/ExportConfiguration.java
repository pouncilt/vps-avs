package com.fusioncharts.exporter.beans;

import com.fusioncharts.exporter.FusionChartsExportHelper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class ExportConfiguration {
    private static Logger logger = null;
    public static String EXPORTHANDLER = "FCExporter_";
    public static String RESOURCEPACKAGE = "com.fusioncharts.exporter.resources";
    public static String SAVEPATH = "./";
    public static String SAVEABSOLUTEPATH = "./";
    public static String HTTP_URI = "http://yourdomain.com/";
    public static String TMPSAVEPATH = "";
    public static boolean OVERWRITEFILE = false;
    public static boolean INTELLIGENTFILENAMING = true;
    public static String FILESUFFIXFORMAT = "TIMESTAMP";

    static {
        logger = Logger.getLogger(ExportConfiguration.class.getName());
    }

    public ExportConfiguration() {
    }

    public static void loadProperties() {
        //Properties props = new Properties();

        try {
            //props.load(FusionChartsExportHelper.class.getResourceAsStream("/fusioncharts_export.properties"));
            EXPORTHANDLER = System.getProperty("EXPORTHANDLER", EXPORTHANDLER);
            RESOURCEPACKAGE = System.getProperty("RESOURCEPACKAGE", RESOURCEPACKAGE);
            SAVEPATH = System.getProperty("SAVEPATH", SAVEPATH);
            HTTP_URI = System.getProperty("HTTP_URI", HTTP_URI);
            TMPSAVEPATH = System.getProperty("TMPSAVEPATH", TMPSAVEPATH);
            String e = System.getProperty("OVERWRITEFILE", "false");
            OVERWRITEFILE = (new Boolean(e)).booleanValue();
            String INTELLIGENTFILENAMINGSTR = System.getProperty("INTELLIGENTFILENAMING", "true");
            INTELLIGENTFILENAMING = (new Boolean(INTELLIGENTFILENAMINGSTR)).booleanValue();
            FILESUFFIXFORMAT = System.getProperty("FILESUFFIXFORMAT", FILESUFFIXFORMAT);
        } catch (NullPointerException var3) {
            logger.info("NullPointer: Properties file not FOUND");
        }/* catch (FileNotFoundException var4) {
            logger.info("Properties file not FOUND");
        } catch (IOException var5) {
            logger.info("IOException: Properties file not FOUND");
        }*/

    }
}