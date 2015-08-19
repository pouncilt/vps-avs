package gov.va.med.avs.controller;

import gov.va.med.avs.model.*;
import gov.va.med.avs.model.jaxb.adapter.DateAdapter;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Created with IntelliJ IDEA.
 * User: pouncilt
 * Date: 11/5/13
 * Time: 2:46 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/jaxb2-marshaller-context.xml"})
public class MapToXMLTest {
    @Autowired
    @Qualifier("jaxb2Marshaller")
    Jaxb2Marshaller jaxb2Marshaller;

    @Test
    public void testMarshallingJaxbIntroductionMappingsToXMLForWebServiceResponse() throws Exception {
        Date todaysDate = new Date();
        String todaysDateAsString = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(todaysDate);
        byte[] pdfData = getPdf("avs-050_18.pdf");
        String base64EncodedPdfData = Base64.encodeBase64String(pdfData);
        String expectedXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><WebServiceResponse><status><messages><message>AVS Found.</message></messages><requestStatus>Successful</requestStatus></status><payload><afterVisitSummary><id>1</id><veteranId>54321</veteranId><fileName>Pouncil AVS</fileName><description>AVS for Mr. Pouncil.</description><createdBy>MD. House</createdBy><createdDate>" + todaysDateAsString + "</createdDate><base64EncodedPDF>"+ base64EncodedPdfData +"</base64EncodedPDF></afterVisitSummary></payload></WebServiceResponse>";
        StringWriter sw = new StringWriter();

        //JaxbIntros config = IntroductionsConfigParser.parseConfig(new ClassPathResource("spring/jaxb-intros-marshaller-mapping.xml").getInputStream());
        //IntroductionsAnnotationReader reader = new IntroductionsAnnotationReader(config);
        //Map<String, Object> jaxbConfig = new HashMap<String, Object>();

        //jaxbConfig.put(JAXBRIContext.DEFAULT_NAMESPACE_REMAP, "http://localhost:8080/va-avs-ws/schemas");
        //jaxbConfig.put(JAXBRIContext.ANNOTATION_READER, reader);
        //JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] {WebServiceResponse.class}, jaxbConfig);
        jaxb2Marshaller.marshal(getWebServiceResponse(todaysDate), new StreamResult(sw));

        assertEquals(expectedXML, sw.toString());
    }

    @Test
    public void testUnmarshallingXMLToJaxbIntroductionMappingsForWebServiceResponse() throws Exception{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date todaysDate = new Date();
        String todaysDateAsString = dateFormat.format(todaysDate);
        byte[] expectedPdfData = getPdf("avs-050_18.pdf");
        String base64EncodedPdfData = Base64.encodeBase64String(expectedPdfData);
        String actualXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><WebServiceResponse><status><messages><message>AVS Found.</message></messages><requestStatus>Successful</requestStatus></status><payload><afterVisitSummary><id>1</id><veteranId>54321</veteranId><fileName>Pouncil AVS</fileName><description>AVS for Mr. Pouncil.</description><createdBy>MD. House</createdBy><createdDate>" + todaysDateAsString + "</createdDate><base64EncodedPDF>"+base64EncodedPdfData+"</base64EncodedPDF></afterVisitSummary></payload></WebServiceResponse>";
        StringReader reader = new StringReader(actualXML);
        WebServiceResponse response = (WebServiceResponse) jaxb2Marshaller.unmarshal(new StreamSource(reader));

        assertNotNull("null Response", response);
        assertNotNull("null Response Status", response.getStatus());
        WebServicePayload payload = response.getPayload();
        assertNotNull("null Response WebServicePayload", payload);
        //List<AfterVisitSummary> afterVisitSummaries = payload.getAfterVisitSummaries();
        AfterVisitSummary afterVisitSummary = payload.getAfterVisitSummary();

        assertNotNull("null After Visit Summaries", afterVisitSummary);
        //assertTrue("no After Visit Summaries", afterVisitSummary.size() > 0);
        assertEquals("1", "1", afterVisitSummary.getId());
        assertEquals("54321", "54321", afterVisitSummary.getVeteranId());
        assertEquals("Pouncil AVS", "Pouncil AVS", afterVisitSummary.getFileName());
        assertEquals("AVS for Mr. Pouncil.", "AVS for Mr. Pouncil.", afterVisitSummary.getDescription());
        assertEquals(todaysDateAsString, dateFormat.format(todaysDate), dateFormat.format(afterVisitSummary.getCreatedDate()));
        assertEquals("Actual PDf is not equal to Expected PDF", true, Arrays.equals(expectedPdfData, afterVisitSummary.getBinaryPDF()));
    }

