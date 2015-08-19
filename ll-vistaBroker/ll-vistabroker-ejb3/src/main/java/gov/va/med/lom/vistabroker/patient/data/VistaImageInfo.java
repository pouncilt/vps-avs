package gov.va.med.lom.vistabroker.patient.data;

import java.io.Serializable;
import java.util.Date;

public class VistaImageInfo implements Serializable {
    private Integer sequenceNumber;
    private String siteCode;
    private String reportTitle;
    private Date procedureDate;
    private String procedureName;
    private Integer numberOfImagesInGroup;
    private String shortDescription;
    private String packageName1; // RAD, LAB, MED, SUR, NONE, PHOTOID
    private String className;
    private String type;
    private String specialty;
    private String procedureEvent;
    private String origin;
    private Date capturedDate;
    private String capturedBy;
    private String imageIen;
    private String pipedImageIen;
    private String location;
    private String filename;
    private String abstractLocation;
    private String offLineJukeBoxShortDescription;
    private Date examDateTimeStamp;
    private String objectType;
    private String procedureField;
    private Date displayDate;
    private String parentDataFileImagePointer;
    private String absType; //M - magnetic, W -worm, O - offline
    private String accessibility; // A - accessible, O - offline
    private String dicomSeriesNumber;
    private String dicomImageNumber;
    private Integer countOfImagesInGroupVISN15;
    private String siteParameterIEN;
    private String siteParameterCode;
    private String integrityCheckErrorDescription;
    private String imageBigPathName;
    private String patientDFN;
    private String patientName;
    private String imageClass;
    private Date imageSavedDateTimeStamp;
    private Date documentDate;
    private String groupIEN;
    private String firstChildOfGroupIEN;
    private String firstChildOfGroupType;
    private String rpcBrokerServer;
    private String rpcBrokerPort;
    private Integer internalValueOfControlledImageField;
    private String viewableStatus;
    private String internalValueOfStatusField;
    private Boolean imageAnnotated;
    private Boolean imageTIUNoteCompleted;
    private String annotatedOperationStatus;
    private String annotatedOperationStatusDescription;
    private String packageName2; // RAD, LAB, MED, SUR, NONE, PHOTOID

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public Date getProcedureDate() {
        return procedureDate;
    }

