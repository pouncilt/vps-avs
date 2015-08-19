package gov.va.med.avs.service;

import gov.va.med.avs.exception.ConfigurationFileNotFoundException;
import gov.va.med.avs.model.AfterVisitSummary;
import gov.va.med.avs.model.AfterVisitSummarySearchRequest;

import java.util.List;

/**
 * Created by Tonté Pouncil on 2/12/15.
 */
public interface AfterVisitSummaryService {
    List<AfterVisitSummary> findAfterVisitSummaries(AfterVisitSummarySearchRequest afterVisitSummarySearchRequest);
    void getAfterVisitSummary(String clientId, String veteranId, AfterVisitSummary afterVisitSummary) throws ConfigurationFileNotFoundException;
}
