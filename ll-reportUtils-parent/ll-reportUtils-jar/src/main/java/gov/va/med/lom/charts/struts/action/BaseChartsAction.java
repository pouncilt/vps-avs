package gov.va.med.lom.charts.struts.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.Preparable;

import gov.va.med.lom.javaUtils.misc.StringUtils;

import gov.va.med.lom.json.struts.action.BaseAction;

import gov.va.med.lom.charts.model.*;

public class BaseChartsAction extends BaseAction implements ServletRequestAware, Preparable {
    
  protected static final Log log = LogFactory.getLog(BaseChartsAction.class);
  
  // chart components
  protected FusionChart fusionChart;
  protected Categories categories;
  protected TrendLines trendLines;
  protected Styles styles;
  
  public void prepare() throws Exception {
    super.prepare();
    request.getSession(); // to avoid IllegalStateException
    initialize();
  }
  
  protected void initialize() {
    // create chart components
    fusionChart = new FusionChart();
    styles = new Styles();
    // set and initialize components
    fusionChart = new FusionChart();
    fusionChart.setStyles(styles);
    // set some defaults
    setType(FusionChart.COLUMN_2D);
    setHeight("100%");
    setWidth("100%");
  }

  /*
   * Fusion Chart
   */
  protected FusionChart getFusionChart() {
    return fusionChart;
  }
  
  protected void setType(String type) {
    fusionChart.getMetadata().setType(type);
    genChartId(); // generate a DOM id based upon chart type and random number
  }
  
  protected void setWidth(String width) {
    fusionChart.getMetadata().setWidth(width);
  }
  
  protected void setHeight(String height) {
    fusionChart.getMetadata().setHeight(height);
  }
  
  protected void setChartId(String chartId) {
    fusionChart.getMetadata().setChartId(chartId);
  }
  
  protected void genChartId() {
    setChartId(fusionChart.getMetadata().getType() + "-" + StringUtils.getRandomInt(999, 9999));
  }
  
  /*
   * Chart Properties
   */
  protected Chart getChart() {
    return fusionChart.getChart();
  }
  
  protected void setCaption(String caption) {
    fusionChart.getChart().setCaption(caption);
  }
  
  protected void setSubCaption(String subCaption) {
    fusionChart.getChart().setSubCaption(subCaption);
  }
  
  protected void setXAxisName(String xAxisName) {
    fusionChart.getChart().setXAxisName(xAxisName);
  }
  
  protected void setYAxisName(String yAxisName) {
    fusionChart.getChart().setYAxisName(yAxisName);
  }
  
  protected void setXAxisMinValue(Integer xAxisMinValue) {
    fusionChart.getChart().setXAxisMinValue(xAxisMinValue);
  }
       
  protected void setXAxisMaxValue(Integer xAxisMaxValue) {
    fusionChart.getChart().setXAxisMaxValue(xAxisMaxValue);
  }
     
  protected void setYAxisMinValue(Integer yAxisMinValue) {
    fusionChart.getChart().setYAxisMinValue(yAxisMinValue);
  }
       
  protected void setYAxisMaxValue(Integer yAxisMaxValue) {
    fusionChart.getChart().setYAxisMaxValue(yAxisMaxValue);
  }
  
  protected void setNumberPrefix(String numberPrefix) {
    fusionChart.getChart().setNumberPrefix(numberPrefix);
  }
  
  protected void setBgColor(String bgColor) {
    fusionChart.getChart().setBgColor(bgColor);
  }       
  
  protected void setBgAlpha(Integer bgAlpha) {
    fusionChart.getChart().setBgAlpha(bgAlpha);
  }     
  
  protected void setUseRoundEdges(Integer useRoundEdges) {
    fusionChart.getChart().setUseRoundEdges(useRoundEdges);
  }       
  
  protected void setShowBorder(Integer showBorder) {
    fusionChart.getChart().setShowBorder(showBorder);
  }       
  
  protected void setPalette(Integer palette) {
    fusionChart.getChart().setPalette(palette);
  }       
  
  protected void setPaletteColors(String paletteColors) {
    fusionChart.getChart().setPaletteColors(paletteColors);
  }       
  
