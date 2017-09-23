package vn.com.daisy.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;

import vn.com.daisy.form.FormDAO;
import vn.com.daisy.login.SessionBean;
@ManagedBean(name="reportOptionProvince")
public class ReportOptionProvince extends ReportOption{
	
	@PostConstruct
	public void init() {	
		List<String> reports = new ArrayList<>();
		List<String> orgNames = new ArrayList<>();
		int userId = 0;
		
		String name = null;
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
		for(String orgName :orgNames){
			name = tranDAO.getTranslation("organisation", "vi_VN", orgName);
			if(name  == null)
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
		System.out.println("KHANH" + orgId);
	}
	public void btnBackClick(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/faces/home.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	public String btnOKClick() {
		FormDAO formDAO = new FormDAO();
		String result = "";	
		String orgName = null;
		int org_id = 0;
		if (reportType != "" && reportType.equals("Loại báo cáo") == false) {
			System.out.println(org);
			if((reportType.equals("BÁO CÁO TÌNH HÌNH HOẠT ĐỘNG KHÁM CHỮA BỆNH TOÀN TỈNH") == true) ||(reportType.equals("BÁO CÁO TỔNG HỢP CỦA TOÀN TỈNH") == true)){
				org_id  = orgId;	
				orgName = orgDAO.getNameOrgFromId(org_id);
				System.out.println("ORG" + orgId);
			}else{				
				org_id = orgDAO.getOrgIdFromName(org, orgId);
				orgName = org;
			}
		
			result = formDAO.getPathFromName(reportType);
			reportType +=" - " + orgName;
			reportType = reportType.toUpperCase();
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.getFlash().put("reportType", reportType);
			ec.getFlash().put("periodType",periodType);
			ec.getFlash().put("orgId",org_id);	
			
			result = result + "?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Xin mời chọn lại loaị báo cáo"));
			result = "";
		}
		return result;
	}
	public void onChanged() {
		if(reportType.equals("BÁO CÁO TỔNG HỢP CỦA TOÀN TỈNH") ==true || reportType.equals("BÁO CÁO TÌNH HÌNH HOẠT ĐỘNG KHÁM CHỮA BỆNH TOÀN TỈNH") == true)
			status = true;
		else
			status = false;

	}
	
	
}
