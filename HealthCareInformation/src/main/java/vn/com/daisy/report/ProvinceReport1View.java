package vn.com.daisy.report;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

@ManagedBean
public class ProvinceReport1View extends ReportView {
	@ManagedProperty("#{provinceReport1Service}")
	private ProvinceReport1Service service;
	private int orgGroupId = 1;
	private int dataelementId = 78;
	private List<Integer> childrenIds = new ArrayList<>();

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
			period = perDAO.getPeriodTypeID(periodType);
			period = perDAO.getPeriodIdFromPeriodTypeID(period);

			service = new ProvinceReport1Service();
			reports = service.createData(orgId, dataelementId, period, orgGroupId);
			reports1 = service.createData1(orgId, 71, period, 2);
			childrenIds = orgDAO.getAllChildrenIdFormParentID(orgId);
			createColumns();
			createColumns1(71, 2, childrenIds.get(0));
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
		List<Integer> categorys = new ArrayList<>();
		categorys = deDAO.getAllCategoryOptionIdFromId(dataelementId);
		String name = null, nameOld = null;
		columns = new ArrayList<>();
		columns.add(new ColumnModel("Tổng số", "Tổng số"));
		if (categorys.toString() != null) {
			for (int id : categorys) {
				nameOld = deDAO.getNameCategoryOptionFromId(id);

				name = tranDAO.getTranslation("categoryoption", "vi_VN", nameOld);
				if (name != null)
					nameOld = name;
				columns.add(new ColumnModel(nameOld, nameOld));
			}
		}
	}

	public void btnBackClick() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext()
					.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
							+ "/faces/home.xhtml");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public void createColumns1(int deId, int orgGId, int orgId) {
		List<Integer> categorys = new ArrayList<>();

		String name = null;
		columns1 = new ArrayList<>();
		childrenIds = orgDAO.getChildrenIdFormParentID(orgId, orgGId);
		categorys = deDAO.getAllCategoryOptionIdFromId(deId);
		columns1.add(new ColumnModel("Tổng số", "Tổng số"));
		columns1.add(new ColumnModel("Số lượt khám dự phòng", "Số lượt khám dự phòng"));

		if (childrenIds != null) {
			System.out.println("CHI" + childrenIds.toString());
			for (int id : childrenIds) {
				name = orgDAO.getNameOrgFromId(id);
				for (int i = 0; i < categorys.size(); i++) {
					name += String.valueOf(i);
					System.out.println("Name2" + name);
					columns1.add(new ColumnModel(name, name));
				}
			}
		}
		categorys = deDAO.getAllCategoryOptionIdFromId(deId);
	}

	public ProvinceReport1Service getService() {
		return service;
	}

	public void setService(ProvinceReport1Service service) {
		this.service = service;
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
