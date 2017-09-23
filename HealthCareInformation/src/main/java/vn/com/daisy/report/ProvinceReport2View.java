package vn.com.daisy.report;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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

@ManagedBean(name = "provinceReport2View")
public class ProvinceReport2View extends District2ReportView {
	@ManagedProperty("#{provinceReport2Service}")
	private ProvinceReport2Service pservice;
	private int[] dataelementIds = new int[] { 73, 74, 79, 75, 77, 76 };

	@PostConstruct
	public void init() {
		int orgId = 0;
		int period = 0;
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		nameReport = (String) ec.getFlash().get("reportType");
		periodType = (String) ec.getFlash().get("periodType");
		nameReport += " " + periodType.toUpperCase();
		orgId = (Integer) ec.getFlash().get("orgId");
		try {
			System.out.println("IDD" + orgId + "");
			period = perDAO.getPeriodTypeID(periodType);
			period = perDAO.getPeriodIdFromPeriodTypeID(period);
			System.out.println("period" + period);
			reports = pservice.createData(orgId, dataelementIds, period);
			createColumns();
		} catch (Exception ex) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Chưa đủ dữ liệu, mời nhập liệu"));
			context.getExternalContext().getFlash().setKeepMessages(true);
			try {
				context.getExternalContext().redirect(
						FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/emptydata.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void createColumns() {
		columns = new ArrayList<>();
		columns.add(new ColumnModel(periodType, periodType));
	}


	public ProvinceReport2Service getPservice() {
		return pservice;
	}

	public void setPservice(ProvinceReport2Service pservice) {
		this.pservice = pservice;
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
		pdf.open();
		pdf.add(new Paragraph("BYT",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.ITALIC, new Color(0, 0, 0))));

	}

}
