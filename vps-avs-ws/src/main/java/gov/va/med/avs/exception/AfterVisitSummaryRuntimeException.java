package gov.va.med.avs.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tpouncil on 4/22/2015.
 */
public class AfterVisitSummaryRuntimeException extends RuntimeException {
    private List<String> messages = new ArrayList<String>();

    public AfterVisitSummaryRuntimeException() {
        super();
    }

    public AfterVisitSummaryRuntimeException(String message) {
        super(message);
        messages.add(message);
    }

    public AfterVisitSummaryRuntimeException(List<String> messages) {
        super((messages != null && messages.size() > 0 && messages.get(0) != null)? messages.get(0):  null);
        this.messages = messages;
    }

    public AfterVisitSummaryRuntimeException(List<String> messages, Throwable cause) {
        super((messages != null && messages.size() > 0 && messages.get(0) != null)? messages.get(0):  null, cause);
        this.messages = messages;
    }

    public AfterVisitSummaryRuntimeException(String message, Throwable cause) {
        super(message, cause);
        messages.add(message);
    }

    public AfterVisitSummaryRuntimeException(Throwable cause) {
        super(cause);
    }

    protected AfterVisitSummaryRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        messages.add(message);
    }

    public AfterVisitSummaryRuntimeException(List<String> messages, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super((messages != null && messages.size() > 0 && messages.get(0) != null)? messages.get(0):  null, cause, enableSuppression, writableStackTrace);
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
}
