package gov.va.med.avs.client;

import gov.va.med.avs.model.*;
import gov.va.med.avs.model.jaxb.adapter.DateAdapter;
import gov.va.med.avs.model.jaxb.adapter.DateTimeAdapter;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by tpouncil on 6/9/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/test-application-context.xml"})
public class AfterVisitSummaryClientTest {
    @Autowired(required=true)
    AfterVisitSummaryClient avsRestfulClient;

    @Test
    public void test() {
        assertTrue(true);
    }

    @Ignore
    public void testRetrievingAfterVisitSummariesUsingXml() {
        try {
            String uri = "http://localhost:8080/vps-avs-ws/service/afterVisitSummaries";
            Header[] headers = new Header[2];
            headers[0] = new BasicHeader("Accept", "application/xml");
            headers[1] = new BasicHeader("Content-Type", "application/xml");
            AfterVisitSummarySearchRequest avsSearchRequest = getAfterVisitSummarySearchRequest();
            WebServiceCollectionResponse response = getAvsRestfulClient().getAfterVisitSummaries(uri, headers, avsSearchRequest);
            assertNotNull(response);
        } catch(Exception e) {
            fail(e.getMessage());
        }
    }

    @Ignore
    public void testRetrievingAfterVisitSummariesUsingJson() {
        try {
            String uri = "http://localhost:8080/vps-avs-ws/service/afterVisitSummaries";
            Header[] headers = new Header[2];
            headers[0] = new BasicHeader("Accept", "application/json");
            headers[1] = new BasicHeader("Content-Type", "application/json");
            AfterVisitSummarySearchRequest avsSearchRequest = getAfterVisitSummarySearchRequest();
            WebServiceCollectionResponse response = getAvsRestfulClient().getAfterVisitSummaries(uri, headers, avsSearchRequest);
            assertNotNull(response);
        } catch(Exception e) {
            fail(e.getMessage());
        }
    }

    @Ignore
    public void testGetAfterVisitSummaryUsingXml() {
        try {
            String avsId = "195";
            String clientId = "12345";
            String veteranId = "18";
            String uri = "http://localhost:8080/vps-avs-ws/service/afterVisitSummaries/"+avsId+".xml?clientId="+clientId+"&veteranId="+veteranId;
            Header[] headers = new Header[2];
            headers[0] = new BasicHeader("Accept", "application/xml");
            headers[1] = new BasicHeader("Content-Type", "application/xml");
            AfterVisitSummary avs = getAfterVisitSummary();
            WebServiceResponse response = getAvsRestfulClient().getAfterVisitSummary(uri, headers, avs);
            assertNotNull(response);
        } catch(Exception e) {
            fail(e.getMessage());
        }
    }

    @Ignore
    public void testGetAfterVisitSummaryUsingJson() {
        try {
            String avsId = "195";
            String clientId = "12345";
            String veteranId = "18";
            String uri = "http://localhost:8080/vps-avs-ws/service/afterVisitSummaries/"+avsId+".json?clientId="+clientId+"&veteranId="+veteranId;
            Header[] headers = new Header[2];
            headers[0] = new BasicHeader("Accept", "application/json");
            headers[1] = new BasicHeader("Content-Type", "application/json");
            AfterVisitSummary avs = getAfterVisitSummary();
            WebServiceResponse response = getAvsRestfulClient().getAfterVisitSummary(uri, headers, avs);
            assertNotNull(response);
        } catch(Exception e) {
            fail(e.getMessage());
        }
    }

    private AfterVisitSummarySearchRequest getAfterVisitSummarySearchRequest() throws Exception{
        DateAdapter dta = new DateAdapter();
        Date startDate = dta.unmarshal("2015-02-01");
        Date endDate = dta.unmarshal("2015-05-31");
        String clientId = "12345";
        String veteranId = "18";
        AfterVisitSummarySearchCriteria searchCriteria = new AfterVisitSummarySearchCriteria(clientId, veteranId, startDate, endDate);
        return new AfterVisitSummarySearchRequest(searchCriteria);
    }

    private AfterVisitSummary getAfterVisitSummary() throws Exception {
        DateTimeAdapter dta = new DateTimeAdapter();
        Date createdDate = dta.unmarshal("2015-05-14T10:22:33");
        String id = "195";
        String clientId = "12345";
        String veteranId = "18";
        String filename = "ZZ000000000195.PDF";
        String createdBy = "CPRSSUPER,REG";

        return new AfterVisitSummary(id, veteranId, filename, createdBy, createdDate);
    }

    public AfterVisitSummaryClient getAvsRestfulClient() {
        return avsRestfulClient;
    }

    public void setAvsRestfulClient(AfterVisitSummaryClient avsRestfulClient) {
        this.avsRestfulClient = avsRestfulClient;
    }
}
