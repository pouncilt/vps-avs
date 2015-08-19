package gov.va.med.lom.reports.text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gov.va.med.lom.javaUtils.misc.DateUtils;
import gov.va.med.lom.reports.model.GridColumn;
import gov.va.med.lom.reports.model.GridData;

public abstract class AbstractTextReport {

  private static final Log log = LogFactory.getLog(AbstractTextReport.class);
  protected static final String NEW_LINE = "\n";
  public static final Character COMMA_DELIMITED = ',';
  public static final Character TAB_DELIMITED = '\t';
  
  protected NumberFormat formatter;
  protected String station;
  protected String title;
  protected String subtitle;
  protected GridData report;
  protected Character delimiter;
  protected List<GridColumn> reportColumns;
  protected String reportText;

  public AbstractTextReport(String station, GridData report, Character delimiter) {
    this.formatter = new DecimalFormat("#0.00");
    this.station = station;
    this.report = report;
    reportColumns = report.getColumns();
    this.delimiter = delimiter;
    setTitle(report.getMetaData().getTitle());
    setSubtitle(report.getMetaData().getSubtitle());
    this.reportText = createDocument();
  }
  
  // Overridden by descending classes
  public abstract String columnValue(int colIndex, Object obj);
  
  public String report() {
    return this.reportText;
  }
  
  protected String createDocument() {
    StringBuffer sb = new StringBuffer();
    
    // add meta information
    if (title != null) {
      sb.append(quoteString(title, delimiter));
      sb.append(NEW_LINE);
    }
    if (subtitle != null) {
      sb.append(quoteString(subtitle, delimiter));
      sb.append(NEW_LINE);
    }    
    if (station != null) {
      sb.append(quoteString(station, delimiter));
      sb.append(NEW_LINE);
    }
    sb.append("Date: " + DateUtils.getEnglishDate());
    sb.append(NEW_LINE);
    
    // spacer row
    sb.append(NEW_LINE);
    
    // header row
    int colCount = 0;
    for (GridColumn reportColumn : reportColumns) {
      // skip hidden columns
      if (reportColumn.isHidden()) {
        colCount++;
        continue;
      }      
      sb.append(quoteString(reportColumn.getHeader(), delimiter));
      if (colCount < reportColumns.size()-1)
        sb.append(delimiter);
      colCount++;
    }
    sb.append(NEW_LINE);
    
    // data rows
    List rootObjects = (List)report.getRoot();    
    
    for (Object object : rootObjects) {
      
      colCount = 0;
      for (GridColumn reportColumn : reportColumns) {
        // skip hidden columns
        if (reportColumn.isHidden()) {
          colCount++;
          continue;
        }        
        String colVal = columnValue(colCount, object);
        sb.append(quoteString(colVal, delimiter));
        if (colCount < reportColumns.size()-1)
          sb.append(delimiter);
        colCount++;
      }
      sb.append(NEW_LINE);
    }
    
    return sb.toString();
  }
  
  public static String quoteString(String str, Character delim) {
    if ((str != null) && str.indexOf(delim) >= 0) {
      StringBuffer sb = new StringBuffer(str);
      sb.insert(0, '"');
      sb.insert(sb.length()-1, '"');
      return sb.toString();
    } else
      return str;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }
  
  public static String concatReports(List<AbstractTextReport> reports) {
    StringBuffer sb = new StringBuffer();
    for (AbstractTextReport report : reports) {
      sb.append(report.report());
      sb.append(NEW_LINE);
      sb.append(NEW_LINE);
    }
    return sb.toString();
  }

}