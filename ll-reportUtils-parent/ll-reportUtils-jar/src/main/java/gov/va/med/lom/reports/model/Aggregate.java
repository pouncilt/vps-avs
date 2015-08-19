package gov.va.med.lom.reports.model;

public class Aggregate {

  private String id;
	private String name;
	private String total;
	
	public Aggregate() {
	}
	
	public Aggregate(String id, String name, String total) {
	  this.id = id;
	  this.name = name;
	  this.total = total;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTotal() {
		return total;
	}
	
	public void setTotal(String total) {
		this.total = total;
	}

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
	
}
