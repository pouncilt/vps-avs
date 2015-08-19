package gov.va.med.lom.reports.model;

import java.util.ArrayList;
import java.util.List;

public class Report {
  
  private String id;
  private String name;
  private Boolean defaultReport;
  private List<SearchField> searchFields;
  private List<SearchCombo> searchCombos;
  
  public Report() {
    defaultReport = false;
    searchFields = new ArrayList<SearchField>();
    searchCombos = new ArrayList<SearchCombo>();
  }
  
  public Report(String id, String name) {
    this();
    this.id = id;
    this.name = name;
  }
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getDefaultReport() {
    return defaultReport;
  }

  public void setDefaultReport(Boolean defaultReport) {
    this.defaultReport = defaultReport;
  }

  /*
   * Search Fields
   */
  public List<SearchField> getSearchFields() {
    return searchFields;  
  }
  
  public void addSearchField(SearchField searchField) {
    searchFields.add(searchField);
  }
  
  public SearchField addSearchField(String id, String fieldLabel) {
    SearchField searchField = SearchField.createSearchField(id, fieldLabel);
    searchFields.add(searchField);
    return searchField;
  }

  public SearchField addSearchField(String id, String fieldLabel, String action) {
    SearchField searchField = SearchField.createSearchField(id, fieldLabel, action);
    searchFields.add(searchField);
    return searchField;
  }
  
  public SearchField addSearchField(String id, String fieldLabel, String action, String defaultValue) {
    SearchField searchField = SearchField.createSearchField(id, fieldLabel, action, defaultValue);
    searchFields.add(searchField);
    return searchField;
  }  
  
  /*
   * Search Combo
   */
  public SearchCombo addSearchCombo(String id, String fieldLabel) {
    SearchCombo searchCombo = SearchCombo.createSearchCombo(id, fieldLabel);
    searchCombos.add(searchCombo);
    return searchCombo;
  }
  
  public SearchCombo addSearchCombo(String id, String fieldLabel, String defaultOptionId) {
    SearchCombo searchCombo = SearchCombo.createSearchCombo(id, fieldLabel, defaultOptionId);
    searchCombos.add(searchCombo);
    return searchCombo;
  }  
  
  public SearchCombo addSearchCombo(String id, String fieldLabel, List<SearchComboOption> options) {
    SearchCombo searchCombo = SearchCombo.createSearchCombo(id, fieldLabel);
    searchCombo.setOptions(options);
    searchCombos.add(searchCombo);
    return searchCombo;
  }  
  
  public SearchCombo addSearchCombo(String id, String fieldLabel, String defaultOptionId, List<SearchComboOption> options) {
    SearchCombo searchCombo = SearchCombo.createSearchCombo(id, fieldLabel, defaultOptionId);
    searchCombo.setOptions(options);
    searchCombos.add(searchCombo);
    return searchCombo;
  }    
}
