package gov.va.med.avs.filters;

import gov.va.med.avs.model.AfterVisitSummarySearchRequest;
import gov.va.med.avs.model.jaxb.adapter.DateTimeAdapter;
import gov.va.med.lom.avs.model.UsageLog;
import gov.va.med.lom.avs.service.ServiceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by tpouncil on 6/24/2015.
 */
public class UsageLogFilter extends CommonsRequestLoggingFilter {
    private Log log = LogFactory.getLog(UsageLogFilter.class);
    private String beforeResponseMessagePrefix = "Before response [";
    private String beforeResponseMessageSuffix = "]";
    private String afterResponseMessagePrefix = "After response [";
    private String afterResponseMessageSuffix = "]";

    public UsageLogFilter () {
        super();
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        super.beforeRequest(request, message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        super.afterRequest(request, message);
        String serverName = request.getLocalName();
        String action = getAction(request.getPathInfo());
        String postBody = message.substring(message.indexOf("payload=") + 8);
        String clientId = request.getParameter("clientId");
        String veteranId = request.getParameter("veteranId");

        log.info("About to call UsageLogFilter.afterRequest().usageLog() method.");
        usageLog(action, postBody, clientId, veteranId, serverName);
        log.info("Finish calling UsageLogFilter.afterRequest().usageLog() method.");
    }

    protected void afterResponse(HttpServletRequest request, HttpServletResponse response, String message) {
        String serverName = request.getLocalName();
        String action = getAction(request.getPathInfo());
        String postBody = message.substring(message.indexOf("payload=") + 8);
        String clientId = request.getParameter("clientId");
        String veteranId = request.getParameter("veteranId");

        log.info("About to call UsageLogFilter.afterResponse().usageLog() method.");
        usageLog(action, postBody, clientId, veteranId, serverName);
        log.info("Finish calling UsageLogFilter.afterResponse().usageLog() method.");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Object responseToUse = new ContentCachingResponseWrapper(response);
        Object requestToUse = new ContentCachingRequestWrapper(request);

        try {
            super.doFilterInternal(request, (HttpServletResponse) responseToUse, filterChain);
        } finally {
            String responseBody = this.getResponseBody((HttpServletResponse) responseToUse);
            this.afterResponse((HttpServletRequest) requestToUse, (HttpServletResponse)responseToUse, responseBody);
            response.getOutputStream().write(responseBody.getBytes());
        }
    }

    private String getResponseBody(HttpServletResponse response) {
        return this.createMessage(response, this.afterResponseMessagePrefix, this.afterResponseMessageSuffix);
    }

    protected String createMessage(HttpServletResponse response, String prefix, String suffix) {
        StringBuilder msg = new StringBuilder();
        msg.append(prefix);

        if(this.isIncludePayload()) {
            ContentCachingResponseWrapper responseWrapper = (ContentCachingResponseWrapper)response;
            byte[] buf1 = responseWrapper.getContentAsByteArray();
            if(buf1.length > 0) {
                int bufferLength = Math.min(buf1.length, this.getMaxPayloadLength());
                String payload;
                try {
                    payload = new String(buf1, 0, bufferLength, responseWrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException var10) {
                    payload = "[unknown]";
                }

                msg.append("payload=").append(payload);
            }
        }

        msg.append(suffix);
        return msg.toString();
    }

    public String getRequestBody(HttpServletRequest request, String action) {
        StringBuilder sb = new StringBuilder();

        if (action != null) {
            try {
                BufferedReader reader = request.getReader();
                reader.mark(10000);

                String line;
                do {
                    line = reader.readLine();
                    sb.append(line).append("\n");
                } while (line != null);
                reader.reset();
                // do NOT close the reader here, or you won't be able to get the post data twice
            } catch(IOException e) {
                log.warn("getRequestBody couldn't.. get the post data", e);  // This has happened if the request's reader is closed
            }
        }


        return sb.toString();
    }
    private String getAction(String pathInfo) {
        log.info("About to call UsageLogFilter.getAction() method. pathInfo: " + pathInfo);
        String action = null;
        if(pathInfo != null) {
            if(pathInfo.matches("/afterVisitSummaries(.xml|.json){1,1}")){
                action = "AVS Restful WebService - Get After Visit Summaries";
            } else if (pathInfo.matches("/afterVisitSummaries/[0-9]+(.xml|.json){1,1}")) {
                action = "AVS Restful WebService - Get After Visit Summary";
            }
        }
        log.info("About to call UsageLogFilter.getAction() method. action: " + action);
        return action;
    }

    protected UsageLog usageLog(String action, String data, String clientId, String veteranId, String serverName) {
        String division = System.getProperty("vista.link.division");
        String duz = System.getProperty("vista.link.duz");
        Date currentDateTimeStamp = new Date();

        log.info("About to call UsageLogFilter.usageLog() action: " + action);
        log.info("About to call UsageLogFilter.usageLog() data: " + data);
        log.info("About to call UsageLogFilter.usageLog() clientId: " + clientId);
        log.info("About to call UsageLogFilter.usageLog() veteranId: " + veteranId);
        log.info("About to call UsageLogFilter.usageLog() serverName: " + serverName);
        log.info("About to call UsageLogFilter.usageLog() division: " + division);
        log.info("About to call UsageLogFilter.usageLog() duz: " + duz);



        UsageLog ul = null;
        try {
            StringBuffer sb = new StringBuffer(action);
            sb.append(" - ");
            sb.append(data);
            sb.append(" (Facility=");
            sb.append(division);
            sb.append(", DUZ=");
            sb.append(duz);
            sb.append(", DFN=");
            sb.append(veteranId);
            sb.append(", LOC=");
            sb.append(clientId);
            sb.append(", DT=");
            sb.append(new DateTimeAdapter().marshal(currentDateTimeStamp));
            sb.append(")");
            log.info(sb.toString());
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            ul = new UsageLog();
            ul.setAction(action);
            ul.setData(truncateData(data));
            ul.setFacilityNo(division);
            ul.setUserDuz(duz);
            ul.setPatientDfn(veteranId);
            /*if (this.getLocationIens() != null) {
                ul.setLocationIens(AvsUtils.delimitString(this.getLocationIens(), false));
            }*/
            if (clientId != null) {
                ul.setLocationNames(clientId);
            }
            ul.setDatetimes(new DateTimeAdapter().marshal(currentDateTimeStamp));
            ul.setServerName(serverName);

            log.info("About to call ServiceFactory.getSettingsService(,,,,,,,...) method.");
            //ServiceFactory.getSettingsService().saveUsageLog(ul.getFacilityNo(), ul.getData(), ul.getPatientDfn(),
            //        ul.getLocationIens(), ul.getLocationNames(), ul.getDatetimes(), ul.getAction(), ul.getData(), ul.getServerName());
            ServiceFactory.getSettingsService().saveUsageLog(ul);
            log.info("Finish calling ServiceFactory.getSettingsService(,,,,,,,...) method.");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return ul;
    }

    public String truncateData(String data) {
        String truncateMessage = "... THE REST OF PAYLOAD WAS TRUNCATED. SEE LOGS FOR REST OF PAYLOAD...";
        if(data.length() > 255 ) {
            data = data.substring(0, 254-truncateMessage.length());
            data = data + truncateMessage;
        }
        return data;
    }
}
