package vn.com.daisy.report;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;


import vn.com.daisy.form.FormDAO;
import vn.com.daisy.login.SessionBean;
import vn.com.daisy.translation.TranslationDAO;
import vn.com.daisy.user.UserDAO;

@ManagedBean(name="reportOptionDistrict")
public class ReportOptionDistrict extends ReportOption {
	
	@PostConstruct
	public void init(){
		TranslationDAO tranDAO = new TranslationDAO();
		reportTypes.put("BÁO CÁO TÌNH HÌNH HOẠT ĐỘNG KHÁM CHỮA BỆNH TẠI TUYẾN HUYỆN, TUYẾN XÃ", "BÁO CÁO TÌNH HÌNH HOẠT ĐỘNG KHÁM CHỮA BỆNH TẠI TUYẾN HUYỆN, TUYẾN XÃ");
		reportTypes.put("BÁO CÁO TỔNG HỢP TUYẾN HUYỆN", "BÁO CÁO TỔNG HỢP TUYẾN HUYỆN");
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
		UserDAO userDAO = new UserDAO();
		int userId = 0;
		int orgId = 0;
		String result = "";
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
		name = orgDAO.getNameOrgFromId(orgId);
		if (reportType != "" && reportType.equals("Loại báo cáo") == false) {
			result = formDAO.getPathFromName(reportType);
			reportType +=" - "+ name;
			reportType = reportType.toUpperCase();
			System.out.println(result);
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.getFlash().put("reportType", reportType);
			ec.getFlash().put("periodType",periodType);
			ec.getFlash().put("orgId",orgId);
			result = result + "?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Xin mời chọn lại loaị báo cáo"));
			result = "";
		}
		return result;
	}
	
	
	
	
	
}
