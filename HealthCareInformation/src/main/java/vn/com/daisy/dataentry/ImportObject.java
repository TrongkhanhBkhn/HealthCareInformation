package vn.com.daisy.dataentry;

import java.util.HashMap;
import java.util.Map;


public class ImportObject {
	public String organisation;
	public Map<String, String> dataelement;
	
	public ImportObject() {
		super();
	}
	public ImportObject(String organisation, Map<String, String> map) {
		this.organisation = organisation;
		this.dataelement = map;
	}
	public Map<String, String> getDataelement() {
		return dataelement;
	}
	public void setDataelement(Map<String, String> dataelement) {
		this.dataelement = dataelement;
	}
	public void setDataelement(HashMap<String, String> dataelement) {
		this.dataelement = dataelement;
	}
	
	
	public String getOrganisation() {
		return organisation;
	}
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	
		
}
