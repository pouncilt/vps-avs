package gov.va.med.lom.javaBroker.rpc.admin;

import java.io.Serializable;

public class SurgeryRequestItem implements Serializable {

  private String operationDate;
  private String ien;
  private String patientDfn;
  private String patientName;
  private String patientLast4;
  private String wardLocation;
  private String specialty;
  private String principleProcedure;
  private String plannedProcedureCode;
  private String otherProcedure;
  private String estCaseLength;
  private String cancellationDate;
  
  public String getOperationDate() {
    return operationDate;
  }
  public void setOperationDate(String operationDate) {
    this.operationDate = operationDate;
  }
  public String getIen() {
    return ien;
  }
  public void setIen(String ien) {
    this.ien = ien;
  }
  public String getPatientDfn() {
    return patientDfn;
  }
  public void setPatientDfn(String patientDfn) {
    this.patientDfn = patientDfn;
  }
  public String getPatientName() {
    return patientName;
  }
  public void setPatientName(String patientName) {
    this.patientName = patientName;
  }
  public String getPatientLast4() {
    return patientLast4;
  }
  public void setPatientLast4(String patientLast4) {
    this.patientLast4 = patientLast4;
  }
  public String getWardLocation() {
    return wardLocation;
  }
  public void setWardLocation(String wardLocation) {
    this.wardLocation = wardLocation;
  }
  public String getSpecialty() {
    return specialty;
  }
  public void setSpecialty(String specialty) {
    this.specialty = specialty;
  }
  public String getPrincipleProcedure() {
    return principleProcedure;
  }
  public void setPrincipleProcedure(String principleProcedure) {
    this.principleProcedure = principleProcedure;
  }
  public String getPlannedProcedureCode() {
    return plannedProcedureCode;
  }
  public void setPlannedProcedureCode(String plannedProcedureCode) {
    this.plannedProcedureCode = plannedProcedureCode;
  }
  public String getOtherProcedure() {
    return otherProcedure;
  }
  public void setOtherProcedure(String otherProcedure) {
    this.otherProcedure = otherProcedure;
  }
  public String getEstCaseLength() {
    return estCaseLength;
  }
  public void setEstCaseLength(String estCaseLength) {
    this.estCaseLength = estCaseLength;
  }
  public String getCancellationDate() {
    return cancellationDate;
  }
  public void setCancellationDate(String cancellationDate) {
    this.cancellationDate = cancellationDate;
  }
  
}