    @Test
    public void testMarshallingJaxbIntroductionMappingsToXMLForWebServiceCollectionResponse() throws Exception {
        Date todaysDate = new Date();
        String todaysDateAsString = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(todaysDate);
        byte[] pdfData = getPdf("avs-050_18.pdf");
        String base64EncodedPdfData = Base64.encodeBase64String(pdfData);
        String expectedXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><WebServiceCollectionResponse><status><messages><message>AVS Found.</message></messages><requestStatus>Successful</requestStatus></status><payload><afterVisitSummaries><afterVisitSummary><id>1</id><veteranId>54321</veteranId><fileName>Pouncil AVS</fileName><description>AVS for Mr. Pouncil.</description><createdBy>MD. House</createdBy><createdDate>" + todaysDateAsString + "</createdDate><base64EncodedPDF>"+ base64EncodedPdfData +"</base64EncodedPDF></afterVisitSummary></afterVisitSummaries></payload></WebServiceCollectionResponse>";
        StringWriter sw = new StringWriter();

        //JaxbIntros config = IntroductionsConfigParser.parseConfig(new ClassPathResource("spring/jaxb-intros-marshaller-mapping.xml").getInputStream());
        //IntroductionsAnnotationReader reader = new IntroductionsAnnotationReader(config);
        //Map<String, Object> jaxbConfig = new HashMap<String, Object>();

        //jaxbConfig.put(JAXBRIContext.DEFAULT_NAMESPACE_REMAP, "http://localhost:8080/va-avs-ws/schemas");
        //jaxbConfig.put(JAXBRIContext.ANNOTATION_READER, reader);
        //JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] {WebServiceResponse.class}, jaxbConfig);
        jaxb2Marshaller.marshal(getWebServiceCollectionResponse(todaysDate), new StreamResult(sw));

        assertEquals(expectedXML, sw.toString());
    }

    @Test
    public void testUnmarshallingXMLToJaxbIntroductionMappingsForWebServiceCollectionResponse() throws Exception{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date todaysDate = new Date();
        String todaysDateAsString = dateFormat.format(todaysDate);
        byte[] expectedPdfData = getPdf("avs-050_18.pdf");
        String base64EncodedPdfData = Base64.encodeBase64String(expectedPdfData);
        String actualXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><WebServiceCollectionResponse><status><messages><message>AVS Found.</message></messages><requestStatus>Successful</requestStatus></status><payload><afterVisitSummaries><afterVisitSummary><id>1</id><veteranId>54321</veteranId><fileName>Pouncil AVS</fileName><description>AVS for Mr. Pouncil.</description><createdBy>MD. House</createdBy><createdDate>" + todaysDateAsString + "</createdDate><base64EncodedPDF>"+base64EncodedPdfData+"</base64EncodedPDF></afterVisitSummary></afterVisitSummaries></payload></WebServiceCollectionResponse>";
        StringReader reader = new StringReader(actualXML);
        WebServiceCollectionResponse response = (WebServiceCollectionResponse) jaxb2Marshaller.unmarshal(new StreamSource(reader));

        assertNotNull("null Response", response);
        assertNotNull("null Response Status", response.getStatus());
        WebServiceCollectionPayload payload = response.getPayload();
        assertNotNull("null Response WebServicePayload", payload);
        List<AfterVisitSummary> afterVisitSummaries = payload.getAfterVisitSummaries();

        assertNotNull("null After Visit Summaries", afterVisitSummaries);
        assertTrue("no After Visit Summaries", afterVisitSummaries.size() > 0);
        assertEquals("1", "1", afterVisitSummaries.get(0).getId());
        assertEquals("54321", "54321", afterVisitSummaries.get(0).getVeteranId());
        assertEquals("Pouncil AVS", "Pouncil AVS", afterVisitSummaries.get(0).getFileName());
        assertEquals("AVS for Mr. Pouncil.", "AVS for Mr. Pouncil.", afterVisitSummaries.get(0).getDescription());
        assertEquals(todaysDateAsString, dateFormat.format(todaysDate), dateFormat.format(afterVisitSummaries.get(0).getCreatedDate()));
        assertEquals("Actual PDf is not equal to Expected PDF", true, Arrays.equals(expectedPdfData, afterVisitSummaries.get(0).getBinaryPDF()));
    }

