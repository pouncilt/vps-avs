package gov.va.med.avs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tpouncil on 3/24/2015.
 */
public class WebServiceCollectionPayload {
    private List<AfterVisitSummary> afterVisitSummaries = new ArrayList<AfterVisitSummary>();

    public WebServiceCollectionPayload() {}

    public WebServiceCollectionPayload(List<AfterVisitSummary> afterVisitSummaries) {
        this.afterVisitSummaries = afterVisitSummaries;
    }

    public List<AfterVisitSummary> getAfterVisitSummaries() {
        return afterVisitSummaries;
    }

    public void setAfterVisitSummaries(List<AfterVisitSummary> afterVisitSummaries) {
        this.afterVisitSummaries = afterVisitSummaries;
    }
}
