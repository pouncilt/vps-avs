package com.fusioncharts.exporter.beans;

import com.fusioncharts.exporter.FusionChartsExportHelper;
import com.fusioncharts.exporter.beans.ChartMetadata;
import com.fusioncharts.exporter.beans.ExportParameterNames;
import com.fusioncharts.exporter.beans.LogMessageSetVO;
import com.fusioncharts.exporter.error.LOGMESSAGE;
import java.util.HashMap;
import java.util.Iterator;

public class ExportBean {
    private ChartMetadata metadata;
    private String stream;
    private HashMap<String, Object> exportParameters = null;

    public ExportBean() {
        this.exportParameters = new HashMap();
        this.exportParameters.put(ExportParameterNames.EXPORTFILENAME.toString(), "FusionCharts");
        this.exportParameters.put(ExportParameterNames.EXPORTACTION.toString(), "download");
        this.exportParameters.put(ExportParameterNames.EXPORTTARGETWINDOW.toString(), "_self");
        this.exportParameters.put(ExportParameterNames.EXPORTFORMAT.toString(), "PDF");
    }

    public void addExportParameter(String parameterName, Object value) {
        this.exportParameters.put(parameterName.toLowerCase(), value);
    }

    public void addExportParametersFromMap(HashMap<String, String> moreParameters) {
        this.exportParameters.putAll(moreParameters);
    }

    public HashMap<String, Object> getExportParameters() {
        return new HashMap(this.exportParameters);
    }

    public Object getExportParameterValue(String key) {
        return this.exportParameters.get(key);
    }

    public ChartMetadata getMetadata() {
        return this.metadata;
    }

    public String getMetadataAsQueryString(String filePath, boolean isError, boolean isHTML) {
        String queryParams = "";
        if(isError) {
            queryParams = queryParams + (isHTML?"<BR>":"&") + "width=0";
            queryParams = queryParams + (isHTML?"<BR>":"&") + "height=0";
        } else {
            queryParams = queryParams + (isHTML?"<BR>":"&") + "width=" + this.metadata.getWidth();
            queryParams = queryParams + (isHTML?"<BR>":"&") + "height=" + this.metadata.getHeight();
        }

        queryParams = queryParams + (isHTML?"<BR>":"&") + "DOMId=" + this.metadata.getDOMId();
        if(filePath != null) {
            queryParams = queryParams + (isHTML?"<BR>":"&") + "fileName=" + filePath;
        }

        return queryParams;
    }

    public String getParametersAndMetadataAsQueryString() {
        String queryParams = "";
        queryParams = queryParams + "?width=" + this.metadata.getWidth();
        queryParams = queryParams + "&height=" + this.metadata.getHeight();
        queryParams = queryParams + "&bgcolor=" + this.metadata.getBgColor();

        String key;
        String value;
        for(Iterator iter = this.exportParameters.keySet().iterator(); iter.hasNext(); queryParams = queryParams + "&" + key + "=" + value) {
            key = (String)iter.next();
            value = (String)this.exportParameters.get(key);
        }

        return queryParams;
    }

    public String getStream() {
        return this.stream;
    }

    public boolean isHTMLResponse() {
        boolean isHTML = false;
        String exportAction = (String)this.getExportParameterValue(ExportParameterNames.EXPORTACTION.toString());
        if(exportAction.equals("download")) {
            isHTML = true;
        }

        return isHTML;
    }

    public void setExportParameters(HashMap<String, Object> exportParameters) {
        this.exportParameters = exportParameters;
    }

    public void setMetadata(ChartMetadata metadata) {
        this.metadata = metadata;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public LogMessageSetVO validate() {
        LogMessageSetVO errorSetVO = new LogMessageSetVO();
        if(this.getMetadata().getWidth() == -1 || this.getMetadata().getHeight() == -1 || this.getMetadata().getWidth() == 0 || this.getMetadata().getHeight() == 0) {
            errorSetVO.addError(LOGMESSAGE.E101);
        }

        if(this.getMetadata().getBgColor() == null) {
            errorSetVO.addWarning(LOGMESSAGE.W513);
        }

        if(this.getStream() == null) {
            errorSetVO.addError(LOGMESSAGE.E100);
        }

        if(this.exportParameters != null && !this.exportParameters.isEmpty()) {
            String exportFormat = (String)this.getExportParameterValue(ExportParameterNames.EXPORTFORMAT.toString());
            boolean exportFormatSupported = FusionChartsExportHelper.getHandlerAssociationsMap().containsKey(exportFormat.toUpperCase());
            if(!exportFormatSupported) {
                errorSetVO.addError(LOGMESSAGE.E517);
            }
        } else {
            errorSetVO.addWarning(LOGMESSAGE.W102);
        }

        return errorSetVO;
    }
}