    @Test
    public void testMarshallingJaxbIntroductionMappingsToXMLForAfterVisitSummarySearchRequest() throws Exception {
        String expectedXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><AfterVisitSummarySearchRequest><afterVisitSummarySearchCriteria><clientId>12345</clientId><veteranId>54321</veteranId><startDate>2008-09-28</startDate><endDate>2014-09-18</endDate></afterVisitSummarySearchCriteria></AfterVisitSummarySearchRequest>";
        StringWriter sw = new StringWriter();

        //JaxbIntros config = IntroductionsConfigParser.parseConfig(new ClassPathResource("spring/jaxb-intros-marshaller-mapping.xml").getInputStream());
        //IntroductionsAnnotationReader reader = new IntroductionsAnnotationReader(config);
        //Map<String, Object> jaxbConfig = new HashMap<String, Object>();

        //jaxbConfig.put(JAXBRIContext.DEFAULT_NAMESPACE_REMAP, "http://localhost:8080/va-avs-ws/schemas");
        //jaxbConfig.put(JAXBRIContext.ANNOTATION_READER, reader);
        //JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] {AfterVisitSummarySearchRequest.class}, jaxbConfig);
        jaxb2Marshaller.marshal(getAfterVisitSummarySearchRequest(), new StreamResult(sw));

        assertEquals(expectedXML, sw.toString());
    }

    @Test
    public void testUnmarshallingXMLToJaxbIntroductionMappingsForAfterVisitSummarySearchRequest() throws Exception{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String actualXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><AfterVisitSummarySearchRequest><afterVisitSummarySearchCriteria><clientId>12345</clientId><veteranId>54321</veteranId><startDate>2008-09-28</startDate><endDate>2014-09-18</endDate></afterVisitSummarySearchCriteria></AfterVisitSummarySearchRequest>";
        StringReader reader = new StringReader(actualXML);
        AfterVisitSummarySearchRequest avsSearchRequest = (AfterVisitSummarySearchRequest) jaxb2Marshaller.unmarshal(new StreamSource(reader));

        assertNotNull("null Search Request", avsSearchRequest);
        assertNotNull("null Search Criteria", avsSearchRequest.getAfterVisitSummarySearchCriteria());
        assertEquals("12345", "12345", avsSearchRequest.getAfterVisitSummarySearchCriteria().getClientId());
        assertEquals("54321", "54321", avsSearchRequest.getAfterVisitSummarySearchCriteria().getVeteranId());
        assertEquals("2008-09-28", "2008-09-28", dateFormat.format(avsSearchRequest.getAfterVisitSummarySearchCriteria().getStartDate()));
        assertEquals("2014-09-18", "2014-09-18", dateFormat.format(avsSearchRequest.getAfterVisitSummarySearchCriteria().getEndDate()));
    }

    private AfterVisitSummarySearchRequest getAfterVisitSummarySearchRequest() throws Exception{
        DateAdapter dta = new DateAdapter();
        Date date20080928 = dta.unmarshal("2008-09-28");
        Date date20140918 = dta.unmarshal("2014-09-18");
        AfterVisitSummarySearchCriteria searchCriteria = new AfterVisitSummarySearchCriteria("12345", "54321", date20080928, date20140918);
        return new AfterVisitSummarySearchRequest(searchCriteria);
    }

    private WebServiceResponse getWebServiceResponse(Date avsDate) {
        List<String> messages = new ArrayList<String>();
        messages.add("AVS Found.");
        WebServiceResponseStatus status = new WebServiceResponseStatus(WebServiceResponseStatus.Request.Successful, messages);
        AfterVisitSummary afterVisitSummary = new AfterVisitSummary("1", "54321", "Pouncil AVS", "AVS for Mr. Pouncil.", "MD. House", avsDate, getPdf("avs-050_18.pdf"));

        WebServicePayload payload = new WebServicePayload(afterVisitSummary);
        return new WebServiceResponse(status, payload);
    }

    private WebServiceCollectionResponse getWebServiceCollectionResponse(Date avsDate) {
        List<String> messages = new ArrayList<String>();
        messages.add("AVS Found.");
        WebServiceResponseStatus status = new WebServiceResponseStatus(WebServiceResponseStatus.Request.Successful, messages);
        AfterVisitSummary afterVisitSummary = new AfterVisitSummary("1", "54321", "Pouncil AVS", "AVS for Mr. Pouncil.", "MD. House", avsDate, getPdf("avs-050_18.pdf"));
        List<AfterVisitSummary> afterVisitSummaries = new ArrayList<AfterVisitSummary>();
        afterVisitSummaries.add(afterVisitSummary);
        WebServiceCollectionPayload payload = new WebServiceCollectionPayload(afterVisitSummaries);
        return new WebServiceCollectionResponse(status, payload);
    }

    private byte[] getPdf(String pdfPath) {
        Resource pdf = new ClassPathResource(pdfPath);
        byte[] pdfBytes = new byte[0];
        try{
            pdfBytes = IOUtils.toByteArray(pdf.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pdfBytes;
    }
}