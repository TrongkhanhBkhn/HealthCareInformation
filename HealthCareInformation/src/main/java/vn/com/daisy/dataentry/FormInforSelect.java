package vn.com.daisy.dataentry;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@ManagedBean(name ="formOption", eager=true)
public class FormInforSelect {
	private String formType;
	private HashMap<String, String>  formTypes = new HashMap<String,String>();

	
	@PostConstruct
	public void init(){
		
	}
	public String btnOKClick(){
		String result = "";
		
		return result;
	}
	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public HashMap<String, String> getFormTypes() {
		return formTypes;
	}

	public void setFormTypes(HashMap<String, String> formTypes) {
		this.formTypes = formTypes;
	}

	
	

}
