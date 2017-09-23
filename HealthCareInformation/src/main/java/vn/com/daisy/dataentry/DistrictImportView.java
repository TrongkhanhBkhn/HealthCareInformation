package vn.com.daisy.dataentry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import vn.com.daisy.datavalue.DataValue;
import vn.com.daisy.datavalue.DataValueId;
import vn.com.daisy.general.General;

@ManagedBean(name = "districtImportView", eager = true)
@ViewScoped
public class DistrictImportView extends WardImportView {

	@PostConstruct
	public void init() {
		int datasetId = 0;
		int orgGroupId = 0;
		int periodTypeId = 0;
		String period_type_name = null;

		columnGroupColsSpan = new ArrayList<>();
		columnGroupRowsSpan = new ArrayList<>();
		columnGroupRowsSpan.add(new ColumnGroupModel("2", "Đơn Vị"));
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		dataset_name = (String) ec.getFlash().get("dataset");
		period_type_name = (String) ec.getFlash().get("period");
		orgId = (Integer) ec.getFlash().get("org");
		status = (Boolean) ec.getFlash().get("status");
		if (status == true)
			statusbtn = false;
		else
			statusbtn = true;
		System.out.println("Pername" + period_type_name + "  " + dataset_name);
		name = tranDAO.getOrginalValue("periodname", "vi_VN", period_type_name);
		if (name != null)
			period_type_name = name;
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
				columnGroupColsSpan.add(new ColumnGroupModel(String.valueOf(category_options_id.size()), nameOld));
				for (int j = 0; j < category_options_id.size(); j++) {
					nameOld = deDAO.getNameCategoryOptionFromId(category_options_id.get(j));
					name = tranDAO.getTranslation("categoryoption", "vi_VN", nameOld);
					if (name != null)
						nameOld = name;
					categoryOpNames.add(nameOld);
				}
			}
		}
		name = orgDAO.getNameOrgFromId(orgId);
		dataset_name += "-" + name + "-" + period_type_name;
		dataset_name = dataset_name.toUpperCase();
		createDynamicColumns();

	}

	public void btnSaveClick() {
		System.out.println(districts.get(0).getDataelement().toString());
		int dataelementId = 0;
		int categoryOptionId = 0;
		int org_id = 0;
		int[] result;
		int i = 0;
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
				System.out.println("Canam" + name);
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

}
