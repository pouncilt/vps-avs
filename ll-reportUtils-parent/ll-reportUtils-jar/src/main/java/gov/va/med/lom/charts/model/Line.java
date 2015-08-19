package gov.va.med.lom.charts.model;

public class Line {
  
  private Double value;
  private Double startValue;
  private Double endValue;
  private String displayValue;
  private Integer isTrendZone;
  private Integer showOnTop;
  private Integer thickness;
  private Integer alpha;
  private String color;
  private Integer dashed;
  private Integer dashLen;
  private Integer dashGap;
  private Integer valueOnRight;
  private String toolText;
  
  public Line() {
    // no arg
  }
  
  public Line(Double startValue, Double endValue, String displayValue, String color) {
    this.startValue = startValue;
    this.endValue = endValue;
    this.displayValue = displayValue;
    this.color = color;
  }
  
  public Double getStartValue() {
    return startValue;
  }
  
  public void setStartValue(Double startValue) {
    this.startValue = startValue;
  }
  
  public Double getEndValue() {
    return endValue;
  }
  
  public void setEndValue(Double endValue) {
    this.endValue = endValue;
  }
  
  public String getDisplayValue() {
    return displayValue;
  }
  
  public void setDisplayValue(String displayValue) {
    this.displayValue = displayValue;
  }
  
  public Integer getIsTrendZone() {
    return isTrendZone;
  }
  
  public void setIsTrendZone(Integer isTrendZone) {
    this.isTrendZone = isTrendZone;
  }
  
  public Integer getShowOnTop() {
    return showOnTop;
  }
  
  public void setShowOnTop(Integer showOnTop) {
    this.showOnTop = showOnTop;
  }
  
  public Integer getThickness() {
    return thickness;
  }
  
  public void setThickness(Integer thickness) {
    this.thickness = thickness;
  }
  
  public Integer getAlpha() {
    return alpha;
  }
  
  public void setAlpha(Integer alpha) {
    this.alpha = alpha;
  }
  
  public String getColor() {
    return color;
  }
  
  public void setColor(String color) {
    this.color = color;
  }
  
  public Integer getDashed() {
    return dashed;
  }
  
  public void setDashed(Integer dashed) {
    this.dashed = dashed;
  }
  
  public Integer getDashLen() {
    return dashLen;
  }
  
  public void setDashLen(Integer dashLen) {
    this.dashLen = dashLen;
  }
  
  public Integer getDashGap() {
    return dashGap;
  }
  
  public void setDashGap(Integer dashGap) {
    this.dashGap = dashGap;
  }
  
  public Integer getValueOnRight() {
    return valueOnRight;
  }
  
  public void setValueOnRight(Integer valueOnRight) {
    this.valueOnRight = valueOnRight;
  }
  
  public String getToolText() {
    return toolText;
  }
  
  public void setToolText(String toolText) {
    this.toolText = toolText;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }
 
}
