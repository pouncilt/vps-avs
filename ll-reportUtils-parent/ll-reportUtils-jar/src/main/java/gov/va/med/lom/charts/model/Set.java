package gov.va.med.lom.charts.model;

public class Set {
  
  private String label;
  private Double value;
  private String displayValue;
  private String color;
  private String link;
  private String toolText;
  private Integer showLabel;
  private Integer showValue;
  private Integer dashed;
  private Integer alpha;
  private String borderColor;
  private Integer isSliced;
  private String borderAlpha;
  private String valuePosition;
  private Integer anchorSides;
  private Integer anchorRadius;
  private String anchorBorderColor;
  private Integer anchorBorderThickness;
  private String anchorBgColor;
  private Integer anchorAlpha;
  private Integer anchorBgAlpha;
  
  // Bubble/Scatter properties
  private String id;
  private String name;
  private Double x;
  private Double y;
  private Double z;
  
  // vline properties (also uses label, color, alpha, dashed properties)
  private Boolean vLine;
  private Double linePosition; // 0 - 1 inclusive
  private Double labelPosition; // 0 - 1 inclusive
  private Integer showLabelBorder;
  private String labelHAlign;
  private String labelVAlign;
  private Integer thickness;
  private Integer dashLen;
  private Integer dashGap;
  
  // Value Positions
  public static final String ABOVE = "above";
  public static final String BELOW = "below";
  public static final String AUTO = "auto";
  
  // Label Alignments
 public static final String LEFT = "left";
 public static final String CENTER = "center";
 public static final String RIGHT = "right";
 public static final String TOP = "top";
 public static final String MIDDLE = "middle";
 public static final String BOTTOM = "bottom";
  
  // Constructors
  public Set() {
    // no arg
  }
  
  public Set(Double value) {
    this.value = value;
  }  
  
  public Set(String label) {
    this.label = label;
  }  
  
  public Set(String label, Double value) {
    this.label = label;
    this.value = value;
  }
  
  public Set(String label, Double value, String color) {
    this(label, value);
    this.color = color;
  }
  
  public Set(String label, Double value, String color, String displayValue, String toolText) {
    this(label, value, color);
    this.displayValue = displayValue;
    this.toolText = toolText;
  }
  
  public Set(String label, Double value, String color, String displayValue, String toolText, 
             String link, boolean showLabel, boolean showValue, boolean dashed, Integer alpha) {
    this(label, value, color, displayValue, toolText);
    this.link = link;
    this.showLabel = showLabel ? 1 : 0;
    this.showValue = showValue ? 1 : 0;
    this.dashed = dashed ? 1 : 0;
    this.alpha = alpha;
  }
  
  public Set(String borderColor, boolean isSliced, String label, Double value, String displayValue, 
             String color, String link, String toolText) {
    this(label, value, color, displayValue, toolText);
    this.borderColor = borderColor;
    this.isSliced = isSliced ? 1 : 0;
    this.link = link;
  }
  
  public Set(String borderColor, boolean isSliced, String label, Double value, String displayValue, 
             String color, String link, String toolText, boolean dashed, Integer alpha) {
    this(borderColor, isSliced, label, value, displayValue, color, link, toolText);
    this.dashed = dashed ? 1 : 0;
    this.alpha = alpha;
  }

  public Set(String label, Double value, String displayValue, String color, String link, String toolText, 
             boolean showLabel, boolean showValue, String valuePosition, boolean dashed, Integer alpha) {
    this(label, value, color, displayValue, toolText);
    this.label = label;
    this.showLabel = showLabel ? 1 : 0;
    this.showValue = showValue ? 1 : 0;
    this.dashed = dashed ? 1 : 0;
    this.link = link;
    this.valuePosition = valuePosition;
    this.alpha = alpha;
  }
      
  
  public Set(String label, Double value, String displayValue, String color, String link, String toolText, 
             boolean showLabel, boolean showValue, String valuePosition, boolean dashed, Integer alpha,
             Integer anchorSides, Integer anchorRadius, String anchorBorderColor, Integer anchorBorderThickness,
             String anchorBgColor, Integer anchorAlpha, Integer anchorBgAlpha) {
    this(label, value, color, displayValue, toolText);
    this.label = label;
    this.showLabel = showLabel ? 1 : 0;
    this.showValue = showValue ? 1 : 0;
    this.dashed = dashed ? 1 : 0;
    this.link = link;
    this.valuePosition = valuePosition;
    this.anchorSides = anchorSides;
    this.anchorRadius = anchorRadius;
    this.anchorBorderColor = anchorBorderColor;
    this.anchorBorderThickness = anchorBorderThickness;
    this.anchorBgColor = anchorBgColor;
    this.anchorAlpha = anchorAlpha;
    this.anchorBgAlpha = anchorBgAlpha;
    this.alpha = alpha;
  }
      
