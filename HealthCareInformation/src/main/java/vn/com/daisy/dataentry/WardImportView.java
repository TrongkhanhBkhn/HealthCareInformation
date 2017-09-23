package vn.com.daisy.dataentry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import vn.com.daisy.Period.PeriodDAO;
import vn.com.daisy.dataelement.DataelementDAO;
import vn.com.daisy.dataset.DatasetDAO;
import vn.com.daisy.datavalue.DataValue;
import vn.com.daisy.datavalue.DataValueDAO;
import vn.com.daisy.datavalue.DataValueId;
import vn.com.daisy.general.General;
import vn.com.daisy.organisation.OrganisationUnitDAO;
import vn.com.daisy.translation.TranslationDAO;
import vn.com.daisy.user.UserDAO;

@ManagedBean(name = "wardImportView", eager = true)
@ViewScoped
public class WardImportView {
	public List<ColumnModel> columns;

	public List<ImportObject> districts;

	public List<ColumnGroupModel> columnGroupColsSpan;

	public List<ColumnGroupModel> columnGroupRowsSpan;
	@ManagedProperty("#{importService}")
	public ImportService service;

	public String dataset_name;

	public DatasetDAO datasetDAO = new DatasetDAO();

	public DataelementDAO deDAO = new DataelementDAO();

	public TranslationDAO tranDAO = new TranslationDAO();

	public PeriodDAO perDAO = new PeriodDAO();

	public UserDAO userDAO = new UserDAO();
	public boolean status=true;
	public boolean statusbtn=false;

	public OrganisationUnitDAO orgDAO = new OrganisationUnitDAO();
	public DataValueDAO dvDAO = new DataValueDAO();
	public List<String> categoryOpNames = new ArrayList<>();
	public List<Integer> dataElements_id = new ArrayList<>();

	public List<Integer> category_options_id = new ArrayList<>();

	public String name = null;
	public String nameOld = null;

	public int periodId = 0;

	public int orgId = 0;

	public int userId = 0;

	@PostConstruct
	public void init() {
		int datasetId = 0;
		int orgGroupId = 0;
		int periodTypeId = 0;
		String period_type_name = null;
		columnGroupColsSpan = new ArrayList<>();
		columnGroupRowsSpan = new ArrayList<>();
		columnGroupRowsSpan.add(new ColumnGroupModel("2", "ĐƠN VỊ"));
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		dataset_name = (String) ec.getFlash().get("dataset");
		period_type_name = (String) ec.getFlash().get("period");
		orgId = (Integer) ec.getFlash().get("org");
		
		status = (Boolean) ec.getFlash().get("status");
		System.out.println("Status" + status);
		if(status==true)
			statusbtn = false;
		else
			statusbtn = true;
		
		System.out.println("Pername" + period_type_name + "  " + dataset_name);
		period_type_name = tranDAO.getOrginalValue("periodname", "vi_VN", period_type_name);
		
		System.out.println("Pername" + period_type_name);
		periodTypeId = perDAO.getPeriodTypeID(period_type_name);
		periodId = perDAO.getPeriodIdFromPeriodTypeID(periodTypeId);

		datasetId = datasetDAO.getDatasetIdFromName(dataset_name);

		orgGroupId = datasetDAO.getOrgGroupIdFromName(dataset_name);

		service = new ImportService();
		districts = service.createDataTable(datasetId, orgGroupId, orgId, periodId);

		dataElements_id = datasetDAO.getAllDEIdFromId(datasetId);
		if (dataElements_id.size() > 0) {
			for (int i = 0; i < dataElements_id.size(); i++) {
				category_options_id = deDAO.getAllCategoryOptionIdFromId(dataElements_id.get(i));
				nameOld = deDAO.getNameDEFromId(dataElements_id.get(i));
				name = tranDAO.getTranslation("dataelement", "vi_VN", nameOld);
				if (name != null)
					nameOld = name;
				System.out.println("FIX1" + nameOld +category_options_id.toString());
				columnGroupColsSpan.add(new ColumnGroupModel(String.valueOf(category_options_id.size()), nameOld));
				for (int j = 0; j < category_options_id.size(); j++) {
					nameOld = deDAO.getNameCategoryOptionFromId(category_options_id.get(j));
					name = tranDAO.getTranslation("categoryoption", "vi_VN", nameOld);
					if (name != null)
						nameOld = name;
					System.out.println("FIX" + nameOld);
					categoryOpNames.add(nameOld);
				}
			}
		}
		name = orgDAO.getNameOrgFromId(orgId);
		dataset_name +="-" +name +"-"+ period_type_name;
		dataset_name = dataset_name.toUpperCase();
		createDynamicColumns();

	}

