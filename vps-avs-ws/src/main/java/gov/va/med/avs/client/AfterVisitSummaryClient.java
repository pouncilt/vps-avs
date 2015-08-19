package gov.va.med.avs.client;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.va.med.avs.model.*;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGetWithEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * Created by tpouncil on 6/9/2015.
 */

public class AfterVisitSummaryClient {
    @Autowired
    @Qualifier("jaxb2Marshaller")
    Jaxb2Marshaller jaxb2Marshaller;

    public WebServiceResponse getAfterVisitSummary(final String uri, Header[] headers, AfterVisitSummary avs ) throws JsonProcessingException{
        final boolean processXmlRequest = headersContainsAcceptXml(headers);
        final String payload = unmarshallAfterVisitSummary(avs, processXmlRequest);

        try {
            HttpClient client = HttpClients.createDefault();
            HttpGetWithEntity entity = new HttpGetWithEntity();
            entity.setEntity(new StringEntity(payload));
            entity.setURI(new URI(uri));
            entity.setHeaders(headers);

            HttpResponse httpResponse = client.execute(entity);

            if(processXmlRequest){
                return (WebServiceResponse) jaxb2Marshaller.unmarshal(new StreamSource(httpResponse.getEntity().getContent()));
            } else {
                return new ObjectMapper().readValue(httpResponse.getEntity().getContent(), WebServiceResponse.class);
            }

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public WebServiceCollectionResponse getAfterVisitSummaries(final String uri, Header[] headers, AfterVisitSummarySearchRequest avsSearchRequest ) throws JsonProcessingException{
        final boolean processXmlRequest = headersContainsAcceptXml(headers);
        final String payload = unmarshallAfterVisitSummarySearchRequest(avsSearchRequest, processXmlRequest);

        try {
            HttpClient client = HttpClients.createDefault();
            HttpGetWithEntity entity = new HttpGetWithEntity();
            entity.setEntity(new StringEntity(payload));
            entity.setURI(new URI(uri));
            entity.setHeaders(headers);

            HttpResponse httpResponse = client.execute(entity);

            if(processXmlRequest){
                return (WebServiceCollectionResponse) jaxb2Marshaller.unmarshal(new StreamSource(httpResponse.getEntity().getContent()));
            } else {
                return new ObjectMapper().readValue(httpResponse.getEntity().getContent(), WebServiceCollectionResponse.class);
            }

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean headersContainsAcceptXml(Header[] headers) {
        boolean headerHasAcceptXml = false;

        if(headers != null){
            for(int i = 0; i < headers.length; i++){
                if(headers[i].getName().equalsIgnoreCase("Accept") && headers[i].getValue().toLowerCase().contains("xml")){
                    headerHasAcceptXml = true;
                    break;
                }
            }
        }

        return headerHasAcceptXml;
    }

    private String unmarshallAfterVisitSummarySearchRequest(AfterVisitSummarySearchRequest avsSearchRequest, boolean processXmlRequest) throws JsonProcessingException {
        String payload = null;
        if(processXmlRequest){
            StringWriter sw = new StringWriter();
            jaxb2Marshaller.marshal(avsSearchRequest, new StreamResult(sw));
            payload = sw.toString();
        } else {
            payload = new ObjectMapper().writeValueAsString(avsSearchRequest);
        }

        return payload;
    }

    private String unmarshallAfterVisitSummary(AfterVisitSummary avs, boolean processXmlRequest) throws JsonProcessingException {
        String payload = null;
        if(processXmlRequest){
            StringWriter sw = new StringWriter();
            jaxb2Marshaller.marshal(avs, new StreamResult(sw));
            payload = sw.toString();
        } else {
            payload = new ObjectMapper().writeValueAsString(avs);
        }

        return payload;
    }
}
