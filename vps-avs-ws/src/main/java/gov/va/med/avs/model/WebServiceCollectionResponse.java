package gov.va.med.avs.model;

/**
 * Created by tpouncil on 3/24/2015.
 */
public class WebServiceCollectionResponse {
    private WebServiceResponseStatus status;
    private WebServiceCollectionPayload payload;

    public WebServiceCollectionResponse() {}

    public WebServiceCollectionResponse(WebServiceResponseStatus status, WebServiceCollectionPayload payload) {
        this.status = status;
        this.payload = payload;
    }

    public WebServiceCollectionResponse(WebServiceResponseStatus status) {
        this.status = status;
    }

    public WebServiceResponseStatus getStatus() {
        return status;
    }

    public void setStatus(WebServiceResponseStatus status) {
        this.status = status;
    }

    public WebServiceCollectionPayload getPayload() {
        return payload;
    }

    public void setPayload(WebServiceCollectionPayload payload) {
        this.payload = payload;
    }
}