  protected void setDecimals(String decimals) {
    fusionChart.getChart().setDecimals(decimals);
  }       
  
  protected void setNumberSuffix(String numberSuffix) {
    fusionChart.getChart().setNumberSuffix(numberSuffix);
  }       
  
  protected void setAnimation(Integer animation) {
    fusionChart.getChart().setAnimation(animation);
  }       
  
  protected void setShowLabels(Integer showLabels) {
    fusionChart.getChart().setShowLabels(showLabels);
  }       
  
  protected void setLabelDisplay(String labelDisplay) {
    fusionChart.getChart().setLabelDisplay(labelDisplay);
  }       
    
  protected void setShowValues(Integer showValues) {
    fusionChart.getChart().setShowValues(showValues);
  }   
  
  protected void setShowLegend(Integer showLegend) {
    fusionChart.getChart().setShowLegend(showLegend);
  }   

  
  /*
   * Categories 
   */
  protected Categories getCategories() {
    return fusionChart.getCategories();
  }
  
  protected void setCategories(Categories categories) {
    fusionChart.setCategories(categories);
  }
  
  protected Categories addCategories() {
    categories = new Categories();
    fusionChart.setCategories(categories);
    return categories;
  }
  
  protected Categories addCategories(String font, Integer fontSize, String fontColor) {
    categories = new Categories(font, fontSize, fontColor);
    fusionChart.setCategories(categories);
    return categories;
  }  
  
  protected void addCategory(Category category) {
    if (categories == null) {
      addCategories();
    }
    List<Category> categoryList = categories.getCategory();
    categoryList.add(category);
  }
  
  protected Category addCategory(String label) {
    if (categories == null) {
      addCategories();
    }
    Category category = new Category(label);
    List<Category> categoryList = categories.getCategory();
    categoryList.add(category);
    return category;
  }
    
  protected Category addCategory(String label, String toolText) {
    if (categories == null) {
      addCategories();
    }
    Category category = new Category(label, toolText);
    List<Category> categoryList = categories.getCategory();
    categoryList.add(category);
    return category;
  }

  
  /*
   * Chart Data 
   */
  protected List<Set> getData() {
    return fusionChart.getData(); 
  }
  
  protected void setData(List<Set> data) {
    fusionChart.setData(data);
  }
  
  protected void addData(Set set) {
    List<Set> data = fusionChart.getData();
    data.add(set);
  }
  
  protected Set addData(String label) {
    Set set = new Set(label);
    List<Set> data = fusionChart.getData();
    data.add(set);   
    return set;
  }  
  
  protected Set addData(String label, double value) {
    Set set = new Set(label, value);
    List<Set> data = fusionChart.getData();
    data.add(set);   
    return set;
  }
  
  protected Set addData(String label, double value, String color) {
    Set set = new Set(label, value, color);
    List<Set> data = fusionChart.getData();
    data.add(set);   
    return set;
  }
  
  protected Set addData(String label, double value, String color, String displayValue, String toolText) {
    Set set = new Set(label, value, color, displayValue, toolText);
    List<Set> data = fusionChart.getData();
    data.add(set);   
    return set;
  }
  
  protected Set addData(String label, double value, String color, String displayValue, String toolText, 
                        String link, boolean showLabel, boolean showValue, boolean dashed, Integer alpha) {
    Set set = new Set(label, value, color, displayValue, toolText, link, showLabel, showValue, dashed, alpha);
    List<Set> data = fusionChart.getData();
    data.add(set);   
    return set;
  }
     
  protected Set addData(String borderColor, boolean isSliced, String label, double value, String displayValue, 
                        String color, String link, String toolText) {
    Set set = new Set(borderColor, isSliced, label, value, displayValue, color, link, toolText);
    List<Set> data = fusionChart.getData();
    data.add(set);   
    return set;
  }
  
