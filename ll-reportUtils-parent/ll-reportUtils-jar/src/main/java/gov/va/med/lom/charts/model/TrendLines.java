package gov.va.med.lom.charts.model;

import java.util.List;
import java.util.ArrayList;

public class TrendLines {
  
  List<Line> line;
  
  public TrendLines() {
    line = new ArrayList<Line>();
  }

  public List<Line> getLine() {
    return line;
  }

  public void setLine(List<Line> line) {
    this.line = line;
  }
  
}
