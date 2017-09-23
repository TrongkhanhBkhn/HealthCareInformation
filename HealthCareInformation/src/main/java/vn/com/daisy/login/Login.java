package vn.com.daisy.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;


import vn.com.daisy.user.UserDAO;
@ManagedBean(name = "login", eager = true)

@SessionScoped
public class Login implements Serializable {
	/**
	* 
	*/
	public String userName = null;
	public String password = null;
	private String virtualCheck = "false";

	public boolean checkBox = false;
	private static final long serialVersionUID = 1L;

	public Login() {
	
		isCheckBox();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void isCheckBox() {
		Cookie[] cookieArr = SessionBean.getRequest().getCookies();
		if (cookieArr != null && cookieArr.length > 0) {
			for (int i = 0; i < cookieArr.length; i++) {
				String cName = cookieArr[i].getName();
				String cValue = cookieArr[i].getValue();
				if (cName.equals("cUserID")) {
					setUserName(cValue);
				} else if (cName.equals("sPassword")) {
					setPassword(cValue);
				} else if (cName.equals("cVirtualCheck")) {
					setVirtualCheck(cValue);
					if (getVirtualCheck().equals("false")) {
						setCheckBox(false);
						setUserName(null);
						setPassword(null);
					} else {
						setCheckBox(true);
					}
				}

			}
		}
	}

	public boolean getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}

	public String getVirtualCheck() {
		return virtualCheck;
	}

	public void setVirtualCheck(String virtualCheck) {
		this.virtualCheck = virtualCheck;
	}

	/**
	 * Function: public String validateUsernamePassword() Check UserName and
	 * Password have valid
	 * 
	 * @return
	 * @throws IOException
	 */
	public String validateUsernamePassword() throws IOException {
		boolean isValid = false;
		boolean status = false;
		 UserDAO userDAO = new UserDAO();
		 status = userDAO.getStatusUser(userName);
		if(userDAO.getUsers(userName, password) !=null && !status)
			isValid = true;
		else
			isValid = false;		
		if (isValid) {
			if (checkBox == true) {
				virtualCheck = "true";
				HttpSession session = SessionBean.getSession();
				session.setAttribute("sUserID", userName);
				session.setMaxInactiveInterval(10 * 60);
				Cookie cUserId = new Cookie("cUserID", userName);
				cUserId.setMaxAge(120 * 60);
				Cookie cPassword = new Cookie("cPasswordID", password);
				cPassword.setMaxAge(120 * 60);
				Cookie cVirtualCheck = new Cookie("cVirtualCheck", virtualCheck);
				cVirtualCheck.setMaxAge(120 * 60);

				SessionBean.getResponse().addCookie(cUserId);
				SessionBean.getResponse().addCookie(cPassword);
				SessionBean.getResponse().addCookie(cVirtualCheck);
			} else {
				virtualCheck = "false";
				HttpSession session = SessionBean.getSession();
				session.setAttribute("user", userName);
				session.setMaxInactiveInterval(120 * 60);
				Cookie cVirtualCheck = new Cookie("cVirtualCheck", virtualCheck);
				cVirtualCheck.setMaxAge(120 * 60);
				SessionBean.getResponse().addCookie(cVirtualCheck);

			}
			return "home";
		} else {
			SessionBean.getResponse().setContentType("text/html");
			PrintWriter out = SessionBean.getResponse().getWriter();
			out.println("<font color=red>Tài khoản này không tồn tại hoặc mật khẩu, tài khoản không đúng</font> ");

			return "login";
		}

	}

	public String exit() {
		Cookie[] cookieArr = SessionBean.getRequest().getCookies();
		Cookie cUser = null, cPassword = null, cVitualCheck = null;
		int count = 0;
		if (cookieArr != null && cookieArr.length > 0) {
			for (Cookie cookie : cookieArr) {
				String cName = cookie.getName();
				if (cName.equals("cUserID")) {
					cUser = cookie;
					count++;
				} else if (cName.equals("sPassword")) {
					cPassword = cookie;
					count++;
				} else if (cName.equals("cVirtualCheck")) {
					cVitualCheck = cookie;
					count++;
				}
				if (count == 3){
					count =0;
					break;
				}
			}

		}
		if (cUser != null && cPassword != null && cVitualCheck != null) {			
			cUser.setMaxAge(0);
			cPassword.setMaxAge(0);
			cVitualCheck.setMaxAge(0);
			SessionBean.getResponse().addCookie(cUser);
			SessionBean.getResponse().addCookie(cPassword);
			SessionBean.getResponse().addCookie(cVitualCheck);
		}if(cVitualCheck !=null){
			cVitualCheck.setMaxAge(0);
			SessionBean.getResponse().addCookie(cVitualCheck);
		}
		
		HttpSession session = SessionBean.getSession();
		session.removeAttribute("sUserID");
		if (session != null)
			session.invalidate();
		setCheckBox(false);
		setUserName(null);
		setPassword(null);
		System.out.println("Logout");
		return "logout";

	}
}