  public Set(String id, String name, Double x, Double y, Double z) {
    this.id = id;
    this.name = name;
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  
  public String getLabel() {
    return label;
  }
  
  public void setLabel(String label) {
    this.label = label;
  }
  
  public Double getValue() {
    return value;
  }
  
  public void setValue(Double value) {
    this.value = value;
  }
  
  public String getDisplayValue() {
    return displayValue;
  }
  
  public void setDisplayValue(String displayValue) {
    this.displayValue = displayValue;
  }
  
  public String getColor() {
    return color;
  }
  
  public void setColor(String color) {
    this.color = color;
  }
  
  public String getLink() {
    return link;
  }
  
  public void setLink(String link) {
    this.link = link;
  }
  
  public String getToolText() {
    return toolText;
  }
  
  public void setToolText(String toolText) {
    this.toolText = toolText;
  }
  
  public Integer getShowLabel() {
    return showLabel;
  }
  
  public void setShowLabel(Integer showLabel) {
    this.showLabel = showLabel;
  }
  
  public Integer getShowValue() {
    return showValue;
  }
  
  public void setShowValue(Integer showValue) {
    this.showValue = showValue;
  }
  
  public Integer getDashed() {
    return dashed;
  }
  
  public void setDashed(Integer dashed) {
    this.dashed = dashed;
  }
  
  public Integer getAlpha() {
    return alpha;
  }
  
  public void setAlpha(Integer alpha) {
    this.alpha = alpha;
  }
  
  public String getBorderColor() {
    return borderColor;
  }
  
  public void setBorderColor(String borderColor) {
    this.borderColor = borderColor;
  }
  
  public Integer getIsSliced() {
    return isSliced;
  }
  
  public void setIsSliced(Integer isSliced) {
    this.isSliced = isSliced;
  }
  
  public String getBorderAlpha() {
    return borderAlpha;
  }
  
  public void setBorderAlpha(String borderAlpha) {
    this.borderAlpha = borderAlpha;
  }
  
  public String getValuePosition() {
    return valuePosition;
  }
  
  public void setValuePosition(String valuePosition) {
    this.valuePosition = valuePosition;
  }
  
  public Integer getAnchorSides() {
    return anchorSides;
  }
  
  public void setAnchorSides(Integer anchorSides) {
    this.anchorSides = anchorSides;
  }
  
  public Integer getAnchorRadius() {
    return anchorRadius;
  }
  
  public void setAnchorRadius(Integer anchorRadius) {
    this.anchorRadius = anchorRadius;
  }
  
  public Integer getAnchorBorderThickness() {
    return anchorBorderThickness;
  }
  
  public void setAnchorBorderThickness(Integer anchorBorderThickness) {
    this.anchorBorderThickness = anchorBorderThickness;
  }
  
  public String getAnchorBgColor() {
    return anchorBgColor;
  }

  public void setAnchorBgColor(String anchorBgColor) {
    this.anchorBgColor = anchorBgColor;
  }
  
  public Integer getAnchorAlpha() {
    return anchorAlpha;
  }
  
  public void setAnchorAlpha(Integer anchorAlpha) {
    this.anchorAlpha = anchorAlpha;
  }
  
  public Integer getAnchorBgAlpha() {
    return anchorBgAlpha;
  }
  
  public void setAnchorBgAlpha(Integer anchorBgAlpha) {
    this.anchorBgAlpha = anchorBgAlpha;
  }

  public String getAnchorBorderColor() {
    return anchorBorderColor;
  }

  public void setAnchorBorderColor(String anchorBorderColor) {
    this.anchorBorderColor = anchorBorderColor;
  }

  public Boolean getVLine() {
    return vLine;
  }

  public void setVLine(Boolean line) {
    vLine = line;
  }

  public Double getLinePosition() {
    return linePosition;
  }

  public void setLinePosition(Double linePosition) {
    this.linePosition = linePosition;
  }

  public Double getLabelPosition() {
    return labelPosition;
  }

  public void setLabelPosition(Double labelPosition) {
    this.labelPosition = labelPosition;
  }

  public Integer getShowLabelBorder() {
    return showLabelBorder;
  }

  public void setShowLabelBorder(Integer showLabelBorder) {
    this.showLabelBorder = showLabelBorder;
  }

  public String getLabelHAlign() {
    return labelHAlign;
  }

  public void setLabelHAlign(String labelHAlign) {
    this.labelHAlign = labelHAlign;
  }

  public String getLabelVAlign() {
    return labelVAlign;
  }

  public void setLabelVAlign(String labelVAlign) {
    this.labelVAlign = labelVAlign;
  }

  public Integer getThickness() {
    return thickness;
  }

  public void setThickness(Integer thickness) {
    this.thickness = thickness;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getX() {
    return x;
  }

  public void setX(Double x) {
    this.x = x;
  }

  public Double getY() {
    return y;
  }

  public void setY(Double y) {
    this.y = y;
  }

  public Double getZ() {
    return z;
  }

  public void setZ(Double z) {
    this.z = z;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
 
}
