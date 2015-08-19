package gov.va.med.lom.reports.model;

import java.util.List;

public class Aggregates {

	private boolean success;
	private int totalCount;
	private List<Aggregate> root;
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public List<Aggregate> getRoot() {
		return root;
	}
	
	public void setRoot(List<Aggregate> root) {
		this.root = root;
	}	
	
}
