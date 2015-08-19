package gov.va.med.lom.charts.model;

import java.util.List;
import java.util.ArrayList;

public class FusionChart {
  
  @SuppressWarnings("rawtypes")

  private ChartMetaData metadata;
  private Chart chart;
  private Categories categories;
  private List<DataSet> dataSet;
  private List<Set> data;
  private TrendLines trendLines;
  private Styles styles;
  
  // Chart Types
  public static final String AREA_2D = "Area2D";
  public static final String BAR_2D = "Bar2D";
  public static final String BUBBLE = "Bubble";
  public static final String COLUMN_2D = "Column2D";
  public static final String COLUMN_3D = "Column3D";
  public static final String DOUGHNUT_2D = "Doughnut2D";
  public static final String DOUGHNUT_3D = "Doughnut3D";
  public static final String FC_EXPORTER = "FC_Exporter";
  public static final String LINE = "Line";
  public static final String MARIMEKKO = "Marimekko";
  public static final String MS_AREA = "MSArea";
  public static final String MS_BAR2D = "MSBar2D";
  public static final String MS_BAR3D = "MSBar3D";
  public static final String MS_COLUMN2D = "MSColumn2D";
  public static final String MS_COLUMN3D = "MSColumn3D";
  public static final String MS_COLUMN_3D_LINE_DY = "MSColumn3DLineDY";
  public static final String MS_COLUMN_LINE_3D = "MSColumnLine3D";
  public static final String MS_COMBI_2D = "MSCombi2D";
  public static final String MS_COMBI_3D = "MSCombi3D";
  public static final String MS_COMBI_DY_2D = "MSCombiDY2D";
  public static final String MS_LINE = "MSLine";
  public static final String MS_STACKED_COLUMN_2D = "MSStackedColumn2D";
  public static final String MS_STACKED_COLUMN_2D_LINE_DY = "MSStackedColumn2DLineDY";
  public static final String PARETO_2D = "Pareto2D";
  public static final String PARETO_3D = "Pareto2D";
  public static final String PIE_2D = "Pie2D";
  public static final String PIE_3D = "Pie3D";
  public static final String SCATTER = "Scatter";
  public static final String SCROLL_AREA_2D = "ScrollArea2D";
  public static final String SCROLL_COLUMN_2D = "ScrollColumn2D";
  public static final String SCROLL_COMBI_2D = "ScrollCombi2D";
  public static final String SCROLL_COMBI_DY_2D = "ScrollCombiDY2D";
  public static final String SCROLL_LINE_2D = "ScrollLine2D";
  public static final String SCROLL_STACKED_COLUMN_2D = "ScrollStackedColumn2D";
  public static final String SS_GRID = "SSGrid";
  public static final String STACKED_AREA_2D = "StackedArea2D";
  public static final String STACKED_BAR_2D = "StackedBar2D";
  public static final String STACKED_BAR_3D = "StackedBar3D";
  public static final String STACKED_COLUMN_2D = "StackedColumn2D";
  public static final String STACKED_COLUMN_2D_LINE = "StackedColumn2DLine";
  public static final String STACKED_COLUMN_3D = "StackedColumn3D";
  public static final String STACKED_COLUMN_3D_LINE = "StackedColumn3DLine";
  public static final String STACKED_COLUMN_3D_LINE_DY = "StackedColumn3DLineDY";
  
  public FusionChart() {
    metadata = new ChartMetaData();
    chart = new Chart();
    data = new ArrayList<Set>();
    dataSet = new ArrayList<DataSet>();
  }
  
  public Chart getChart() {
    return chart;
  }
  
  public void setChart(Chart chart) {
    this.chart = chart;
  }
  
  public List<Set> getData() {
    return data;
  }
  
  public void setData(List<Set> data) {
    this.data = data;
  }
  
  public TrendLines getTrendLines() {
    return trendLines;
  }
  
  public void setTrendLines(TrendLines trendLines) {
    this.trendLines = trendLines;
  }
  
  public Styles getStyles() {
    return styles;
  }
  
  public void setStyles(Styles styles) {
    this.styles = styles;
  }

  public ChartMetaData getMetadata() {
    return metadata;
  }

  public void setMetadata(ChartMetaData metadata) {
    this.metadata = metadata;
  }

  public List<DataSet> getDataSet() {
    return dataSet;
  }

  public void setDataSet(List<DataSet> dataSet) {
    this.dataSet = dataSet;
  }

  public Categories getCategories() {
    return categories;
  }

  public void setCategories(Categories categories) {
    this.categories = categories;
  }

}
