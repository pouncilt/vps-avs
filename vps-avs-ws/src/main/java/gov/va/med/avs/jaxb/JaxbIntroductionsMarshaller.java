package gov.va.med.avs.jaxb;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * Created by Tonté Pouncil on 2/11/15.
 */
public class JaxbIntroductionsMarshaller extends Jaxb2Marshaller {
    @Override
    public boolean supports(Class<?> clazz) {
        boolean supportsClass = super.supports(clazz);

        if (supportsClass == false) {
            supportsClass = true;
        }

        return supportsClass;
    }
}
