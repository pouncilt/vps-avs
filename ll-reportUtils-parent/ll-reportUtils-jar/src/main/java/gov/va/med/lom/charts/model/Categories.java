package gov.va.med.lom.charts.model;

import java.util.List;
import java.util.ArrayList;

public class Categories {
  
  private String font;
  private Integer fontSize;
  private String fontColor;
  private String verticalLineColor;
  private Integer verticalLineAlpha;
  private List<Category> category;
  
  public Categories() {
    category = new ArrayList<Category>();
  }
  
  public Categories(String font, Integer fontSize, String fontColor) {
    this();
    this.font = font;
    this.fontSize = fontSize;
    this.fontColor = fontColor;
  }

  public String getFont() {
    return font;
  }

  public void setFont(String font) {
    this.font = font;
  }

  public Integer getFontSize() {
    return fontSize;
  }

  public void setFontSize(Integer fontSize) {
    this.fontSize = fontSize;
  }

  public String getFontColor() {
    return fontColor;
  }

  public void setFontColor(String fontColor) {
    this.fontColor = fontColor;
  }

  public List<Category> getCategory() {
    return category;
  }

  public void setCategory(List<Category> category) {
    this.category = category;
  }


  public String getVerticalLineColor() {
    return verticalLineColor;
  }

  public void setVerticalLineColor(String verticalLineColor) {
    this.verticalLineColor = verticalLineColor;
  }

  public Integer getVerticalLineAlpha() {
    return verticalLineAlpha;
  }

  public void setVerticalLineAlpha(Integer verticalLineAlpha) {
    this.verticalLineAlpha = verticalLineAlpha;
  }
  
}
