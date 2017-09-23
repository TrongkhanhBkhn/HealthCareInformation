package vn.com.daisy.report;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;

import vn.com.daisy.dataentry.ColumnModel;
import vn.com.daisy.datavalue.DataValueDAO;

@ManagedBean
@ViewScoped
public class District1ReportView extends ReportView {

	@ManagedProperty("#{districtReport1Service}")
	private DistrictReport1Service service;

	@PostConstruct
	public void init() {
		int orgId = 0;
		int period = 0;
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		nameReport = (String) ec.getFlash().get("reportType");
		periodType = (String) ec.getFlash().get("periodType");
		orgId = (Integer) ec.getFlash().get("orgId");
		try {
			System.out.println("gia tri" + periodType);
			service = new DistrictReport1Service();
			period = perDAO.getPeriodTypeID(periodType);
			period = perDAO.getPeriodIdFromPeriodTypeID(period);
			System.out.println("gia tri" + orgId + "  " + period);
			
			reports = service.createData(orgId, 2, 71, period);
			reports1 = service.createData1(orgId, 1, 78, period);
			nameReport += " " + periodType.toUpperCase();
			createColumns(orgId, period);
		} catch (Exception ex) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Chưa đủ dữ liệu, mời nhập liệu"));
			context.getExternalContext().getFlash().setKeepMessages(true);
			try {
				context.getExternalContext()
						.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
								+ "/faces/emptydata.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void createColumns(int orgId, int periodId) {
		columns = new ArrayList<ColumnModel>();
		columns1 = new ArrayList<ColumnModel>();
		List<String> childrenNames = new ArrayList<>();
		List<Integer> categorys = new ArrayList<>();
		String name = null;
		DataValueDAO dataDAO = new DataValueDAO();
		int org_id = 0;
		childrenNames = orgDAO.getChildrenFormParentID(orgId, 2);
		org_id = orgDAO.getOrgIdFromName(childrenNames.get(0), orgId);
		categorys = dataDAO.getCategoryOptionId(71, org_id, periodId);
		for (int i = 0; i < categorys.size(); i++) {
			name = deDAO.getNameCategoryOptionFromId(categorys.get(i));
			columns.add(new ColumnModel(name.toUpperCase(), name));

		}
		childrenNames = orgDAO.getChildrenFormParentID(orgId, 1);
		System.out.println(childrenNames.toString());
		org_id = orgDAO.getOrgIdFromName(childrenNames.get(0), orgId);
		System.out.println(org_id);
		categorys = dataDAO.getCategoryOptionId(78, org_id, periodId);
		System.out.println(categorys);
		columns1.add(new ColumnModel("TỔNG SỐ LƯỢT KHÁM BỆNH TUYẾN XÃ", "Tổng số lượt khám bệnh tuyến xã"));
		for (int i = 0; i < categorys.size(); i++) {
			name = deDAO.getNameCategoryOptionFromId(categorys.get(i));
			System.out.println(name);
			columns1.add(new ColumnModel(name.toUpperCase(), name));
		}

		
	}

	public List<ReportObject> getReports() {
		return reports;
	}

	public void setReports(List<ReportObject> reports) {
		this.reports = reports;
	}

	public DistrictReport1Service getService() {
		return service;
	}

	public void setService(DistrictReport1Service service) {
		this.service = service;
	}

	public List<ColumnModel> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}

	public String getNameReport() {
		return nameReport;
	}

	public void setNameReport(String nameReport) {
		this.nameReport = nameReport;
	}

	public List<ReportObject> getReports1() {
		return reports1;
	}

	public void setReports1(List<ReportObject> reports1) {
		this.reports1 = reports1;
	}

	public List<ColumnModel> getColumns1() {
		return columns1;
	}

	public void setColumns1(List<ColumnModel> columns1) {
		this.columns1 = columns1;
	}

	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		CellStyle style = wb.createCellStyle();
		style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());

		for (Row row : sheet) {
			for (Cell cell : row) {
				cell.setCellValue(cell.getStringCellValue().toUpperCase());
				cell.setCellStyle(style);
			}
		}
	}

	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;

		pdf.addTitle(nameReport);

		pdf.setPageSize(PageSize.A4.rotate());

		pdf.add(new Paragraph("BYT",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.ITALIC, new Color(0, 0, 0))));
		pdf.open();
	}

	public void btnBackClick() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			context.getExternalContext()
					.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
							+ "/faces/home.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
