package gov.va.med.avs.controller;

import gov.va.med.avs.exception.*;
import gov.va.med.avs.model.*;
import gov.va.med.avs.model.jaxb.adapter.DateTimeAdapter;
import gov.va.med.avs.service.AfterVisitSummaryService;
import gov.va.med.lom.avs.model.UsageLog;
import gov.va.med.lom.avs.service.ServiceFactory;
import gov.va.med.lom.avs.util.AvsUtils;
import gov.va.med.lom.vistabroker.exception.VistaBrokerConnectionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class AfterVisitSummaryController {
    @Autowired
    private AfterVisitSummaryService afterVisitSummaryService;
    private Log log = LogFactory.getLog(AfterVisitSummaryController.class);

    @RequestMapping(value = "/afterVisitSummaries", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public WebServiceCollectionResponse getAfterVisitSummaries(@RequestParam("clientId") String clientId, @RequestParam("veteranId") String veteranId,
                                                               @RequestBody AfterVisitSummarySearchRequest afterVisitSummarySearchRequest) {
        //usageLog("AVS WebService - Get After Visit Summaries", afterVisitSummarySearchRequest, request);
        List<AfterVisitSummary> afterVisitSummaries = getAfterVisitSummaryService().findAfterVisitSummaries(afterVisitSummarySearchRequest);
        WebServiceCollectionPayload payload = new WebServiceCollectionPayload(afterVisitSummaries);
        WebServiceCollectionResponse response = new WebServiceCollectionResponse(new WebServiceResponseStatus(WebServiceResponseStatus.Request.Successful, new ArrayList<String>()), payload);
        //usageLog("AVS WebService - Get After Visit Summaries", response.toString(), clientId, veteranId, request.getLocalName());
        return response;
    }

    @RequestMapping(value = "/afterVisitSummaries/{afterVisitSummaryId}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public WebServiceResponse getAfterVisitSummary(@PathVariable String afterVisitSummaryId,
                                                   @RequestParam("clientId") String clientId, @RequestParam("veteranId") String veteranId,
                                                   @RequestBody AfterVisitSummary afterVisitSummary) {
        //usageLog("AVS WebService - Get After Visit Summary", afterVisitSummary.toString(), clientId, veteranId, request.getLocalName());
        getAfterVisitSummaryService().getAfterVisitSummary(clientId, veteranId, afterVisitSummary);
        WebServicePayload payload = new WebServicePayload(afterVisitSummary);
        return new WebServiceResponse(new WebServiceResponseStatus(WebServiceResponseStatus.Request.Successful, new ArrayList<String>()), payload);
    }

    @ExceptionHandler(VeteranNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public WebServiceResponse handleException(VeteranNotFoundException e) {
        List<String> messages = e.getMessages();
        return new WebServiceResponse(new WebServiceResponseStatus(WebServiceResponseStatus.Request.Failure, messages));
    }

    @ExceptionHandler(AfterVisitSummariesNotFoundException.class)
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public WebServiceResponse handleException(AfterVisitSummariesNotFoundException e) {
        List<String> messages = e.getMessages();
        return new WebServiceResponse(new WebServiceResponseStatus(WebServiceResponseStatus.Request.Successful, messages));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public WebServiceResponse handleException(IllegalArgumentException e) {
        List<String> messages = new ArrayList<String>();
        messages.add(e.getMessage());

        return new WebServiceResponse(new WebServiceResponseStatus(WebServiceResponseStatus.Request.Failure, messages));
    }

    @ExceptionHandler(RequiredElementNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public WebServiceResponse handleException(RequiredElementNotFoundException e) {
        List<String> messages = e.getMessages();
        return new WebServiceResponse(new WebServiceResponseStatus(WebServiceResponseStatus.Request.Failure, messages));
    }

    @ExceptionHandler(RequestedResourceTooLargeException.class)
    @ResponseStatus(HttpStatus.REQUEST_ENTITY_TOO_LARGE)
    @ResponseBody
    public WebServiceResponse handleException(RequestedResourceTooLargeException e) {
        List<String> messages = e.getMessages();
        return new WebServiceResponse(new WebServiceResponseStatus(WebServiceResponseStatus.Request.Failure, messages));
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public WebServiceResponse handleException(InvalidDateRangeException e) {
        List<String> messages = e.getMessages();
        return new WebServiceResponse(new WebServiceResponseStatus(WebServiceResponseStatus.Request.Failure, messages));
    }

    @ExceptionHandler(InvalidDateFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public WebServiceResponse handleException(InvalidDateFormatException e) {
        List<String> messages = e.getMessages();
        return new WebServiceResponse(new WebServiceResponseStatus(WebServiceResponseStatus.Request.Failure, messages));
    }

    @ExceptionHandler(AfterVisitSummaryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public WebServiceResponse handleException(AfterVisitSummaryNotFoundException e) {
        List<String> messages = e.getMessages();
        return new WebServiceResponse(new WebServiceResponseStatus(WebServiceResponseStatus.Request.Failure, messages));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public WebServiceResponse handleException(Exception e) {
        List<String> messages = new ArrayList<String>();
        messages.add(e.getMessage());
        return new WebServiceResponse(new WebServiceResponseStatus(WebServiceResponseStatus.Request.Failure, messages));
    }

    @ExceptionHandler(ConfigurationFileNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public WebServiceResponse handleException(ConfigurationFileNotFoundException e) {
        List<String> messages = e.getMessages();
        return new WebServiceResponse(new WebServiceResponseStatus(WebServiceResponseStatus.Request.Failure, messages));
    }

    @ExceptionHandler(VistaBrokerConnectionException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public WebServiceResponse handleException(VistaBrokerConnectionException e) {
        List<String> messages = new ArrayList<String>();
        messages.add(e.getMessage());

        return new WebServiceResponse(new WebServiceResponseStatus(WebServiceResponseStatus.Request.Failure, messages));
    }

    private String printAfterVisitSummaries(List<AfterVisitSummary> afterVisitSummaries) {
        StringBuilder sb = new StringBuilder("afterVisitSummaries" + afterVisitSummaries.size() + "] details:\n");
        int index = 0;

        for (AfterVisitSummary afterVisitSummary : afterVisitSummaries) {
            sb.append("afterVisitSummaries[" + (index++) + "]: " + afterVisitSummary + "\n");
        }

        return sb.toString();
    }

    public AfterVisitSummaryService getAfterVisitSummaryService() {
        return afterVisitSummaryService;
    }

    public void setAfterVisitSummaryService(AfterVisitSummaryService afterVisitSummaryService) {
        this.afterVisitSummaryService = afterVisitSummaryService;
    }
}
