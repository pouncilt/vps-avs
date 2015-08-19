package gov.va.med.lom.charts.model;

public class Category {
  
  private String label;
  private Integer showLabel;
  private String toolText;
  private Integer x;
  private Integer sl;
  private Integer showVerticalLine;
  
  public Category() {
    // no arg
  }
  
  public Category(String label) {
    this.label = label;
  }
  
  public Category(String label, String toolText) {
    this(label);
    this.toolText = toolText;
    this.showLabel = 1;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Integer getShowLabel() {
    return showLabel;
  }

  public void setShowLabel(Integer showLabel) {
    this.showLabel = showLabel;
  }

  public String getToolText() {
    return toolText;
  }

  public void setToolText(String toolText) {
    this.toolText = toolText;
  }

  public Integer getX() {
    return x;
  }

  public void setX(Integer x) {
    this.x = x;
  }

  public Integer getSl() {
    return sl;
  }

  public void setSl(Integer sl) {
    this.sl = sl;
  }

  public Integer getShowVerticalLine() {
    return showVerticalLine;
  }

  public void setShowVerticalLine(Integer showVerticalLine) {
    this.showVerticalLine = showVerticalLine;
  }

}
