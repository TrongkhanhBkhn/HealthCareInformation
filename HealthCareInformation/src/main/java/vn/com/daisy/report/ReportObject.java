package vn.com.daisy.report;

import java.util.Map;

public class ReportObject {
	private int index;
	private String organisation;
	private Map<String , String> map;
	private String note;
	
	
	public ReportObject(int index, String organisation, Map<String, String> map, String note) {
		super();
		this.index = index;
		this.organisation = organisation;
		this.map = map;
		this.note = note;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getOrganisation() {
		return organisation;
	}
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
}
