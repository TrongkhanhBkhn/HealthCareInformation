package vn.com.daisy.dataentry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import vn.com.daisy.Period.PeriodDAO;
import vn.com.daisy.dataset.DatasetDAO;
import vn.com.daisy.form.FormDAO;
import vn.com.daisy.organisation.OrganisationUnitDAO;
import vn.com.daisy.translation.TranslationDAO;

@ManagedBean(name = "inforSelect", eager = true)
@ViewScoped
public class InforSelect {
	private HashMap<String, String> periodTypes = new HashMap<String, String>();

	@ManagedProperty("Kỳ báo cáo")
	private String periodType;

	@ManagedProperty("Đơn vị tổ chức")
	private String orgUnitNational;

	@ManagedProperty("Đơn vị tổ chức tỉnh")
	private String orgUnitsProvince;

	@ManagedProperty("Đơn vị tổ chức huyện")
	private String orgUnitsDistrict;

	private HashMap<String, String> datasets = new HashMap<String, String>();
	@ManagedProperty("Tập Dữ Liệu")
	private String dataset;
	private List<Object> periodtype_names = new ArrayList<Object>();
	private DatasetDAO datasetDAO = new DatasetDAO();
	private int orgId = 0;
	private boolean status = true;
	private String name;

	@PostConstruct
	public void init() {
		String value = null;
		int orgLevel = 0;
		int parentId = 0;
		List<Integer> list = new ArrayList<>();
		PeriodDAO periodDAO = new PeriodDAO();
		OrganisationUnitDAO orgDAO = new OrganisationUnitDAO();
		TranslationDAO tranDAO = new TranslationDAO();

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		orgId = (Integer) ec.getFlash().get("orgId");
		orgLevel = (Integer) ec.getFlash().get("orgLevel");
		status = (boolean)ec.getFlash().get("status");
		list = datasetDAO.getDatasetIdFromOrgId(orgId);
		
		System.out.println("Status" + "  " + status);

		periodtype_names = periodDAO.getAllNamePeriodType();
		
		if (periodtype_names != null) {
			for (Object obj : periodtype_names) {
				System.out.println("PeDAO" +obj.toString());
				value = tranDAO.getTranslation("periodname", "vi_VN", obj.toString());
				if (value != null)
					periodTypes.put(value, value);
				else
					periodTypes.put(obj.toString(), obj.toString());
			}
		}else{
			periodTypes.put("", "");
		}

		for (int i = 0; i < list.size(); i++) {
			value = datasetDAO.getDatasetNameFromId(list.get(i));

			if (value != null) {
				name = value;
				value = tranDAO.getTranslation("dataset", "vi_VN", value);
				if (value != null)
					datasets.put(value, value);
				else
					datasets.put(name, name);

			}
		}
		switch (orgLevel) {
		case 2: {
			orgUnitsDistrict = "Không tồn tại";
			name = orgDAO.getNameOrgFromId(orgId);
			orgUnitsProvince = tranDAO.getTranslation("organisation", "vi_VN", name);
			if (orgUnitsProvince == null)
				orgUnitsProvince = name;
			parentId = orgDAO.getParentIdFromOrgId(orgId);
			name = orgDAO.getNameOrgFromId(parentId);
			orgUnitNational = tranDAO.getTranslation("organisation", "vi_VN", name);
			if (orgUnitNational == null)
				orgUnitNational = name;
		}
			break;
		case 3: {
			name = orgDAO.getNameOrgFromId(orgId);
			orgUnitsDistrict = orgUnitsProvince = tranDAO.getTranslation("organisation", "vi_VN", name);
			if (orgUnitsDistrict == null)
				orgUnitsDistrict = name;
			parentId = orgDAO.getParentIdFromOrgId(orgId);
			name = orgDAO.getNameOrgFromId(parentId);
			orgUnitsProvince = tranDAO.getTranslation("organisation", "vi_VN", name);
			if (orgUnitsProvince == null)
				orgUnitsProvince = name;
			parentId = orgDAO.getParentIdFromOrgId(parentId);
			name = orgDAO.getNameOrgFromId(parentId);
			orgUnitNational = tranDAO.getTranslation("organisation", "vi_VN", orgDAO.getNameOrgFromId(parentId));
			if (orgUnitNational == null)
				orgUnitNational = name;
		}
			break;
		default:
			break;
		}

	}

	public void btnBackClick() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
							+ "/faces/home.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	public String btnOKClick() {
		String result = "";
		if (periodType != "" && dataset != "" && periodType.equals("Lựa chọn") == false
				&& periodType.equals("Kỳ báo cáo") == false && dataset.equals("Lựa chọn") == false
				&& dataset.equals("Dataset") == false) {
			FormDAO formDAO = new FormDAO();
			result = formDAO.getPathFromId(datasetDAO.getFormIdFromDatasetName(dataset));
			System.out.println(dataset);
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.getFlash().put("dataset", dataset);
			ec.getFlash().put("period", periodType);
			ec.getFlash().put("org", orgId);
			if(status == true)
				ec.getFlash().put("status", false);
			else{
				ec.getFlash().put("status", true);
				System.out.println("sKhanh");
			}
			
			result = result + "?faces-redirect=true";

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Xin mời chọn lại"));
			result = null;
		}

		return result;
	}

	public HashMap<String, String> getPeriodTypes() {
		return periodTypes;
	}

	public void setPeriodTypes(HashMap<String, String> periodTypes) {
		this.periodTypes = periodTypes;
	}

	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	public String getOrgUnitNational() {
		return orgUnitNational;
	}

	public void setOrgUnitNational(String orgUnitNational) {
		this.orgUnitNational = orgUnitNational;
	}

	public String getOrgUnitsProvince() {
		return orgUnitsProvince;
	}

	public void setOrgUnitsProvince(String orgUnitsProvince) {
		this.orgUnitsProvince = orgUnitsProvince;
	}

	public String getOrgUnitsDistrict() {
		return orgUnitsDistrict;
	}

	public void setOrgUnitsDistrict(String orgUnitsDistrict) {
		this.orgUnitsDistrict = orgUnitsDistrict;
	}

	public HashMap<String, String> getDatasets() {
		return datasets;
	}

	public void setDatasets(HashMap<String, String> datasets) {
		this.datasets = datasets;
	}

	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
