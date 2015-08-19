package gov.va.med.avs.model;

import gov.va.med.avs.exception.RequiredElementNotFoundException;

/**
 * Created by Tonté Pouncil on 2/5/15.
 */

public class AfterVisitSummarySearchRequest {

    private AfterVisitSummarySearchCriteria afterVisitSummarySearchCriteria;

    public AfterVisitSummarySearchRequest() {}

    public AfterVisitSummarySearchRequest(AfterVisitSummarySearchCriteria afterVisitSummarySearchCriteria) {
        this.afterVisitSummarySearchCriteria = afterVisitSummarySearchCriteria;
        validate();
    }

    public AfterVisitSummarySearchCriteria getAfterVisitSummarySearchCriteria() {
        return afterVisitSummarySearchCriteria;
    }

    public void setAfterVisitSummarySearchCriteria(AfterVisitSummarySearchCriteria afterVisitSummarySearchCriteria) {
        this.afterVisitSummarySearchCriteria = afterVisitSummarySearchCriteria;
    }

    public void validate() {
        if(afterVisitSummarySearchCriteria == null) {
            throw new RequiredElementNotFoundException("afterVisitSummarySearchCriteria is required; but not found.");
        }

        afterVisitSummarySearchCriteria.validate();
    }
}
