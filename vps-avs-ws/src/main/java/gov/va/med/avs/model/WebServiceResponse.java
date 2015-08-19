package gov.va.med.avs.model;

import java.io.Serializable;

/**
 * Created by Tonté Pouncil on 2/4/15.
 */
public class WebServiceResponse implements Serializable {
    private WebServiceResponseStatus status;
    private WebServicePayload payload;

    public WebServiceResponse() {}

    public WebServiceResponse(WebServiceResponseStatus status, WebServicePayload payload) {
        this.status = status;
        this.payload = payload;
    }

    public WebServiceResponse(WebServiceResponseStatus status) {
        this.status = status;
    }

    public WebServiceResponseStatus getStatus() {
        return status;
    }

    public void setStatus(WebServiceResponseStatus status) {
        this.status = status;
    }

    public WebServicePayload getPayload() {
        return payload;
    }

    public void setPayload(WebServicePayload payload) {
        this.payload = payload;
    }
}
