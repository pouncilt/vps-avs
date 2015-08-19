package gov.va.med.avs.exception;

import java.util.List;

/**
 * Created by Tonté Pouncil on 2/18/15.
 */
public class AfterVisitSummariesNotFoundException extends AfterVisitSummaryRuntimeException{
    public AfterVisitSummariesNotFoundException() {
        super();
    }

    public AfterVisitSummariesNotFoundException(String message) {
        super(message);
    }

    public AfterVisitSummariesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AfterVisitSummariesNotFoundException(List<String> messages) {
        super(messages);
    }

    public AfterVisitSummariesNotFoundException(List<String> messages, Throwable cause) {
        super(messages, cause);
    }

    public AfterVisitSummariesNotFoundException(Throwable cause) {
        super(cause);
    }
}
