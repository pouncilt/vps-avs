package gov.va.med.lom.reports.pdf;

import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import gov.va.med.lom.javaUtils.misc.DateUtils;
import gov.va.med.lom.javaUtils.misc.ReflectUtil;
import gov.va.med.lom.reports.model.GridColumn;
import gov.va.med.lom.reports.model.GridData;

public abstract class AbstractPdfReport {

  private static final Log log = LogFactory.getLog(AbstractPdfReport.class);

  protected static NumberFormat CURRENCY = NumberFormat.getCurrencyInstance();
  protected static BaseColor COLOR_NORMAL = new BaseColor(0xFFFFFF);
  protected static BaseColor COLOR_HEADER = new BaseColor(0xF1F1F1);
  protected static BaseColor COLOR_ROW_HEADER = new BaseColor(0xD0DEF0);
  protected static BaseColor COLOR_STRIPE = new BaseColor(0xFAFAFA);
  protected static BaseColor COLOR_HEADER_BORDER = new BaseColor(0x000000);
  protected static BaseColor COLOR_ROW_BORDER = new BaseColor(0xEDEDED);
  protected static BaseColor COLOR_GROUP_BORDER = new BaseColor(0x99BBE8);
  protected static Font FONT_GROUP_ROW = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
  protected static Font FONT_CELL = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL);
  protected static Font FONT_CELL_STRIKE = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.STRIKETHRU);
  protected static Font FONT_CELL_HEADER = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
  protected static Font FONT_PAGE_HEADER = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
  protected static Font FONT_PAGE_SUBTITLE = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
  protected static Font FONT_SMALL = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL);
  protected static String NEW_LINE = "\r\n";

  protected NumberFormat formatter;
  protected String station;
  protected String user;
  protected String title;
  protected String subtitle;
  protected Rectangle pageSize;
  protected int[] colWidths;
  protected GridData report;
  protected PdfTemplate total;
  protected List<GridColumn> reportColumns;
  protected ByteArrayOutputStream ouputStream;

  public AbstractPdfReport() {}
  
  public AbstractPdfReport(String station, String user, GridData report, int[] colWidths, Rectangle pageSize) {
    this(station, user, report, pageSize);
    this.colWidths = colWidths;
    createDocument();    
  }
  
  public AbstractPdfReport(String station, String user, GridData report, Rectangle pageSize) {
    this.formatter = new DecimalFormat("#0.00");
    this.station = station;
    this.user = user;
    this.report = report;
    reportColumns = report.getColumns();
    if (pageSize != null)
      this.pageSize = pageSize;
    else
      this.pageSize = PageSize.LETTER;
    setTitle(report.getMetaData().getTitle());
    setSubtitle(report.getMetaData().getSubtitle());    
  }
  
  // abstract methods overridden by extending classes
  public abstract String columnValue(int colIndex, Object obj);
  
  public abstract Class getRootClass();
  
  // public method called by client to get report data as byte array output stream
  public ByteArrayOutputStream report() {
    return ouputStream;
  }
  
  // methods used internally and by extending classes
  protected String footerText() {
    return null;
  }
  
  protected void insertContents(PdfPTable main) {
    try {
      PdfPTable tbl1 = new PdfPTable(colWidths.length);
      tbl1.setWidths(colWidths);
      tbl1.setWidthPercentage(100);
      tbl1.setHeaderRows(2);

      // add an empty row for spacing
      PdfPCell cell = emptyCell();
      cell.setColspan(colWidths.length);
      cell.setMinimumHeight(10f);
      tbl1.addCell(cell);
      
      // header row
      for (GridColumn reportColumn : reportColumns) {
        // skip hidden columns
        if (reportColumn.isHidden()) {
          continue;
        }        
        
        cell = new PdfPCell(new Phrase(reportColumn.getHeader(), FONT_CELL_HEADER));
        int align = Element.ALIGN_LEFT;
        if ((reportColumn.getAlign() != null) && (reportColumn.getAlign().equals("center"))) {
          align = Element.ALIGN_CENTER;   
        } else if ((reportColumn.getAlign() != null) && (reportColumn.getAlign().equals("right"))) {
          align = Element.ALIGN_RIGHT;
        }
        cell.setFixedHeight(23f);
        cell.setHorizontalAlignment(align);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.BOX);
        cell.setBorderColor(COLOR_HEADER_BORDER);
        cell.setBackgroundColor(COLOR_ROW_HEADER);
        tbl1.addCell(cell);
      }
      
      // data rows
      if ((report.getMetaData() != null) && (report.getMetaData().getGroupInfo() != null) &&
          (report.getMetaData().getGroupInfo().getGroupField() != null)) {
        dataTableGrouped(tbl1);
      } else {
        dataTable(tbl1);
      }
      
      // add an empty row for spacing
      cell = emptyCell();
      cell.setColspan(colWidths.length);
      cell.setMinimumHeight(10f);
      tbl1.addCell(cell);

      // add table to main 
      cell = new PdfPCell(tbl1);
      cell.setBorder(Rectangle.NO_BORDER);
      main.addCell(cell);
      
    } catch (DocumentException e) {
      log.error("error creating document", e);
      return;
    }
  }

  protected ByteArrayOutputStream createDocument() {
    this.ouputStream = (ByteArrayOutputStream)createDocument(null);
    return this.ouputStream;
  }
  
  protected OutputStream createDocument(String filename) {

    Document pdf = new Document(pageSize, 10, 10, 10, 10);
    OutputStream buffer = null;
    if (filename != null) {
      try {
        buffer = new FileOutputStream(filename);
      } catch(Exception e) {
        buffer = new ByteArrayOutputStream();
      }
    } else {
      buffer = new ByteArrayOutputStream();
    }
    try {
      PdfWriter.getInstance(pdf, buffer);
      pdf.addSubject(station + " -- " + getTitle());
      pdf.addAuthor(user);
      pdf.addTitle(getTitle());
      pdf.addCreator(user);
      pdf.open();
      PdfPTable main = createMainTable();
      pdf.add(main);
      pdf.close();
    } catch (DocumentException e) {
      log.error("Error creating PdfWriter", e);
      throw new RuntimeException("error creating PdfWriter", e);
    }

    return buffer;
  }

  protected void dataTable(PdfPTable tbl) {

    int rowCount = 0;
    BaseColor c;

    List rootObjects = (List)report.getRoot();    
    
    for (Object object : rootObjects) {
      
      if (rowCount % 2 == 0) {
        c = COLOR_NORMAL;
      } else {
        c = COLOR_STRIPE;
      }

      int colCount = 0;
      for (GridColumn reportColumn : reportColumns) {
        
        // skip hidden columns
        if (reportColumn.isHidden()) {
          colCount++;
          continue;
        }
        
        int align = Element.ALIGN_LEFT;
        if ((reportColumn.getAlign() != null) && (reportColumn.getAlign().equals("center"))) {
          align = Element.ALIGN_CENTER;   
        } else if ((reportColumn.getAlign() != null) && (reportColumn.getAlign().equals("right"))) {
          align = Element.ALIGN_RIGHT;
        }
        
        String colVal = columnValue(colCount, object);
        
        PdfPCell cell = new PdfPCell(new Phrase(colVal, FONT_CELL));
        cell.setBorder(Rectangle.BOX);
        if (rowCount == 0) {
          cell.setUseVariableBorders(true);
          cell.setBorderColorBottom(COLOR_ROW_BORDER);
          cell.setBorderColorLeft(COLOR_ROW_BORDER);
          cell.setBorderColorRight(COLOR_ROW_BORDER);
          cell.setBorderColorTop(COLOR_HEADER_BORDER);
        } else {
          cell.setUseVariableBorders(false);
          cell.setBorderColor(COLOR_ROW_BORDER);
        }
        cell.setBackgroundColor(c);
        cell.setHorizontalAlignment(align);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tbl.addCell(cell);
        colCount++;
      }

      rowCount++;

    }
  }
  
  protected void dataTableGrouped(PdfPTable tbl) {
    
    Hashtable<String, List<Object>> groupHT = new Hashtable<String, List<Object>>();
    
    String groupField = null;
    // iterate through grid columns to get header for this field
    List<GridColumn> columns = report.getColumns();
    for (GridColumn column : columns) {
      if (column.getDataIndex().equals(report.getMetaData().getGroupInfo().getGroupField())) {
        groupField = column.getHeader();
        break;
      }
    }
    
    // piece together the name of the "getter" method
    StringBuffer methodName = new StringBuffer("get");
    methodName.append(report.getMetaData().getGroupInfo().getGroupField());
    methodName.setCharAt(3, (char)(methodName.charAt(3) - 32));
    
    // use reflection to get the "getter" method of the group field on the root object
    Method method = null;
    try {
      Class[] argCls = {}; // this is a getter, so no need for argument classes
      method = ReflectUtil.getMethod(getRootClass(), methodName.toString(), argCls);
    } catch(Exception e) {
      e.printStackTrace();
      return;
    }
    
    // iterate through the items in the root object and put in hash table keyed by group field
    List rootObjects = (List)report.getRoot();
    for (Object object : rootObjects) {
      Object groupFieldVal = null;
      try {
        Object args[] = {}; // getter method expects no args
        groupFieldVal = method.invoke(object, args);
      } catch(Exception e) {
        e.printStackTrace();
      }
      if (groupFieldVal != null) {
        List<Object> list = groupHT.get((String)groupFieldVal);
        if (list == null) {
          list = new ArrayList<Object>();
          groupHT.put((String)groupFieldVal, list);
        }
        list.add(object);
      }
    }
    
    // iterate through keys (group field values)
    Enumeration<String> en = groupHT.keys();
    while (en.hasMoreElements()) {
      
      String groupFieldVal = en.nextElement(); 
      List<Object> objects = groupHT.get(groupFieldVal);
      // insert "group field" row
      String rowVal = groupField + ": " + groupFieldVal + " (" + objects.size() + " Items)";
      PdfPCell groupRow = new PdfPCell(new Phrase(rowVal, FONT_GROUP_ROW));
      groupRow.setFixedHeight(30);
      groupRow.setBorderWidthBottom(2);
      groupRow.setBorder(Rectangle.BOTTOM);
      groupRow.setBorderColorBottom(COLOR_GROUP_BORDER);
      groupRow.setBackgroundColor(COLOR_NORMAL);
      groupRow.setHorizontalAlignment(Element.ALIGN_LEFT);
      groupRow.setVerticalAlignment(Element.ALIGN_BOTTOM);
      groupRow.setPaddingBottom(5f);
      groupRow.setColspan(colWidths.length);
      tbl.addCell(groupRow);
      
      // insert group data rows
      for (Object object : objects) {
        
        int colCount = 0;
        for (GridColumn reportColumn : reportColumns) {
          
          // skip hidden columns
          if (reportColumn.isHidden()) {
            colCount++;
            continue;
          }          
          
          int align = Element.ALIGN_LEFT;
          if ((reportColumn.getAlign() != null) && (reportColumn.getAlign().equals("center"))) {
            align = Element.ALIGN_CENTER;   
          } else if ((reportColumn.getAlign() != null) && (reportColumn.getAlign().equals("right"))) {
            align = Element.ALIGN_RIGHT;
          }
          
          String colVal = columnValue(colCount, object);
          
          PdfPCell cell = new PdfPCell(new Phrase(colVal, FONT_CELL));
          cell.setBorder(Rectangle.BOX);
          cell.setBackgroundColor(COLOR_NORMAL);
          cell.setBorderColor(COLOR_ROW_BORDER);
          cell.setHorizontalAlignment(align);
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
          tbl.addCell(cell);
          colCount++;
        }
      }
    }
    
  }
  
  /**
   * returns the data for the upper right corner of each report consisting of
   * current date followed by report id
   * 
   * @param id
   * @param cellFont
   * @param color
   * @return
   */
  protected PdfPTable metaHeader() {
    try {
      PdfPTable datatable = new PdfPTable(1);
      datatable.setWidths(new int[] { 20 });
      datatable.setWidthPercentage(100);

      PdfPCell cell = new PdfPCell(new Phrase(DateUtils.getEnglishDate(), FONT_CELL));
      cell.setBorder(Rectangle.NO_BORDER);
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      cell.setBackgroundColor(COLOR_HEADER);
      datatable.addCell(cell);

      return datatable;
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    return null;
  }

  protected PdfPTable createHeader() {
    try {
      PdfPTable tbl = new PdfPTable(2);
      tbl.setWidths(new int[] { 80, 20 });
      tbl.setWidthPercentage(100);

      PdfPCell cell = new PdfPCell(new Phrase(station, FONT_CELL));
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setColspan(1);
      cell.setBorder(Rectangle.NO_BORDER);
      cell.setBackgroundColor(COLOR_HEADER);
      tbl.addCell(cell);

      PdfPTable mhTable = metaHeader();
        if (mhTable != null) {
        cell = new PdfPCell(metaHeader());
        cell.setRowspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setBackgroundColor(COLOR_HEADER);
        cell.setPaddingBottom(20f);
        tbl.addCell(cell);
      }
        
      float paddingTop = 10f;
      float paddingBottom = 20f;
      if ((getSubtitle() != null) && (!getSubtitle().equals(""))) {
        paddingTop = 5f;
        paddingBottom = 0f;
      }
      cell = new PdfPCell(new Phrase(getTitle(), FONT_PAGE_HEADER));
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      cell.setColspan(2);
      cell.setBorder(Rectangle.NO_BORDER);
      cell.setBackgroundColor(COLOR_HEADER);
      cell.setPaddingTop(paddingTop);
      cell.setPaddingBottom(paddingBottom);
      tbl.addCell(cell);
      
      if ((getSubtitle() != null) && (!getSubtitle().equals(""))) {
        cell = new PdfPCell(new Phrase(getSubtitle(), FONT_PAGE_SUBTITLE));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setBackgroundColor(COLOR_HEADER);
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(10f);
        tbl.addCell(cell);  
      }

      return tbl;
    } catch (Exception e) {
      log.error("error creating header", e);
      return null;
    }
  }
  
  protected PdfPTable createFooter() {
    String text = footerText();
    if (text == null)
      return null;
    try {
      PdfPTable tbl = new PdfPTable(1);
      tbl.setWidths(new int[] { 100 });
      tbl.setWidthPercentage(100);
      
      PdfPCell cell = new PdfPCell(new Phrase(text, FONT_SMALL));
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
      cell.setBorder(Rectangle.NO_BORDER);
      cell.setBackgroundColor(COLOR_HEADER);
      cell.setPaddingTop(10f);
      cell.setPaddingBottom(10f);
      tbl.addCell(cell);

      return tbl;
    } catch (Exception e) {
      log.error("error creating footer", e);
      return null;
    }
  }

  protected PdfPTable createMainTable() {
    try {
      PdfPTable main = new PdfPTable(1);
      main.setWidths(new int[] { 100 });
      main.setWidthPercentage(100);
      
      // header
      PdfPTable header = createHeader();
      if (header != null) {
        PdfPCell cell = new PdfPCell(header);
        cell.setBorder(Rectangle.BOX);
        main.addCell(cell);
        main.setHeaderRows(1);
      }      
      
      // data table
      insertContents(main);
      
      // footer
      PdfPTable footer = createFooter();
      if (footer != null) {
        PdfPCell cell = new PdfPCell(footer);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setBorder(Rectangle.BOX);
        main.addCell(cell);
      }
      
      return main;
    } catch (Exception e) {
      log.error("error creating main table", e);
      return null;
    }
  }

  protected String formatDate(Date d) {
    try {
      return DateUtils.toEnglishDate(d);
    } catch(Exception e) {
      return DateUtils.getEnglishDate();
    }
  }

  protected String formatDateTime(Date d) {
    try {
      return DateUtils.toEnglishDateTime(d);
    } catch(Exception e) {
      return DateUtils.getEnglishDateTime();
    }    
  }

  protected static String checkNull(String s) {
    if (s == null)
      return " ";
    return s;
  }

  protected static PdfPCell emptyCell() {
    PdfPCell cell = new PdfPCell(new Phrase("", FONT_CELL));
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setBackgroundColor(COLOR_NORMAL);
    return cell;
  }
  
  protected void setColWidths(int[] colWidths) {
    this.colWidths = colWidths;
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

  public Rectangle getPageSize() {
    return pageSize;
  }

  public void setPageSize(Rectangle pageSize) {
    this.pageSize = pageSize;
  }
  
  public static ByteArrayOutputStream concatReports(List<AbstractPdfReport> reports) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PdfCopyFields finalCopy = null;
    try {
      List<PdfReader> pdfReadersList = new ArrayList<PdfReader>();
      for (AbstractPdfReport report : reports) {
        pdfReadersList.add(new PdfReader(report.report().toByteArray()));
      }
      finalCopy = new PdfCopyFields(outputStream);
      finalCopy.open();
      for (PdfReader pdfReader : pdfReadersList) {
        finalCopy.addDocument(pdfReader);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      finalCopy.close();
    }
    return outputStream;
  }
    
  
}