  protected Set addData(String borderColor, boolean isSliced, String label, double value, String displayValue, 
                        String color, String link, String toolText, boolean dashed, Integer alpha) {
    Set set = new Set(borderColor, isSliced, label, value, displayValue, color, link, toolText, dashed, alpha);
    List<Set> data = fusionChart.getData();
    data.add(set);   
    return set;
  }
    
  
  protected Set addData(String label, double value, String displayValue, String color, String link, String toolText, 
                        boolean showLabel, boolean showValue, String valuePosition, boolean dashed, Integer alpha) {
    Set set = new Set(label, value, displayValue, color, link, toolText, showLabel, showValue, valuePosition, dashed, alpha);
    List<Set> data = fusionChart.getData();
    data.add(set);   
    return set;
  }
  
  protected Set addData(String label, double value, String displayValue, String color, String link, String toolText, 
                        boolean showLabel, boolean showValue, String valuePosition, boolean dashed, Integer alpha,
                        Integer anchorSides, Integer anchorRadius, String anchorBorderColor, Integer anchorBorderThickness,
                        String anchorBgColor, Integer anchorAlpha, Integer anchorBgAlpha) {
    Set set = new Set(label, value, displayValue, color, link, toolText, showLabel, showValue, 
                      valuePosition, dashed, alpha, anchorSides, anchorRadius, anchorBorderColor,
                      anchorBorderThickness, anchorBgColor, anchorAlpha, anchorBgAlpha);
    List<Set> data = fusionChart.getData();
    data.add(set);   
    return set;
  }  
  
  
  /*
   * Chart Data Set
   */
  protected List<DataSet> getDataSet() {
    return fusionChart.getDataSet(); 
  }
  
  protected void setDataSet(List<DataSet> dataSet) {
    fusionChart.setDataSet(dataSet);
  }
  
  protected void addDataSet(DataSet dataSet) {
    List<DataSet> dataSetList = getDataSet();
    dataSetList.add(dataSet);
    setDataSet(dataSetList);
  }  
  
  protected DataSet addDataSet(String seriesName) {
    DataSet dataSet = new DataSet(seriesName);
    addDataSet(dataSet);
    return dataSet;
  }
  
  protected DataSet addDataSet(String seriesName, List<Set> data) {
    DataSet dataSet = new DataSet(seriesName, data);
    addDataSet(dataSet);
    return dataSet;
  }
    
  
  /*
   * Vertical Seperator Lines
   */
  protected Set addVLine() {
    Set set = new Set();
    set.setVLine(true);
    List<Set> data = fusionChart.getData();
    data.add(set);   
    return set;
  }
  
  protected Set addVLine(Set set) {
    set.setVLine(true);
    List<Set> data = fusionChart.getData();
    data.add(set);   
    return set;
  }
     
  
  protected Set addVLine(String label) {
    Set set = new Set();
    set.setVLine(true);
    set.setLabel(label);
    List<Set> data = fusionChart.getData();
    data.add(set);   
    return set;
  }
   
  protected Set addVLine(String label, String color) {
    Set set = new Set();
    set.setVLine(true);
    set.setLabel(label);
    set.setColor(color);
    List<Set> data = fusionChart.getData();
    data.add(set);   
    return set;
  }
     
  
  /*
   * Trend Lines
   */
  protected TrendLines getTrendLines() {
    return trendLines;
  }
  
  protected void setTrendLine(Line line) {
    TrendLines trendLines = fusionChart.getTrendLines();
    if (trendLines == null) {
      trendLines = new TrendLines();
      fusionChart.setTrendLines(trendLines);
    }
    List<Line> lines = trendLines.getLine();
    lines.add(line);
  }

  protected Line addTrendLine(Double value, String displayValue) {
    Line line = new Line();
    line.setValue(value);
    line.setDisplayValue(displayValue);
    setTrendLine(line);
    return line;
  }
  
  protected Line addTrendLine(Double startValue, Double endValue, String displayValue, String color) {
    Line line = new Line(startValue, endValue, displayValue, color);
    setTrendLine(line);
    return line;
  }

  
  /*
   * Styles
   */
  protected Styles getStyles() {
    return styles;
  }
  
  protected void setStyles(Styles styles) {
    this.styles = styles;
  }
  
  protected void addStyle(Style style) {
    List<Style> definition = styles.getDefinition();
    definition.add(style);
  }
  
