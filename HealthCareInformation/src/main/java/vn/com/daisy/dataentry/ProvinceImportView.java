package vn.com.daisy.dataentry;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import vn.com.daisy.datavalue.DataValue;
import vn.com.daisy.datavalue.DataValueId;
import vn.com.daisy.general.General;
@ManagedBean(name="provinceImportView")
public class ProvinceImportView extends WardImportView{
	@ManagedProperty("#{pimportService}")
	private ProvinceImportService pservice;
	@PostConstruct
	public void init(){
		int datasetId = 0;
		String period_type_name = null;
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		dataset_name = (String) ec.getFlash().get("dataset");
		period_type_name = (String) ec.getFlash().get("period");
		orgId = (Integer)ec.getFlash().get("org");
		periodId = perDAO.getPeriodTypeID(period_type_name);
		status = (Boolean) ec.getFlash().get("status");
		if(status==true)
			statusbtn = false;
		else
			statusbtn = true;
		periodId = perDAO.getPeriodIdFromPeriodTypeID(periodId);
		datasetId = datasetDAO.getDatasetIdFromName(dataset_name);
		pservice = new ProvinceImportService();
		districts  = pservice.createDataTable(datasetId, orgId, periodId);
		dataElements_id = datasetDAO.getAllDEIdFromId(datasetId);
		if (dataElements_id.size() > 0){
			for (int i = 0; i < dataElements_id.size(); i++){
				category_options_id = deDAO.getAllCategoryOptionIdFromId(dataElements_id.get(i));
				for (int j = 0; j < category_options_id.size(); j++) {
					
					nameOld = deDAO.getNameCategoryOptionFromId(category_options_id.get(j));
					name = tranDAO.getTranslation("categoryoption", "vi_VN", nameOld);
					if(name !=null)
						nameOld = name;
					categoryOpNames.add(nameOld);
				}
			}
		}
		name = orgDAO.getNameOrgFromId(orgId);
		dataset_name +="-" +name +"-"+ period_type_name;
		dataset_name = dataset_name.toUpperCase();
		 createDynamicColumns();
	}
	public ProvinceImportService getPservice() {
		return pservice;
	}
	public void setPservice(ProvinceImportService pservice) {
		this.pservice = pservice;
	}
	@Override
	public void createDynamicColumns() {
		// TODO Auto-generated method stub
		super.createDynamicColumns();
	}
	
	@Override
	public void btnSaveClick(){
		System.out.println(districts.get(0).getDataelement().toString());
		int dataelementId = 0;
		int categoryOptionId = 0;
		int org_id = 0;
		Map<String, String> map = new HashMap<>();
		for (ImportObject district : districts) {
			nameOld = district.getOrganisation();	
			name = tranDAO.getOrginalValue("organisation", "vi_VN", nameOld);
			if(name !=null)
				nameOld = name;
			System.out.println("khanh" +nameOld);
			System.out.println("khanh" +orgId);
			org_id = orgDAO.getParentIdFromOrgId(orgId);
			org_id = orgDAO.getOrgIdFromName(nameOld, org_id);
			map = district.getDataelement();
			for (String key : map.keySet()) {
				name = tranDAO.getOrginalValue("categoryoption", "vi_VN", key);
				System.out.println("Canam" + name);
				if(name !=null)
					nameOld = name;
				else
					nameOld = key;
				categoryOptionId = deDAO.getCategoryOptionIdName(nameOld);
				System.out.println("CatoId" + categoryOptionId);			
				dataelementId = deDAO.getDEIdFromCategoryOptionId(categoryOptionId);
				System.out.println("Carote" + dataelementId);
				dvDAO.addDataValue(new DataValue(new DataValueId(dataelementId, periodId, org_id, categoryOptionId),
						map.get(key).toString(), General.getCurrentDate(), General.getCurrentDate(), userId));
			
			
			}
		}
		
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Nhập dữ liệu thành công"));
		context.getExternalContext().getFlash().setKeepMessages(true);
		try {
			context.getExternalContext().redirect(
					FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/home.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
