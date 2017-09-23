package vn.com.daisy.user;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class LockAccount {
	private String username;
	private UserDAO userDAO = new UserDAO();
	private User user = new User();
	private UserInfo userInfo = new UserInfo();

	public void btnDelete() {
		try {
			user = userDAO.getUser(username);
			userInfo = userDAO.getUserInfor(user.getUserId());
			if (user == null || username.equals("admin")) {
				if (username.equals("admin"))
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Không được thao tác với  " + username));
				else
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Không tồn tại tài khoản " + username));
			} else {
				userDAO.deleteUserInfo(userInfo);
				userDAO.deleteUser(user);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Xóa " + username + " thành công"));
			}
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Không tồn tại tài khoản " + username));
		}

	}

	public void btnLock() {
		try {
			user = userDAO.getUser(username);
			if (user == null || username.equals("admin")) {
				if (username.equals("admin"))
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Không được thao tác với  " + username));
				else
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Không tồn tại tài khoản " + username));
			} else {
				user.setDisabled(true);
				userDAO.updateUser(user);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Khóa " + username + " thành công"));
			}
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Không tồn tại tài khoản " + username));
		}
	}

	public void btnUnlock() {
		try {
			user = userDAO.getUser(username);
			if (user == null || username.equals("admin")) {
				if (username.equals("admin"))
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Không được thao tác với " + username));
				else
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Không tồn tại tài khoản " + username));
			} else {
				user.setDisabled(false);
				userDAO.updateUser(user);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Mở khóa " + username + " thành công"));
			}
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Không tồn tại tài khoản " + username));
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
