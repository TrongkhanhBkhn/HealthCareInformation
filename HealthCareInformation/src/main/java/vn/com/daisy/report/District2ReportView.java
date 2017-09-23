package vn.com.daisy.report;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
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
public class District2ReportView extends ReportView {

	private DistrictReport2Service service;
	private int[] orgGroupId = new int[] { 2, 1 };
	private int[] dataelementId = new int[] { 72, 76, 77, 75 };
	private ExternalContext ec;

	@PostConstruct
	public void init() {
		int orgId = 0;
		int period = 0;
		ec = FacesContext.getCurrentInstance().getExternalContext();
		nameReport = (String) ec.getFlash().get("reportType");
		periodType = (String) ec.getFlash().get("periodType");
		orgId = (Integer) ec.getFlash().get("orgId");
		nameReport += " " + periodType.toUpperCase();
		try {
			service = new DistrictReport2Service();
			System.out.println("PeriodType" + periodType);
			period = perDAO.getPeriodTypeID(periodType);
			System.out.println("PeriodTypeID" + period);
			period = perDAO.getPeriodIdFromPeriodTypeID(period);
			System.out.println("PeriodID" + period);
			reports = service.createData(orgId, orgGroupId, dataelementId, period);
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
		int org_id = 0;
		String name = null;
		DataValueDAO dataDAO = new DataValueDAO();
		List<String> childrenNames = new ArrayList<>();
		List<Integer> categorys = new ArrayList<>();
		childrenNames = orgDAO.getChildrenFormParentID(orgId, 2);
		org_id = orgDAO.getOrgIdFromName(childrenNames.get(0), orgId);
		categorys = dataDAO.getCategoryOptionId(72, org_id, periodId);
		for (int i = 0; i < categorys.size(); i++) {
			name = deDAO.getNameCategoryOptionFromId(categorys.get(i));
			columns.add(new ColumnModel(name.toUpperCase(), name));
		}
		name = "Số trạm y tế xã/phường";
		columns.add(new ColumnModel(name.toUpperCase(), name));
		name = "Trạm Y tế có bác sỹ làm việc";
		columns.add(new ColumnModel(name.toUpperCase(), name));
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

	public DistrictReport2Service getService() {
		return service;
	}

	public void setService(DistrictReport2Service service) {
		this.service = service;
	}

	public List<ReportObject> getReports() {
		return reports;
	}

	public void setReports(List<ReportObject> reports) {
		this.reports = reports;
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
		document = new Document(PageSize.A4, 50, 50, 50, 50);
		Document pdf = (Document) document;
		// pdf.open();
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
