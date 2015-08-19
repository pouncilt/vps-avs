package gov.va.med.avs.exception;

import java.util.List;

/**
 * Created by Tonté Pouncil on 2/18/15.
 */
public class InvalidDateFormatException extends AfterVisitSummaryRuntimeException {
    public InvalidDateFormatException() {
        super();
    }

    public InvalidDateFormatException(String message) {
        super(message);
    }

    public InvalidDateFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDateFormatException(List<String> messages, Throwable cause) {
        super(messages, cause);
    }

    public InvalidDateFormatException(List<String> messages) {
        super(messages);
    }

    public InvalidDateFormatException(Throwable cause) {
        super(cause);
    }
}
