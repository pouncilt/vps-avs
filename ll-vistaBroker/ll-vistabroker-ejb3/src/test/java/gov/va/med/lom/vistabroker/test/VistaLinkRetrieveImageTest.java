package gov.va.med.lom.vistabroker.test;

import gov.va.med.lom.foundation.service.response.CollectionServiceResponse;
import gov.va.med.lom.vistabroker.patient.data.VistaImageInfo;
import gov.va.med.lom.vistabroker.patient.data.VistaImageQueueInfo;
import gov.va.med.lom.vistabroker.security.ISecurityContext;
import gov.va.med.lom.vistabroker.security.SecurityContextFactory;
import gov.va.med.lom.vistabroker.util.FMDateUtils;
import gov.va.med.lom.vistabroker.util.VistaBrokerServiceFactory;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by tpouncil on 3/11/2015.
 */
public class VistaLinkRetrieveImageTest {

    private List<String> minimumParamsToRetrieveVistaImages;

    @Test
    public void testImageRetrieval() {
        String division = "050";
        String duz = "32";
        assertTrue(true);
        try {
            // Set security context
            ISecurityContext securityContext = SecurityContextFactory.createDuzSecurityContext(division, duz);   //connector,avs .c(division, duz);
            List<String> params = getMinimumParamsToRetrieveVistaImages();
            CollectionServiceResponse<VistaImageQueueInfo> csr = VistaBrokerServiceFactory.getPatientVBService().retrieveVistaImages(securityContext, params);

            List<VistaImageQueueInfo> vistaImageInfoQueueList = (List<VistaImageQueueInfo>) csr.getCollection();
            assertNotNull("vistaImageInfoQueueList cannot be null", vistaImageInfoQueueList);

            if ((vistaImageInfoQueueList != null) && (vistaImageInfoQueueList.size() > 0)) {
                for (VistaImageQueueInfo vistaImageQueueInfo : vistaImageInfoQueueList) {
                    assertNotNull("vistaImageInfoQueueList cannot be null", vistaImageInfoQueueList);
                    System.out.println("VI Import Result: " + vistaImageQueueInfo);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            fail("test failed because of unexcepted exception was thrown.");
        }
    }

    @Test
    public void testImageMetaDataRetrieval() {
        String division = "050";
        String duz = "32";
        assertTrue(true);
        try {
            // Set security context
            ISecurityContext securityContext = SecurityContextFactory.createDuzSecurityContext(division, duz);   //connector,avs .c(division, duz);
            List<Object> params = getMinimumParamsToRetrieveVistaImagesMetaData();
            CollectionServiceResponse<VistaImageInfo> csr = VistaBrokerServiceFactory.getPatientVBService().retrieveVistaImageDescriptions(securityContext, params);

            List<VistaImageInfo> vistaImageInfoList = (List<VistaImageInfo>) csr.getCollection();
            assertNotNull("vistaImageInfoList cannot be null", vistaImageInfoList);

            if ((vistaImageInfoList != null) && (vistaImageInfoList.size() > 0)) {
                for (VistaImageInfo vistaImageInfo : vistaImageInfoList) {
                    System.out.println("VI Import Result: " + vistaImageInfo);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            fail("test failed because of unexcepted exception was thrown.");
        }
    }

    public List<Object> getMinimumParamsToRetrieveVistaImagesMetaData() {
        Calendar march_01_2015 = new GregorianCalendar(2015, 2, 1);
        Calendar march_28_2015 = new GregorianCalendar(2015, 2, 28);

        List<String> miscprms = new ArrayList<String>();
        miscprms.add("IDFN^^18");

        List<Object> requestParams = new ArrayList<Object>();
        requestParams.add("CE");
        requestParams.add(Integer.toString((int)FMDateUtils.dateToFMDate(march_01_2015.getTime()))); //3150201
        requestParams.add(Integer.toString((int)FMDateUtils.dateToFMDate(march_28_2015.getTime()))); //3150228
        requestParams.add(miscprms);

        return requestParams;
    }

    public List<String> getMinimumParamsToRetrieveVistaImages() {
        List<String> requestParams = new ArrayList<String>();
        requestParams.add("39");
        requestParams.add("43");

        return requestParams;
    }
}
