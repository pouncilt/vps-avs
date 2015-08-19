package gov.va.med.avs.exception;

import java.util.List;

/**
 * Created by tpouncil on 4/15/2015.
 */
public class RequiredElementNotFoundException extends AfterVisitSummaryRuntimeException {
    public RequiredElementNotFoundException() {
        super();
    }

    public RequiredElementNotFoundException(String message) {
        super(message);
    }

    public RequiredElementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequiredElementNotFoundException(List<String> messages) {
        super(messages);
    }

    public RequiredElementNotFoundException(List<String> messages, Throwable cause) {
        super(messages, cause);
    }

    public RequiredElementNotFoundException(Throwable cause) {
        super(cause);
    }

    protected RequiredElementNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    protected RequiredElementNotFoundException(List<String> messages, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(messages, cause, enableSuppression, writableStackTrace);
    }
}
