package com.fusioncharts.exporter.error;

import com.fusioncharts.exporter.beans.ExportConfiguration;
import com.fusioncharts.exporter.beans.LogMessageSetVO;
import com.fusioncharts.exporter.error.LOGMESSAGE;
import com.fusioncharts.exporter.error.Status;
import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Logger;

public class ErrorHandler {
    private static Logger logger = null;

    static {
        logger = Logger.getLogger(ErrorHandler.class.getName());
    }

    public ErrorHandler() {
    }

    public static String buildResponse(LogMessageSetVO logMessageSetVO, boolean isHTML) {
        StringBuffer err_buf = new StringBuffer();
        StringBuffer warn_buf = new StringBuffer();
        String errors = "";
        String notices = "";
        Set errorSet = logMessageSetVO.getErrorsSet();
        Set warningSet = logMessageSetVO.getWarningSet();
        Iterator var9 = errorSet.iterator();

        Enum otherMessages;
        while(var9.hasNext()) {
            otherMessages = (Enum)var9.next();
            err_buf.append(otherMessages.toString());
        }

        var9 = warningSet.iterator();

        while(var9.hasNext()) {
            otherMessages = (Enum)var9.next();
            err_buf.append(otherMessages.toString());
        }

        if(err_buf.length() > 0) {
            errors = (isHTML?"<BR>":"&") + "statusMessage=" + err_buf.substring(0) + (isHTML?"<BR>":"&") + "statusCode=" + Status.FAILURE.getCode();
        } else {
            errors = "statusMessage=" + Status.SUCCESS + "&statusCode=" + Status.SUCCESS.getCode();
        }

        if(warn_buf.length() > 0) {
            notices = (isHTML?"<BR>":"&") + "notice=" + warn_buf.substring(0);
        }

        String otherMessages1 = logMessageSetVO.getOtherMessages();
        otherMessages1 = otherMessages1 == null?"":otherMessages1;
        logger.info("Errors=" + errors);
        logger.info("Notices=" + notices);
        logger.info("Miscellaneous Messages=" + otherMessages1);
        return errors + notices + otherMessages1;
    }

    public static String buildResponse(String eCodes, boolean isHTML) {
        StringTokenizer tokenizer = new StringTokenizer(eCodes, ",");
        StringBuffer err_buf = new StringBuffer();
        StringBuffer warn_buf = new StringBuffer();
        String errors = "";
        String notices = "";
        String errCode = null;

        while(tokenizer.hasMoreTokens()) {
            errCode = tokenizer.nextToken();
            if(errCode.length() > 0) {
                if(errCode.indexOf("E") != -1) {
                    err_buf.append(LOGMESSAGE.valueOf(errCode));
                } else {
                    warn_buf.append(LOGMESSAGE.valueOf(errCode));
                }
            }
        }

        if(err_buf.length() > 0) {
            errors = (isHTML?"<BR>":"&") + "statusMessage=" + err_buf.substring(0) + (isHTML?"<BR>":"&") + "statusCode=" + Status.FAILURE.getCode();
        } else {
            errors = "statusMessage=" + Status.SUCCESS + "&statusCode=" + Status.SUCCESS.getCode();
        }

        if(warn_buf.length() > 0) {
            notices = (isHTML?"<BR>":"&") + "notice=" + warn_buf.substring(0);
        }

        logger.info("Errors=" + errors);
        logger.info("Notices=" + notices);
        return errors + notices;
    }

    public static LogMessageSetVO checkServerSaveStatus(String fileName) {
        LogMessageSetVO errorSetVO = new LogMessageSetVO();
        String pathToSaveFolder = ExportConfiguration.SAVEABSOLUTEPATH;
        File saveFolder = new File(pathToSaveFolder);
        if(!saveFolder.exists()) {
            errorSetVO.addError(LOGMESSAGE.E508);
        } else if(!saveFolder.canWrite()) {
            errorSetVO.addError(LOGMESSAGE.E403);
        } else {
            String completeFilePath = pathToSaveFolder + File.separator + fileName;
            File saveFile = new File(completeFilePath);
            if(saveFile.exists()) {
                errorSetVO.addWarning(LOGMESSAGE.W509);
                if(ExportConfiguration.OVERWRITEFILE) {
                    errorSetVO.addWarning(LOGMESSAGE.W510);
                    if(!saveFile.canWrite()) {
                        errorSetVO.addError(LOGMESSAGE.E511);
                    }
                } else if(!ExportConfiguration.INTELLIGENTFILENAMING) {
                    errorSetVO.addError(LOGMESSAGE.E512);
                }
            }
        }

        return errorSetVO;
    }

    public static boolean doesServerSaveFolderExist() {
        boolean saveFolderExists = true;
        String pathToSaveFolder = ExportConfiguration.SAVEABSOLUTEPATH;
        File saveFolder = new File(pathToSaveFolder);
        if(!saveFolder.exists()) {
            saveFolderExists = false;
        }

        return saveFolderExists;
    }
}