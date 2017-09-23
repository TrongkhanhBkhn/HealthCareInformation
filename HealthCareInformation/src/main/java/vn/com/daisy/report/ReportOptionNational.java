package vn.com.daisy.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;

import vn.com.daisy.form.FormDAO;
import vn.com.daisy.login.SessionBean;

@ManagedBean(name = "reportOptionNational")
public class ReportOptionNational extends ReportOption {

	public String district;
	public HashMap<String, String> districts = new HashMap<String, String>();

	public String orgName;
	List<String> reports = new ArrayList<>();
	List<String> orgNames = new ArrayList<>();
	int userId = 0;

	@PostConstruct
	public void init() {

		Cookie[] cookieArr = SessionBean.getRequest().getCookies();
		if (cookieArr != null && cookieArr.length > 0) {
			for (Cookie cookie : cookieArr) {
				String cName = cookie.getName();
				if (cName.equals("cUserID"))
					name = cookie.getValue();
			}
		}
		userId = userDAO.getUserIdFromUserName(name);
		orgId = userDAO.getOrgIdFromUserId(userId);
		orgNames = orgDAO.getAllChildrenFormParentID(orgId);
		System.out.println(orgNames.toString());
		for (String orgName : orgNames) {
			name = tranDAO.getTranslation("organisation", "vi_VN", orgName);
			if (name == null)
				name = orgName;
			System.out.println(name);
			orgs.put(name, name);
		}
		reports = tranDAO.getAllObjectValue("report", "vi_VN");
		if (reports == null) {

		} else {
			for (String report : reports)
				reportTypes.put(report, report);
		}
		reportTypes.put("BÁO CÁO TỔNG HỢP CHỈ SỐ TUYẾN TRUNG ƯƠNG", "BÁO CÁO TỔNG HỢP CHỈ SỐ TUYẾN TRUNG ƯƠNG");
		
		objs = perDAO.getAllNamePeriodType();
		if(objs !=null){
			for(Object obj:objs){
				name = tranDAO.getTranslation("periodname", "vi_VN", obj.toString());
				System.out.println(obj.toString());
				if(name ==null)
					name = obj.toString();
				periodTypes.put(name, name);
			}
		}else
		{
			periodTypes.put("","");
		}
	}

	public void subjectSelectionChanged() {
		System.out.println("Khanh" + orgId);
		System.out.println("Khanh" + org);
		orgId = orgDAO.getOrgIdFromName(org, orgId);
		orgNames = orgDAO.getAllChildrenFormParentID(orgId);
		System.out.println(orgNames.toString());
		for (String orgName : orgNames) {
			name = tranDAO.getTranslation("organisation", "vi_VN", orgName);
			if (name == null)
				name = orgName;
			districts.put(name, name);
		}
	}

	public void onChanged() {
		orgNames.clear();
		if (reportType.equals("BÁO CÁO TỔNG HỢP CỦA TOÀN TỈNH") == true
				|| reportType.equals("BÁO CÁO TÌNH HÌNH HOẠT ĐỘNG KHÁM CHỮA BỆNH TOÀN TỈNH") == true) {
			status = true;
		} else {
			status = false;
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
		FormDAO formDAO = new FormDAO();
		String result = "";
		String nameOrg = null;
		int org_id = 0;
		if (reportType != "" && reportType.equals("Loại báo cáo") == false) {
			System.out.println(org);
			if (reportType.equals("BÁO CÁO TỔNG HỢP CHỈ SỐ TUYẾN TRUNG ƯƠNG") == true) {
				org_id = 1;
				//nameOrg = "TRUNG ƯƠNG";
				result = formDAO.getPathFromName(reportType);
				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				ec.getFlash().put("reportType", reportType);
				ec.getFlash().put("orgId", org_id);
				result = result + "?faces-redirect=true";
				return result;
			}
			if ((reportType.equals("BÁO CÁO TÌNH HÌNH HOẠT ĐỘNG KHÁM CHỮA BỆNH TOÀN TỈNH") == true)
					|| (reportType.equals("BÁO CÁO TỔNG HỢP CỦA TOÀN TỈNH") == true)) {
				orgId = orgDAO.getOrgIdFromName(org, orgId);
				org_id = orgId;
				nameOrg = org;
			} else {
				orgId = orgDAO.getOrgIdFromName(org, orgId);
				org_id = orgDAO.getOrgIdFromName(district, orgId);
				nameOrg = district;
			}

			result = formDAO.getPathFromName(reportType);
			reportType += " - " + nameOrg;
			reportType = reportType.toUpperCase();
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.getFlash().put("reportType", reportType);
			ec.getFlash().put("periodType", periodType);
			ec.getFlash().put("orgId", org_id);

			result = result + "?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Xin mời chọn lại loaị báo cáo"));
			result = "";
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
