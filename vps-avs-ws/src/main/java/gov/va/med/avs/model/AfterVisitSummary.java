package gov.va.med.avs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gov.va.med.avs.exception.RequiredElementNotFoundException;
import gov.va.med.avs.serializer.JsonDateSerializer;

import java.beans.Transient;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Tonté Pouncil on 2/4/15.
 */
public class AfterVisitSummary{
    private String id;
    private String veteranId;
    private String fileName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    private String createdBy;
    private Date createdDate;
    private byte[] binaryPDF;
    @JsonIgnore
    private boolean doComprehensiveValidation = true;

    public AfterVisitSummary() {
        doComprehensiveValidation = false;
    }

    public AfterVisitSummary(String id, String veteranId, String fileName, String createdBy, Date createdDate) {
        this.id = id;
        this.veteranId = veteranId;
        this.fileName = fileName;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        doComprehensiveValidation = false;
        validate();
    }

    public AfterVisitSummary(String id, String veteranId, String fileName, String description, String createdBy, Date createdDate) {
        this.id = id;
        this.veteranId = veteranId;
        this.fileName = fileName;
        this.description = description;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        validate();
    }

    public AfterVisitSummary(String id, String veteranId, String fileName, String description, String createdBy, Date createdDate, byte[] binaryPDF) {
        this.id = id;
        this.veteranId = veteranId;
        this.fileName = fileName;
        this.description = description;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.binaryPDF = binaryPDF;
        validate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVeteranId() {
        return veteranId;
    }

    public void setVeteranId(String veteranId) {
        this.veteranId = veteranId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public byte[] getBinaryPDF() {
        return binaryPDF;
    }

    public void setBinaryPDF(byte[] binaryPDF) {
        this.binaryPDF = binaryPDF;
    }

    @Transient
    @Override
    public String toString() {
        return "AfterVisitSummary{" +
                "id='" + id + '\'' +
                ", veteranId='" + veteranId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", description='" + description + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", binaryPDF=" + Arrays.toString(binaryPDF) +
                '}';
    }

    public void validate() {
        if(veteranId == null) throw new RequiredElementNotFoundException("veteranId is required; but not found.");
        if(fileName == null) throw new RequiredElementNotFoundException("fileName is required; but not found.");
        if(doComprehensiveValidation && description == null) throw new RequiredElementNotFoundException("description is required; but not found.");
        if(createdBy == null) throw new RequiredElementNotFoundException("createdBy is required; but not found.");
        if(createdDate == null) throw new RequiredElementNotFoundException("createdDate is required; but not found.");
    }
}
