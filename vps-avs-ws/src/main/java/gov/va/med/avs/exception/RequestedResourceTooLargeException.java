package gov.va.med.avs.exception;

import java.util.List;

/**
 * Created by Tonté Pouncil on 2/18/15.
 */
public class RequestedResourceTooLargeException extends AfterVisitSummaryRuntimeException {
    public RequestedResourceTooLargeException() {
        super();
    }

    public RequestedResourceTooLargeException(String message) {
        super(message);
    }

    public RequestedResourceTooLargeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestedResourceTooLargeException(List<String> messages) {
        super(messages);
    }

    public RequestedResourceTooLargeException(List<String> messages, Throwable cause) {
        super(messages, cause);
    }

    public RequestedResourceTooLargeException(Throwable cause) {
        super(cause);
    }
}
