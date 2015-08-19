package gov.va.med.avs.exception;

import java.util.List;

/**
 * Created by Tonté Pouncil on 2/13/15.
 */
public class VeteranNotFoundException extends AfterVisitSummaryRuntimeException{

    public VeteranNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VeteranNotFoundException(List<String> messages, Throwable cause) {
        super(messages, cause);
    }

    public VeteranNotFoundException(Throwable cause) {
        super(cause);
    }

    public VeteranNotFoundException(String message) {
        super(message);
    }

    public VeteranNotFoundException(List<String> messages) {
        super(messages);
    }

    public VeteranNotFoundException() {
        super();
    }
}
