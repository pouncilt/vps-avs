package gov.va.med.avs.exception;

/**
 * Created by tpouncil on 3/24/2015.
 */
public class ConfigurationFileNotFoundException extends AfterVisitSummaryRuntimeException {
    public ConfigurationFileNotFoundException(String message) {
        super(message);
    }
}
