package vn.com.daisy.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import vn.com.daisy.dataentry.ColumnGroupModel;
import vn.com.daisy.dataentry.ColumnModel;

@ManagedBean
@ViewScoped
public class NationalReportView extends ReportView {

	public List<Object> arr = new ArrayList<>();
	private int[] dataelementId = new int[] {78, 75, 72, 76, 77, 79,80,81};
	private List<Integer> periodIds_ = new ArrayList<>();
	private int[] periodIds ;
	private NationalReportService service;
	int orgId = 0;

	@PostConstruct
	public void init() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		nameReport = (String) ec.getFlash().get("reportType");
		orgId = (Integer) ec.getFlash().get("orgId");
		service = new NationalReportService();
		columnGroupColsSpan = new ArrayList<>();
		columnGroupRowsSpan = new ArrayList<>();
		columns = new ArrayList<>();
		reports = new ArrayList<>();
		
		arr = perDAO.getAllNamePeriodType();
		columnGroupRowsSpan.add(new ColumnGroupModel("2", "TÊN ĐƠN VỊ"));
		for (Object obj : arr) {
			columnGroupColsSpan.add(new ColumnGroupModel("4", obj.toString()));
		}
		
		periodIds_ = perDAO.getAllPeriodId();
		periodIds = new int[periodIds_.size()];
		for(int i=0 ;i<periodIds_.size(); i++){
			periodIds[i] = periodIds_.get(i);
			System.out.println(periodIds[i]);
		}
		columns = createDynamicColumn(arr.size());
		reports = service.createData(dataelementId, periodIds, orgId);
	}

	public List<ColumnModel> createDynamicColumn(int size) {
		List<ColumnModel> list = new ArrayList<>();
		String name = null;
		for (int i = 0; i < size; i++) {
			name = deDAO.getNameDEFromId(dataelementId[0])+String.valueOf(periodIds[i]);
			list.add(new ColumnModel(deDAO.getNameDEFromId(dataelementId[0]).toUpperCase(), name));
			list.add(new ColumnModel("TỈ LỆ TRẠM Y TẾ CÓ BÁC SỸ", "Tỉ lệ TYT xã có BS"+String.valueOf(periodIds[i])));
			list.add(
					new ColumnModel("TỈ LỆ PHỤ NỮ ĐẺ ĐƯỢC NHÂN VIÊN Y TẾ ĐỠ", "Tỉ lệ phụ nữ đẻ được nhân viê y tế đỡ" +String.valueOf(periodIds[i])));
			name = deDAO.getNameDEFromId(dataelementId[5])+String.valueOf(periodIds[i]);
			list.add(new ColumnModel(deDAO.getNameDEFromId(dataelementId[5]).toUpperCase(), name));
		}
		return list;
	}

	public NationalReportService getService() {
		return service;
	}

	public void setService(NationalReportService service) {
		this.service = service;
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
