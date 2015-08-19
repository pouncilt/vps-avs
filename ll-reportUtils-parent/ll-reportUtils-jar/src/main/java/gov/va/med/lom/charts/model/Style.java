package gov.va.med.lom.charts.model;

public class Style {
  
  private String type;
  private String name;
  // font
  private String font;
  private Integer size;
  private String color;
  private String align;
  private Integer bold;
  private Integer italic;
  private Integer underline;
  private String bgColor;
  private String borderColor;
  private Integer isHTML;
  private Integer leftMargin;
  private Integer letterSpacing;
  // animation
  private String param;
  private Integer start;
  private Integer duration;
  private String easing;
  // shadow, glow, and blur
  private Integer distance;
  private Integer angle;
  private Integer alpha;
  private Integer blurX;
  private Integer blurY;
  private Integer strength;
  private Integer quality;
  // bevel
  private String shadowColor;
  private Integer shadowAlpha;
  private String highlightColor;
  private Integer highlighAlpha;
  
  // Style Types
  public static final String FONT = "font";
  public static final String ANIMATION = "animation";
  public static final String SHADOW = "shadow";
  public static final String GLOW = "glow";
  public static final String BEVEL = "bevel";
  public static final String BLUR = "blur";
  
  // Animation Objects
  public static final String X = "_x";
  public static final String Y = "_y";
  public static final String X_SCALE = "_xScale";
  public static final String Y_SCALE = "_yScale";
  public static final String ALPHA = "_alpha";
  public static final String ROTATION = "_rotation";
  
  // Animation Easing Values
  public static final String ELASTIC = "elastic";
  public static final String BOUNCE = "bounce";
  public static final String REGULAR = "regular";
  public static final String STRONG = "strong";
  public static final String NONE = "none";
  
  // Font Alignments
  public static final String LEFT = "left";
  public static final String RIGHT = "right";
  public static final String CENTER = "center";
  
  // Labels
  public static final String STAGGER = "stagger";
  
  // No-arg constructor
  public Style() {
    //
  }
  
  // Font Style Constructor
  public Style(String name, String font, Integer size, String color, String align, boolean bold,
               boolean italic, boolean underline, String bgColor, String borderColor, boolean isHTML,
               Integer leftMargin, Integer letterSpacing) {
    this.name = name;
    this.font = font;
    this.size = size;
    this.color = color;
    this.align = align;
    this.bold = bold ? 1 : 0;
    this.italic = italic ? 1 : 0; 
    this.underline = underline ? 1 : 0; 
    this.bgColor = bgColor;
    this.borderColor = borderColor;
    this.isHTML = isHTML ? 1 : 0; 
    this.leftMargin = leftMargin;
    this.letterSpacing = letterSpacing;
    this.type = FONT;
  }
  
  // Animation Style Constructor
  public Style(String name, String param, Integer start, Integer duration, String easing) {
    this.name = name;
    this.param = param;
    this.start = start;
    this.duration = duration;
    this.easing = easing;
    this.type = ANIMATION;
  }
  
  // Shadow Style Constructor
  public Style(String name, Integer angle, String color, Integer alpha, Integer blurX, Integer blurY, Integer strength, Integer quality) {
    this(name, color, alpha, blurX, blurY, strength, quality);
    this.angle = angle;
    this.type = SHADOW;
  }
  
  // Glow Style Constructor
  public Style(String name, String color, Integer alpha, Integer blurX, Integer blurY, Integer strength, Integer quality) {
    this(name, blurX, blurY, quality);
    this.color = color;
    this.alpha = alpha;
    this.strength = strength;
    this.type = SHADOW;
  }
  
  // Bevel Style Constructor
  public Style(String name, Integer angle, Integer distance, String shadowColor, Integer shadowAlpha,
               String highlightColor, Integer highlightAlpha, Integer blurX, Integer blurY, Integer strength, Integer quality) {
    this(name, blurX, blurY, quality);
    this.angle = angle;
    this.distance = distance;
    this.shadowColor = shadowColor;
    this.shadowAlpha = shadowAlpha;
    this.highlightColor = highlightColor;
    this.highlighAlpha = highlightAlpha;
    this.strength = strength;
    this.type = BEVEL;
    
  }
  
