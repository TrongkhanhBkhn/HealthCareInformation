package vn.com.daisy.user;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;

import vn.com.daisy.login.SessionBean;

@ManagedBean
@ViewScoped
public class ChangePassword {
	private String old_pass;
	private String new_pass;
	private UserDAO userDAO = new UserDAO();
	private String userLogin = null;
	private int userId = 0;
	private String password;
	@PostConstruct
	public void init(){
	
		Cookie[] cookieArr = SessionBean.getRequest().getCookies();
		if (cookieArr != null && cookieArr.length > 0) {
			for (Cookie cookie : cookieArr) {
				String cName = cookie.getName();
				if (cName.equals("cUserID"))
					userLogin = cookie.getValue();
				else if(cName.equals("cPasswordID"))
					password = cookie.getValue();
				
				System.out.println("PASS" +password);
			}
		}
		userId = userDAO.getUserIdFromUserName(userLogin);
	}
	public void btnOKClick(){
		if(old_pass.equals(password) == false){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sai mật khẩu"));
		}else{
			User user = new User();
			user.setUserId(userId);
			user.setUserName(userLogin);
			user.setPassword(new_pass);
			userDAO.updateUser(user);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Thay đổi mật khẩu thành công"));
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Thay đổi thành công"));
			context.getExternalContext().getFlash().setKeepMessages(true);
			try {
				context.getExternalContext()
						.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
								+ "/faces/login.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	public String getOld_pass() {
		return old_pass;
	}
	public void setOld_pass(String old_pass) {
		this.old_pass = old_pass;
	}
	public String getNew_pass() {
		return new_pass;
	}
	public void setNew_pass(String new_pass) {
		this.new_pass = new_pass;
	}
	
}
