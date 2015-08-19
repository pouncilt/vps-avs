package gov.va.med.avs.exception;

import java.util.List;

/**
 * Created by Tonté Pouncil on 2/18/15.
 */
public class InvalidDateRangeException extends AfterVisitSummaryRuntimeException {
    public InvalidDateRangeException() {
        super();
    }

    public InvalidDateRangeException(String message) {
        super(message);
    }

    public InvalidDateRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDateRangeException(List<String> messages) {
        super(messages);
    }

    public InvalidDateRangeException(List<String> messages, Throwable cause) {
        super(messages, cause);
    }

    public InvalidDateRangeException(Throwable cause) {
        super(cause);
    }
}
