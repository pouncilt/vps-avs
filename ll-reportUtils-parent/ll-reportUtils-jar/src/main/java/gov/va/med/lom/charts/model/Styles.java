package gov.va.med.lom.charts.model;

import java.util.ArrayList;
import java.util.List;

public class Styles {
  
  private List<Style> definition;
  private List<Apply> application;
  
  public Styles() {
    definition = new ArrayList<Style>();
    application = new ArrayList<Apply>();
  }
  
  public Styles(ArrayList<Style> definition, ArrayList<Apply> application) {
    this.definition = definition;
    this.application = application;
  }
  
  public List<Style> getDefinition() {
    return definition;
  }
  
  public void setDefinition(List<Style> definition) {
    this.definition = definition;
  }
  
  public List<Apply> getApplication() {
    return application;
  }
  
  public void setApplication(List<Apply> application) {
    this.application = application;
  }
  
}
