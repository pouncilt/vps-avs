package gov.va.med.lom.charts.model;

public class Apply {
  
  private String toObject;
  private String styles;
  
  // Chart Objects
  public static final String BACKGROUND = "background";
  public static final String CANVAS = "canvas";
  public static final String CAPTION = "caption";
  public static final String DATALABELS = "datalabels";
  public static final String DATAPLOT = "dataplot";
  public static final String DATAVALUES = "datavalues";
  public static final String DIVLINES = "divlines";
  public static final String HGRID = "hgrid";
  public static final String SUBCAPTION = "subcaption";
  public static final String TOOLTIP = "tooltip";
  public static final String TRENDLINES = "trendlines";
  public static final String TRENDVALUES = "trendvalues";
  public static final String VLINES = "vlines";
  public static final String XAXISNAME = "xaxisname";
  public static final String YAXISNAME = "yaxisname";
  public static final String YAXISVALUES = "yaxisvalues";
  public static final String QUADRANTLABELS = "quadrantlabels";
  
  public Apply(String toObject, String styles) {
    this.toObject = toObject;
    this.styles = styles;
  }
  
  public String getToObject() {
    return toObject;
  }
  
  public void setToObject(String toObject) {
    this.toObject = toObject;
  }
  
  public String getStyles() {
    return styles;
  }
  
  public void setStyles(String styles) {
    this.styles = styles;
  }
  
}
