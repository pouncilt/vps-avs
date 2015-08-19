package gov.va.med.avs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.va.med.avs.exception.InvalidDateRangeException;
import gov.va.med.avs.exception.RequiredElementNotFoundException;
import gov.va.med.avs.serializer.JsonDateDeserializer;

import java.util.Date;

/**
 * Created by Tonté Pouncil on 2/4/15.
 */
public class AfterVisitSummarySearchCriteria {
    private String clientId = null;
    private String veteranId = null;
    private Date startDate = null;
    private Date endDate = null;

    public AfterVisitSummarySearchCriteria() {}

    public AfterVisitSummarySearchCriteria(String clientId, String veteranId, Date startDate, Date endDate) {
        this.clientId = clientId;
        this.veteranId = veteranId;
        this.startDate = startDate;
        this.endDate = endDate;
        validate();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getVeteranId() {
        return veteranId;
    }

    public void setVeteranId(String veteranId) {
        this.veteranId = veteranId;
    }

    @JsonDeserialize(using=JsonDateDeserializer.class)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonDeserialize(using=JsonDateDeserializer.class)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void validate() {
        if(clientId == null) throw new RequiredElementNotFoundException("clientId is required; but not found.");
        if(veteranId == null) throw new RequiredElementNotFoundException("veteranId is required; but not found.");
        if(startDate == null) throw new RequiredElementNotFoundException("startDate is required; but not found.");
        if(endDate == null) throw new RequiredElementNotFoundException("endDate is required; but not found.");

        if(startDate.after(endDate)) {
            throw new InvalidDateRangeException("Invalid date range.  Start date can not come after end date.");
        }

        if(endDate.before(startDate)) {
            throw new InvalidDateRangeException("Invalid date range.  End date can not come before start date.");
        }
    }
}
