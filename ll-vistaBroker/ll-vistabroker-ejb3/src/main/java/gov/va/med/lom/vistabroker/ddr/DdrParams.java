package gov.va.med.lom.vistabroker.ddr;

import java.io.Serializable;

public class DdrParams implements Serializable {

  private String file;
  private String iens;
  private String fields;
  private String flags;
  private int max;
  private String from;
  private String part;
  private String xref;
  private String screen;
  private String id;
  private String options;
  private String moreFrom;
  private String moreIens;
  private boolean useScLister;
  private String field;
  private String value;
  
  public String getFile() {
    return file;
  }
  public void setFile(String file) {
    this.file = file;
  }
  public String getIens() {
    return iens;
  }
  public void setIens(String iens) {
    this.iens = iens;
  }
  public String getFields() {
    return fields;
  }
  public void setFields(String fields) {
    this.fields = fields;
  }
  public String getFlags() {
    return flags;
  }
  public void setFlags(String flags) {
    this.flags = flags;
  }
  public int getMax() {
    return max;
  }
  public void setMax(int max) {
    this.max = max;
  }
  public String getFrom() {
    return from;
  }
  public void setFrom(String from) {
    this.from = from;
  }
  public String getPart() {
    return part;
  }
  public void setPart(String part) {
    this.part = part;
  }
  public String getXref() {
    return xref;
  }
  public void setXref(String xref) {
    this.xref = xref;
  }
  public String getScreen() {
    return screen;
  }
  public void setScreen(String screen) {
    this.screen = screen;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getOptions() {
    return options;
  }
  public void setOptions(String options) {
    this.options = options;
  }
  public String getMoreFrom() {
    return moreFrom;
  }
  public void setMoreFrom(String moreFrom) {
    this.moreFrom = moreFrom;
  }
  public String getMoreIens() {
    return moreIens;
  }
  public void setMoreIens(String moreIens) {
    this.moreIens = moreIens;
  }
  public boolean isUseScLister() {
    return useScLister;
  }
  public void setUseScLister(boolean useScLister) {
    this.useScLister = useScLister;
  }
  public String getField() {
    return field;
  }
  public void setField(String field) {
    this.field = field;
  }
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }
  
}
