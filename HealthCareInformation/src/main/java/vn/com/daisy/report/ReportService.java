package vn.com.daisy.report;

import java.util.ArrayList;
import java.util.List;

import vn.com.daisy.dataelement.DataelementDAO;
import vn.com.daisy.datavalue.DataValueDAO;
import vn.com.daisy.organisation.OrganisationUnitDAO;
import vn.com.daisy.translation.TranslationDAO;

public class ReportService {
	public  List<String> results = new ArrayList<>();
	public int index = 0;
	public String orgName = null;
	public String categoryName = null;
	public OrganisationUnitDAO orgDAO = new OrganisationUnitDAO();
	public DataValueDAO dataDAO = new DataValueDAO();
	public DataelementDAO deDAO = new DataelementDAO();
	public TranslationDAO tranDAO = new TranslationDAO();
	public List<Integer> childrenIds = new ArrayList<>();
	public List<Integer> childrenIds1 = new ArrayList<>();
	public List<Integer> categoryOpIds = new ArrayList<>();
	public String name =null;
	public ReportService(){
		
	}

}
