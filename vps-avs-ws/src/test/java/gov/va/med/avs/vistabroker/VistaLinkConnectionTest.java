package gov.va.med.avs.vistabroker;

import gov.va.med.lom.vistabroker.security.ISecurityContext;
import gov.va.med.lom.vistabroker.security.SecurityContextFactory;
import gov.va.med.lom.vistabroker.service.MiscVBService;
import gov.va.med.lom.vistabroker.util.VistaBrokerServiceFactory;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by tpouncil on 3/6/2015.
 */
public class VistaLinkConnectionTest {
    @Test
    public void testVistaLinkConnectionTest(){
        MiscVBService miscRpcs = VistaBrokerServiceFactory.getMiscVBService();

        String division = "050";
        String duz = "32";

        try {
            // Set security context
            ISecurityContext securityContext = SecurityContextFactory.createDuzSecurityContext(division, duz);

            // Get and print date/time from VistA
            System.out.println("---------- DATE/TIME ---------");

            int today = miscRpcs.fmToday(securityContext).getPayload();
            assertNotNull("today", today);
            double now = miscRpcs.fmNow(securityContext).getPayload();
            assertNotNull("now", now);

            System.out.println("TODAY (FM date format): " + today);
            System.out.println("NOW (FM date/time format): " + now);
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
