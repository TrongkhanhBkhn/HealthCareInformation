package vn.com.daisy.dataentry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import vn.com.daisy.report.ReportOption;

@ManagedBean
@ViewScoped
public class NationalOption extends ReportOption {
	private String district;
	private HashMap<String, String> districts = new HashMap<>();
	private int orgId;
	private List<String> arrChildren = new ArrayList<>();

	@PostConstruct
	public void init() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		orgId = (Integer) ec.getFlash().get("orgId");
		arrChildren = orgDAO.getAllChildrenFormParentID(orgId);
		for (String children : arrChildren)
			orgs.put(children, children);
	}

	public void onChanged() {
		int org_id = 0;
		org_id = orgDAO.getOrgIdFromName(org, orgId);
		arrChildren = orgDAO.getAllChildrenFormParentID(org_id);
		for (String children : arrChildren)
			districts.put(children, children);
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
		String result = null;
		int org_id = 0;
		int level = 0;
		org_id = orgDAO.getOrgIdFromName(org, orgId);

		if (org.equals("Lựa chọn") == false && org != "") {
			if (district.equals("Lựa chọn") == false && district != "") {

				System.out.println(org_id + district);
				try {
					org_id = orgDAO.getOrgIdFromName(district, org_id);
					level = orgDAO.getLevelFormOrgId(org_id);
				} catch (Exception ex) {

				}

				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				ec.getFlash().put("orgId", org_id);
				ec.getFlash().put("orgLevel", level);
				ec.getFlash().put("status", true);

			} else {
				try {
					level = orgDAO.getLevelFormOrgId(org_id);
				} catch (Exception ex) {

				}
				System.out.println(org_id + "  " + level);
				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				ec.getFlash().put("orgId", org_id);
				ec.getFlash().put("orgLevel", level);
				ec.getFlash().put("status", true);
			}
			result = "optioninfor.xhtml";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Xin mời chọn lại"));
			result = null;
		}
		return result;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public HashMap<String, String> getDistricts() {
		return districts;
	}

	public void setDistricts(HashMap<String, String> districts) {
		this.districts = districts;
	}

}
