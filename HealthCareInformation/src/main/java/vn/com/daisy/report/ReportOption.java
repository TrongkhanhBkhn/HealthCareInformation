package vn.com.daisy.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.com.daisy.Period.PeriodDAO;
import vn.com.daisy.organisation.OrganisationUnitDAO;
import vn.com.daisy.translation.TranslationDAO;
import vn.com.daisy.user.UserDAO;

public class ReportOption {

	public String reportType;
	public HashMap<String, String> reportTypes = new HashMap<String, String>();

	public String org;
	public HashMap<String, String> orgs = new HashMap<String, String>();

	public String periodType;
	public HashMap<String, String> periodTypes = new HashMap<String, String>();
	int orgId = 0;
	public OrganisationUnitDAO orgDAO = new OrganisationUnitDAO();
	public TranslationDAO tranDAO = new TranslationDAO();
	public UserDAO userDAO = new UserDAO();
	public PeriodDAO perDAO = new PeriodDAO();
	public boolean status = false;
	public List<Object> objs = new ArrayList<>();
	public String name = null;
	public ReportOption() {

	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public HashMap<String, String> getReportTypes() {
		return reportTypes;
	}

	public void setReportTypes(HashMap<String, String> reportTypes) {
		this.reportTypes = reportTypes;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public HashMap<String, String> getOrgs() {
		return orgs;
	}

	public void setOrgs(HashMap<String, String> orgs) {
		this.orgs = orgs;
	}

	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	public HashMap<String, String> getPeriodTypes() {
		return periodTypes;
	}

	public void setPeriodTypes(HashMap<String, String> periodTypes) {
		this.periodTypes = periodTypes;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
