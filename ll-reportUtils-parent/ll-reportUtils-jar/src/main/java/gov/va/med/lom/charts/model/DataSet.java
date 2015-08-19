package gov.va.med.lom.charts.model;

import java.util.List;
import java.util.ArrayList;

public class DataSet {
  
  private String id;
  private String seriesName;
  private String renderAs;
  private String color;
  private String alpha;
  private String ratio;
  private Integer showValues;
  private Integer dashed;
  private Integer includeInLegen;
  private Integer plotBorderThickness;
  private Integer showPlotBorder;
  private Integer anchorSides;
  private Integer anchorRadius;
  private Integer drawLine;
  private List<Set> data;
  
  public DataSet() {
    data = new ArrayList<Set>();
  }
  
  public DataSet(String seriesName) {
    this();
    this.seriesName = seriesName;
  }
  
  public DataSet(String seriesName, List<Set> data) {
    this(seriesName);
    this.data = data;
  }

  public String getSeriesName() {
    return seriesName;
  }

  public void setSeriesName(String seriesName) {
    this.seriesName = seriesName;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getAlpha() {
    return alpha;
  }

  public void setAlpha(String alpha) {
    this.alpha = alpha;
  }

  public String getRatio() {
    return ratio;
  }

  public void setRatio(String ratio) {
    this.ratio = ratio;
  }

  public Integer getShowValues() {
    return showValues;
  }

  public void setShowValues(Integer showValues) {
    this.showValues = showValues;
  }

  public Integer getDrawLine() {
    return drawLine;
  }

  public void setDrawLine(Integer drawLine) {
    this.drawLine = drawLine;
  }

  public Integer getDashed() {
    return dashed;
  }

  public void setDashed(Integer dashed) {
    this.dashed = dashed;
  }

  public Integer getIncludeInLegen() {
    return includeInLegen;
  }

  public void setIncludeInLegen(Integer includeInLegen) {
    this.includeInLegen = includeInLegen;
  }

  public List<Set> getData() {
    return data;
  }

  public void setData(List<Set> data) {
    this.data = data;
  }

  public String getRenderAs() {
    return renderAs;
  }

  public void setRenderAs(String renderAs) {
    this.renderAs = renderAs;
  }

  public Integer getPlotBorderThickness() {
    return plotBorderThickness;
  }

  public void setPlotBorderThickness(Integer plotBorderThickness) {
    this.plotBorderThickness = plotBorderThickness;
  }

  public Integer getShowPlotBorder() {
    return showPlotBorder;
  }

  public void setShowPlotBorder(Integer showPlotBorder) {
    this.showPlotBorder = showPlotBorder;
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
  
}