  protected Style addFontStyle(String name, String font, Integer size, String color, String align, boolean bold,
                                boolean italic, boolean underline, String bgColor, String borderColor, 
                                boolean isHTML, Integer leftMargin, Integer letterSpacing) {
    Style style = new Style(name, font, size, color, align, bold, italic, underline, 
                            bgColor, borderColor, isHTML, leftMargin, letterSpacing);
    addStyle(style);
    return style;
  }
  
  protected Style addAnimationStyle(String name, String param, Integer start, Integer duration, String easing) {
    Style style = new Style(name, param, start, duration, easing);
    addStyle(style);
    return style;
  }
  
  protected Style addShadowStyle(String name, Integer angle, String color, Integer alpha, 
                                 Integer blurX, Integer blurY, Integer strength, Integer quality) {
    Style style = new Style(name, angle, color, alpha, blurX, blurY, strength, quality);
    addStyle(style);
    return style;
  }
  
  protected Style addGlowStyle(String name, String color, Integer alpha, Integer blurX, 
                               Integer blurY, Integer strength, Integer quality) {
    Style style = new Style(name, color, alpha, blurX, blurY, strength, quality);
    addStyle(style);
    return style;
  }
  
  protected Style addBevelStyle(String name, Integer angle, Integer distance, String shadowColor, Integer shadowAlpha,
                                String highlightColor, Integer highlightAlpha, Integer blurX, Integer blurY, 
                                Integer strength, Integer quality) {
    Style style = new Style(name, angle, distance, shadowColor, shadowAlpha, highlightColor, highlightAlpha,
                            blurX, blurY, strength, quality);
    addStyle(style);
    return style;
  }
  
  protected Style addBlurStyle(String name, Integer blurX, Integer blurY, Integer quality) {
    Style style = new Style(name, blurX, blurY, quality);
    addStyle(style);
    return style;
  }
              
  protected void addApplication(Apply apply) {
    List<Apply> applications = this.styles.getApplication();
    applications.add(apply);
  }
  
  protected Apply addApplication(String toObject, String styles) {
    Apply apply = new Apply(toObject, styles);
    addApplication(apply);
    return apply;
  }
      
  
  /*
   * Chart Export Properties 
   */
  protected void setExportEnabled(Integer exportEnabled) {
    fusionChart.getChart().setExportEnabled(exportEnabled);
  }
  
  protected void setExportHandler(String exportHandler) {
    fusionChart.getChart().setExportHandler(exportHandler);
  }
  
  protected void setExportAtClient(Integer exportAtClient) {
    fusionChart.getChart().setExportAtClient(exportAtClient);
  }
  
  protected void setExportAction(String exportAction) {
    fusionChart.getChart().setExportAction(exportAction);
  }
  
  protected void setExportTargetWindow(String exportTargetWindow) {
    fusionChart.getChart().setExportTargetWindow(exportTargetWindow);
  }
  
  protected void setExportFileName(String exportFileName) {
    fusionChart.getChart().setExportFileName(exportFileName);
  }
  
  protected void setExportFormat(String exportFormat) {
    fusionChart.getChart().setExportFormat(exportFormat);
  }
  
  protected void setShowExportDialog(Integer showExportDialog) {
    fusionChart.getChart().setShowExportDialog(showExportDialog);
  }
  
  protected void setExportCallback(String exportCallback) {
    fusionChart.getChart().setExportCallback(exportCallback);
  }
  
  protected void setExportShowMenuItem(Integer exportShowMenuItem) {
    fusionChart.getChart().setExportShowMenuItem(exportShowMenuItem);
  }
  
  
  /*
   * Data Response Methods
   */
  protected String sendJson() {
    if ((styles.getApplication().size() == 0) || (styles.getApplication().size() == 0)) {
      fusionChart.setStyles(null);
    }
    // check if data exists, otherwise set to null
    if (fusionChart.getData().size() == 0) {
      fusionChart.setData(null);
    }
    // check if datasets exist, otherwise set to null
    if (fusionChart.getDataSet().size() == 0) {
      fusionChart.setDataSet(null);
    }
    //return setJson(fusionChart);
    return writeJson(fusionChart);
  }
  
}
