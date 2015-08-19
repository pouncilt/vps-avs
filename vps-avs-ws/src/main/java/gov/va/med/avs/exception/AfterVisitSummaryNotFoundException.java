package gov.va.med.avs.exception;

import java.util.List;

/**
 * Created by Tonté Pouncil on 2/13/15.
 */
public class AfterVisitSummaryNotFoundException extends AfterVisitSummaryRuntimeException {
    public AfterVisitSummaryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AfterVisitSummaryNotFoundException(List<String> messages, Throwable cause) {
        super(messages, cause);
    }

    public AfterVisitSummaryNotFoundException(Throwable cause) {
        super(cause);
    }

    public AfterVisitSummaryNotFoundException(String message) {
        super(message);
    }

    public AfterVisitSummaryNotFoundException(List<String> messages) {
        super(messages);
    }

    public AfterVisitSummaryNotFoundException() {
        super();
    }
}
