package vn.com.daisy.general;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;

import vn.com.daisy.login.SessionBean;
import vn.com.daisy.organisation.OrganisationUnitDAO;
import vn.com.daisy.user.UserDAO;

@ManagedBean(name = "accessService", eager = true)
@ViewScoped
public class AccessService {
	private int orgLevel = 0;

	private int orgId = 0;
	private UserDAO userDAO = new UserDAO();
	private OrganisationUnitDAO orgDAO = new OrganisationUnitDAO();
	private String userLogin = null;
	private String emtydata = null;

	@PostConstruct
	public void init() {

		int userId = 0;
		Cookie[] cookieArr = SessionBean.getRequest().getCookies();
		if (cookieArr != null && cookieArr.length > 0) {
			for (Cookie cookie : cookieArr) {
				String cName = cookie.getName();
				if (cName.equals("cUserID"))
					userLogin = cookie.getValue();
			}
		}
		emtydata = "Không có dữ liệu trong database. Vui lòng nhập liệu";
		userId = userDAO.getUserIdFromUserName(userLogin);
		System.out.println("khanh" + userLogin + userId);
		if (userId != 0) {
			orgId = userDAO.getOrgIdFromUserId(userId);
			System.out.println("khanh1" + orgId);
			orgLevel = orgDAO.getLevelFormOrgId(orgId);
			System.out.println("khanh2" + orgLevel);
		}
	}

	public String DataEntryService() {
		String result = null;
		if (orgLevel == 2 || orgLevel == 3) {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.getFlash().put("orgId", orgId);
			ec.getFlash().put("orgLevel", orgLevel);
			ec.getFlash().put("status", false);

			result = "dataentryform/optioninfor.xhtml";
		} else if (orgLevel == 1) {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.getFlash().put("orgId", orgId);
			result = "dataentryform/nationaloption.xhtml";
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Chỉ dành cho User",
					"Bạn không đủ quyền truy cập");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			result = "";
		}
		return result;
	}

	public String btnChangeStatus() {
		String result = null;
		if (orgLevel == 0) {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.getFlash().put("orgId", 1);
			result = "lockaccount.xhtml";
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Chỉ dành cho Admin",
					"Bạn không đủ quyền truy cập");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			result = "";
		}
		return result;
	}

	public String ReportService() {
		String result = null;
		if (orgLevel == 3) {
			result = "report/reportoptiondistrict.xhtml";
		}
		if (orgLevel == 2) {
			result = "report/reportoptionprovice.xhtml";
		}
		if (orgLevel == 1) {
			result = "report/reportnationaloption.xhtml";
		}
		return result;
	}

	public String createUser() {
		String result = null;
		if (orgLevel == 0) {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.getFlash().put("orgId", 1);
			result = "user.xhtml";
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Chỉ dành cho Admin",
					"Bạn không đủ quyền truy cập");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			result = "";
		}
		return result;
	}

	public String btnBackClick() {
		return "home.xhtml" + "?faces-redirect=true";
	}

	public String btnChangePass() {
		return "changepassword.xhtml" + "?faces-redirect=true";
	}

	public String getEmtydata() {
		return emtydata;
	}

	public void setEmtydata(String emtydata) {
		this.emtydata = emtydata;
	}

}