  // Blur Style Constructor
  public Style(String name, Integer blurX, Integer blurY, Integer quality) {
    this.name = name;
    this.blurX = blurX;
    this.blurY = blurY;
    this.quality = quality;
    this.type = BLUR;
  }
  
  public String getType() {
    return type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public String getFont() {
    return font;
  }
  
  public void setFont(String font) {
    this.font = font;
  }
  
  public Integer getSize() {
    return size;
  }
  
  public void setSize(Integer size) {
    this.size = size;
  }
  
  public String getColor() {
    return color;
  }
  
  public void setColor(String color) {
    this.color = color;
  }
  
  public String getAlign() {
    return align;
  }
  
  public void setAlign(String align) {
    this.align = align;
  }
  
  public Integer getBold() {
    return bold;
  }
  
  public void setBold(Integer bold) {
    this.bold = bold;
  }
  
  public Integer getItalic() {
    return italic;
  }
  
  public void setItalic(Integer italic) {
    this.italic = italic;
  }
  
  public Integer getUnderline() {
    return underline;
  }
  
  public void setUnderline(Integer underline) {
    this.underline = underline;
  }
  
  public String getBgColor() {
    return bgColor;
  }
  
  public void setBgColor(String bgColor) {
    this.bgColor = bgColor;
  }
  
  public String getBorderColor() {
    return borderColor;
  }
  
  public void setBorderColor(String borderColor) {
    this.borderColor = borderColor;
  }
  
  public Integer getIsHTML() {
    return isHTML;
  }
  
  public void setIsHTML(Integer isHTML) {
    this.isHTML = isHTML;
  }
  
  public Integer getLeftMargin() {
    return leftMargin;
  }
  
  public void setLeftMargin(Integer leftMargin) {
    this.leftMargin = leftMargin;
  }
  
  public Integer getLetterSpacing() {
    return letterSpacing;
  }
  
  public void setLetterSpacing(Integer letterSpacing) {
    this.letterSpacing = letterSpacing;
  }
  
  public String getParam() {
    return param;
  }
  
  public void setParam(String param) {
    this.param = param;
  }
  
  public Integer getStart() {
    return start;
  }
  
  public void setStart(Integer start) {
    this.start = start;
  }
  
  public Integer getDuration() {
    return duration;
  }
  
  public void setDuration(Integer duration) {
    this.duration = duration;
  }
  
  public String getEasing() {
    return easing;
  }
  
  public void setEasing(String easing) {
    this.easing = easing;
  }
  
  public Integer getDistance() {
    return distance;
  }
  
  public void setDistance(Integer distance) {
    this.distance = distance;
  }
  
  public Integer getAngle() {
    return angle;
  }
  
  public void setAngle(Integer angle) {
    this.angle = angle;
  }
  
  public Integer getAlpha() {
    return alpha;
  }
  
  public void setAlpha(Integer alpha) {
    this.alpha = alpha;
  }
  
  public Integer getBlurX() {
    return blurX;
  }

  public void setBlurX(Integer blurX) {
    this.blurX = blurX;
  }
  
  public Integer getBlurY() {
    return blurY;
  }
  
  public void setBlurY(Integer blurY) {
    this.blurY = blurY;
  }
  
  public Integer getStrength() {
    return strength;
  }
  
  public void setStrength(Integer strength) {
    this.strength = strength;
  }
  
  public Integer getQuality() {
    return quality;
  }
  
  public void setQuality(Integer quality) {
    this.quality = quality;
  }
  
  public String getShadowColor() {
    return shadowColor;
  }
  
  public void setShadowColor(String shadowColor) {
    this.shadowColor = shadowColor;
  }
  
  public Integer getShadowAlpha() {
    return shadowAlpha;
  }
  
  public void setShadowAlpha(Integer shadowAlpha) {
    this.shadowAlpha = shadowAlpha;
  }
  
  public String getHighlightColor() {
    return highlightColor;
  }
  
  public void setHighlightColor(String highlightColor) {
    this.highlightColor = highlightColor;
  }
  
  public Integer getHighlighAlpha() {
    return highlighAlpha;
  }
  
  public void setHighlighAlpha(Integer highlighAlpha) {
    this.highlighAlpha = highlighAlpha;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
 
}