	public void btnSaveClick() {
		System.out.println(districts.get(0).getDataelement().toString());
		int dataelementId = 0;
		int[] result;
		int i = 0;
		int categoryOptionId = 0;
		int org_id = 0;
		Map<String, String> map = new HashMap<>();
		result = new int[districts.get(0).getDataelement().size()];
		for (ImportObject district : districts) {
			nameOld = district.getOrganisation();
			name = tranDAO.getOrginalValue("organisation", "vi_VN", nameOld);
			if (name != null)
				nameOld = name;
			org_id = orgDAO.getOrgIdFromName(nameOld, orgId);
			map = district.getDataelement();
			i = 0;
			for (String key : map.keySet()) {
				name = tranDAO.getOrginalValue("categoryoption", "vi_VN", key);
				if (name != null)
					nameOld = name;
				else
					nameOld = key;
				categoryOptionId = deDAO.getCategoryOptionIdName(nameOld);
				System.out.println("CatoId" + categoryOptionId);

				dataelementId = deDAO.getDEIdFromCategoryOptionId(categoryOptionId);
				System.out.println("Carote" + dataelementId);
				dvDAO.addDataValue(new DataValue(new DataValueId(dataelementId, periodId, org_id, categoryOptionId),
						map.get(key).toString(), General.getCurrentDate(), General.getCurrentDate(), userId));
				result[i] += Integer.parseInt(map.get(key).toString());
				i++;
			}
		}
		
		
		for(i = 0;i<result.length; i++){
			System.out.println("KEYY" + result[i]);
		}
		i = 0;
		for (String key : districts.get(0).getDataelement().keySet()) {
			name = tranDAO.getOrginalValue("categoryoption", "vi_VN", key);
			if (name != null)
				nameOld = name;
			else
				nameOld = key;
			categoryOptionId = deDAO.getCategoryOptionIdName(nameOld);
			dataelementId = deDAO.getDEIdFromCategoryOptionId(categoryOptionId);
			dvDAO.addDataValue(new DataValue(new DataValueId(dataelementId, periodId, orgId, categoryOptionId),
					String.valueOf(result[i]), General.getCurrentDate(), General.getCurrentDate(), userId));
			i++;
		}
		System.out.println(districts.get(0).getDataelement().keySet());
		System.out.println(result.toString());
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Nhập dữ liệu thành công"));
		context.getExternalContext().getFlash().setKeepMessages(true);
		try {
			context.getExternalContext()
					.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
							+ "/faces/home.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void btnBackClick() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
							+ "/faces/home.xhtml");
		} catch (IOException e) {
			
			e.printStackTrace();

		}
	}

	public List<ImportObject> getDistricts() {
		return districts;
	}

	public void setDistricts(List<ImportObject> districts) {
		this.districts = districts;
	}

	public ImportService getService() {
		return service;
	}

	public void setService(ImportService service) {
		this.service = service;
	}

	public void createDynamicColumns() {
		columns = new ArrayList<ColumnModel>();
		for (String colName : categoryOpNames)
			columns.add(new ColumnModel(colName.toUpperCase(), colName));

	}

	public void onRowEdit(RowEditEvent event) {
		ImportObject map = new ImportObject();
		map = ((ImportObject) event.getObject());
		FacesMessage msg = new FacesMessage("Car Edited", map.getDataelement().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<ColumnModel> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isStatusbtn() {
		return statusbtn;
	}

	public void setStatusbtn(boolean statusbtn) {
		this.statusbtn = statusbtn;
	}

	public String getDataset_name() {
		return dataset_name;
	}

	public void setDataset_name(String dataset_name) {
		this.dataset_name = dataset_name;
	}

}