    public void setProcedureDate(Date procedureDate) {
        this.procedureDate = procedureDate;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public Integer getNumberOfImagesInGroup() {
        return numberOfImagesInGroup;
    }

    public void setNumberOfImagesInGroup(Integer numberOfImagesInGroup) {
        this.numberOfImagesInGroup = numberOfImagesInGroup;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getPackageName1() {
        return packageName1;
    }

    public void setPackageName1(String packageName1) {
        this.packageName1 = packageName1;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getProcedureEvent() {
        return procedureEvent;
    }

    public void setProcedureEvent(String procedureEvent) {
        this.procedureEvent = procedureEvent;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Date getCapturedDate() {
        return capturedDate;
    }

    public void setCapturedDate(Date capturedDate) {
        this.capturedDate = capturedDate;
    }

    public String getCapturedBy() {
        return capturedBy;
    }

    public void setCapturedBy(String capturedBy) {
        this.capturedBy = capturedBy;
    }

    public String getImageIen() {
        return imageIen;
    }

    public void setImageIen(String imageIen) {
        this.imageIen = imageIen;
    }

    public String getPipedImageIen() {
        return pipedImageIen;
    }

    public void setPipedImageIen(String pipeImageIen) {
        this.pipedImageIen = pipedImageIen;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getAbstractLocation() {
        return abstractLocation;
    }

    public void setAbstractLocation(String abstractLocation) {
        this.abstractLocation = abstractLocation;
    }

    public String getOffLineJukeBoxShortDescription() {
        return offLineJukeBoxShortDescription;
    }

    public void setOffLineJukeBoxShortDescription(String offLineJukeBoxShortDescription) {
        this.offLineJukeBoxShortDescription = offLineJukeBoxShortDescription;
    }

    public Date getExamDateTimeStamp() {
        return examDateTimeStamp;
    }

    public void setExamDateTimeStamp(Date examDateTimeStamp) {
        this.examDateTimeStamp = examDateTimeStamp;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getProcedureField() {
        return procedureField;
    }

    public void setProcedureField(String procedureField) {
        this.procedureField = procedureField;
    }

    public Date getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(Date displayDate) {
        this.displayDate = displayDate;
    }

    public String getParentDataFileImagePointer() {
        return parentDataFileImagePointer;
    }

    public void setParentDataFileImagePointer(String parentDataFileImagePointer) {
        this.parentDataFileImagePointer = parentDataFileImagePointer;
    }

    public String getAbsType() {
        return absType;
    }

    public void setAbsType(String absType) {
        this.absType = absType;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public String getDicomSeriesNumber() {
        return dicomSeriesNumber;
    }

    public void setDicomSeriesNumber(String dicomSeriesNumber) {
        this.dicomSeriesNumber = dicomSeriesNumber;
    }

    public String getDicomImageNumber() {
        return dicomImageNumber;
    }

    public void setDicomImageNumber(String dicomImageNumber) {
        this.dicomImageNumber = dicomImageNumber;
    }

    public Integer getCountOfImagesInGroupVISN15() {
        return countOfImagesInGroupVISN15;
    }

    public void setCountOfImagesInGroupVISN15(Integer countOfImagesInGroupVISN15) {
        this.countOfImagesInGroupVISN15 = countOfImagesInGroupVISN15;
    }

    public String getSiteParameterIEN() {
        return siteParameterIEN;
    }

    public void setSiteParameterIEN(String siteParameterIEN) {
        this.siteParameterIEN = siteParameterIEN;
    }

    public String getSiteParameterCode() {
        return siteParameterCode;
    }

    public void setSiteParameterCode(String siteParameterCode) {
        this.siteParameterCode = siteParameterCode;
    }

    public String getIntegrityCheckErrorDescription() {
        return integrityCheckErrorDescription;
    }

    public void setIntegrityCheckErrorDescription(String integrityCheckErrorDescription) {
        this.integrityCheckErrorDescription = integrityCheckErrorDescription;
    }

    public String getImageBigPathName() {
        return imageBigPathName;
    }

    public void setImageBigPathName(String imageBigPathName) {
        this.imageBigPathName = imageBigPathName;
    }

    public String getPatientDFN() {
        return patientDFN;
    }

    public void setPatientDFN(String patientDFN) {
        this.patientDFN = patientDFN;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getImageClass() {
        return imageClass;
    }

    public void setImageClass(String imageClass) {
        this.imageClass = imageClass;
    }

    public Date getImageSavedDateTimeStamp() {
        return imageSavedDateTimeStamp;
    }

    public void setImageSavedDateTimeStamp(Date imageSavedDateTimeStamp) {
        this.imageSavedDateTimeStamp = imageSavedDateTimeStamp;
    }

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }

    public String getGroupIEN() {
        return groupIEN;
    }

    public void setGroupIEN(String groupIEN) {
        this.groupIEN = groupIEN;
    }

    public String getFirstChildOfGroupIEN() {
        return firstChildOfGroupIEN;
    }

    public void setFirstChildOfGroupIEN(String firstChildOfGroupIEN) {
        this.firstChildOfGroupIEN = firstChildOfGroupIEN;
    }

    public String getFirstChildOfGroupType() {
        return firstChildOfGroupType;
    }

    public void setFirstChildOfGroupType(String firstChildOfGroupType) {
        this.firstChildOfGroupType = firstChildOfGroupType;
    }

    public String getRpcBrokerServer() {
        return rpcBrokerServer;
    }

    public void setRpcBrokerServer(String rpcBrokerServer) {
        this.rpcBrokerServer = rpcBrokerServer;
    }

    public String getRpcBrokerPort() {
        return rpcBrokerPort;
    }

    public void setRpcBrokerPort(String rpcBrokerPort) {
        this.rpcBrokerPort = rpcBrokerPort;
    }

    public Integer getInternalValueOfControlledImageField() {
        return internalValueOfControlledImageField;
    }

    public void setInternalValueOfControlledImageField(Integer internalValueOfControlledImageField) {
        this.internalValueOfControlledImageField = internalValueOfControlledImageField;
    }

    public String getViewableStatus() {
        return viewableStatus;
    }

    public void setViewableStatus(String viewableStatus) {
        this.viewableStatus = viewableStatus;
    }

    public String getInternalValueOfStatusField() {
        return internalValueOfStatusField;
    }

    public void setInternalValueOfStatusField(String internalValueOfStatusField) {
        this.internalValueOfStatusField = internalValueOfStatusField;
    }

    public Boolean getImageAnnotated() {
        return imageAnnotated;
    }

    public void setImageAnnotated(Boolean imageAnnotated) {
        this.imageAnnotated = imageAnnotated;
    }

    public Boolean getImageTIUNoteCompleted() {
        return imageTIUNoteCompleted;
    }

    public void setImageTIUNoteCompleted(Boolean imageTIUNoteCompleted) {
        this.imageTIUNoteCompleted = imageTIUNoteCompleted;
    }

    public String getAnnotatedOperationStatus() {
        return annotatedOperationStatus;
    }

    public void setAnnotatedOperationStatus(String annotatedOperationStatus) {
        this.annotatedOperationStatus = annotatedOperationStatus;
    }

    public String getAnnotatedOperationStatusDescription() {
        return annotatedOperationStatusDescription;
    }

    public void setAnnotatedOperationStatusDescription(String annotatedOperationStatusDescription) {
        this.annotatedOperationStatusDescription = annotatedOperationStatusDescription;
    }

    public String getPackageName2() {
        return packageName2;
    }

    public void setPackageName2(String packageName2) {
        this.packageName2 = packageName2;
    }

    @Override
    public String toString() {
        return "VistaImageInfo{" +
                "sequenceNumber=" + sequenceNumber +
                ", siteCode='" + siteCode + '\'' +
                ", reportTitle='" + reportTitle + '\'' +
                ", procedureDate=" + procedureDate +
                ", procedureName='" + procedureName + '\'' +
                ", numberOfImagesInGroup=" + numberOfImagesInGroup +
                ", shortDescription='" + shortDescription + '\'' +
                ", packageName1='" + packageName1 + '\'' +
                ", className='" + className + '\'' +
                ", type='" + type + '\'' +
                ", specialty='" + specialty + '\'' +
                ", procedureEvent='" + procedureEvent + '\'' +
                ", origin='" + origin + '\'' +
                ", capturedDate=" + capturedDate +
                ", capturedBy='" + capturedBy + '\'' +
                ", imageIen='" + imageIen + '\'' +
                ", pipedImageIen='" + pipedImageIen + '\'' +
                ", location='" + location + '\'' +
                ", filename='" + filename + '\'' +
                ", abstractLocation='" + abstractLocation + '\'' +
                ", offLineJukeBoxShortDescription='" + offLineJukeBoxShortDescription + '\'' +
                ", examDateTimeStamp=" + examDateTimeStamp +
                ", objectType='" + objectType + '\'' +
                ", procedureField='" + procedureField + '\'' +
                ", displayDate=" + displayDate +
                ", parentDataFileImagePointer='" + parentDataFileImagePointer + '\'' +
                ", absType='" + absType + '\'' +
                ", accessibility='" + accessibility + '\'' +
                ", dicomSeriesNumber='" + dicomSeriesNumber + '\'' +
                ", dicomImageNumber='" + dicomImageNumber + '\'' +
                ", countOfImagesInGroupVISN15=" + countOfImagesInGroupVISN15 +
                ", siteParameterIEN='" + siteParameterIEN + '\'' +
                ", siteParameterCode='" + siteParameterCode + '\'' +
                ", integrityCheckErrorDescription='" + integrityCheckErrorDescription + '\'' +
                ", imageBigPathName='" + imageBigPathName + '\'' +
                ", patientDFN='" + patientDFN + '\'' +
                ", patientName='" + patientName + '\'' +
                ", imageClass='" + imageClass + '\'' +
                ", imageSavedDateTimeStamp=" + imageSavedDateTimeStamp +
                ", documentDate=" + documentDate +
                ", groupIEN='" + groupIEN + '\'' +
                ", firstChildOfGroupIEN='" + firstChildOfGroupIEN + '\'' +
                ", firstChildOfGroupType='" + firstChildOfGroupType + '\'' +
                ", rpcBrokerServer='" + rpcBrokerServer + '\'' +
                ", rpcBrokerPort='" + rpcBrokerPort + '\'' +
                ", internalValueOfControlledImageField=" + internalValueOfControlledImageField +
                ", viewableStatus='" + viewableStatus + '\'' +
                ", internalValueOfStatusField='" + internalValueOfStatusField + '\'' +
                ", imageAnnotated=" + imageAnnotated +
                ", imageTIUNoteCompleted=" + imageTIUNoteCompleted +
                ", annotatedOperationStatus='" + annotatedOperationStatus + '\'' +
                ", annotatedOperationStatusDescription='" + annotatedOperationStatusDescription + '\'' +
                ", packageName2='" + packageName2 + '\'' +
                '}';
    }
}
