package vn.com.daisy.report;

import java.util.List;

import vn.com.daisy.Period.PeriodDAO;
import vn.com.daisy.dataelement.DataelementDAO;
import vn.com.daisy.dataentry.ColumnGroupModel;
import vn.com.daisy.dataentry.ColumnModel;
import vn.com.daisy.organisation.OrganisationUnitDAO;
import vn.com.daisy.translation.TranslationDAO;

public class ReportView {
	public List<ColumnModel> columns;
	public List<ReportObject> reports;

	public List<ColumnModel> columns1;
	
	public List<ReportObject> reports1;
	public List<ColumnGroupModel> columnGroupColsSpan;

	public List<ColumnGroupModel> columnGroupRowsSpan;
	public String nameReport;
	public DataelementDAO deDAO = new DataelementDAO();
	public PeriodDAO perDAO = new PeriodDAO();

	public OrganisationUnitDAO orgDAO = new OrganisationUnitDAO();
	public TranslationDAO tranDAO = new TranslationDAO();
	public String periodType;
	public ReportView(){
		
	}
	public List<ColumnModel> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}
	public List<ReportObject> getReports() {
		return reports;
	}
	public void setReports(List<ReportObject> reports) {
		this.reports = reports;
	}
	public String getNameReport() {
		return nameReport;
	}
	public void setNameReport(String nameReport) {
		this.nameReport = nameReport;
	}
	public DataelementDAO getDeDAO() {
		return deDAO;
	}
	public void setDeDAO(DataelementDAO deDAO) {
		this.deDAO = deDAO;
	}
	public PeriodDAO getPerDAO() {
		return perDAO;
	}
	public void setPerDAO(PeriodDAO perDAO) {
		this.perDAO = perDAO;
	}
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	public List<ColumnModel> getColumns1() {
		return columns1;
	}
	public void setColumns1(List<ColumnModel> columns1) {
		this.columns1 = columns1;
	}
	public List<ReportObject> getReports1() {
		return reports1;
	}
	public void setReports1(List<ReportObject> reports1) {
		this.reports1 = reports1;
	}
	public OrganisationUnitDAO getOrgDAO() {
		return orgDAO;
	}
	public void setOrgDAO(OrganisationUnitDAO orgDAO) {
		this.orgDAO = orgDAO;
	}
	public TranslationDAO getTranDAO() {
		return tranDAO;
	}
	public void setTranDAO(TranslationDAO tranDAO) {
		this.tranDAO = tranDAO;
	}
	public List<ColumnGroupModel> getColumnGroupColsSpan() {
		return columnGroupColsSpan;
	}
	public void setColumnGroupColsSpan(List<ColumnGroupModel> columnGroupColsSpan) {
		this.columnGroupColsSpan = columnGroupColsSpan;
	}
	public List<ColumnGroupModel> getColumnGroupRowsSpan() {
		return columnGroupRowsSpan;
	}
	public void setColumnGroupRowsSpan(List<ColumnGroupModel> columnGroupRowsSpan) {
		this.columnGroupRowsSpan = columnGroupRowsSpan;
	}
	
}
