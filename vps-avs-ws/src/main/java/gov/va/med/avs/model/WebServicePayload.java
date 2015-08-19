package gov.va.med.avs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tonté Pouncil on 2/6/15.
 */
public class WebServicePayload {
    private AfterVisitSummary afterVisitSummary = null;

    public WebServicePayload() {}

    public WebServicePayload(AfterVisitSummary afterVisitSummary) {
        this.afterVisitSummary = afterVisitSummary;
    }

    public AfterVisitSummary getAfterVisitSummary() {
        return afterVisitSummary;
    }

    public void setAfterVisitSummary(AfterVisitSummary afterVisitSummary) {
        this.afterVisitSummary = afterVisitSummary;
    }
}
