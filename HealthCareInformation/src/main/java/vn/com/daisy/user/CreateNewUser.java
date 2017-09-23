package vn.com.daisy.user;

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

import vn.com.daisy.general.General;
import vn.com.daisy.organisation.OrganisationUnitDAO;
import vn.com.daisy.translation.TranslationDAO;

@ManagedBean(name = "createNewUser")
@ViewScoped
public class CreateNewUser {
	private String district;
	private HashMap<String, String> districts = new HashMap<>();
	private String province;
	private HashMap<String, String> provinces = new HashMap<>();
	private UserObject userObj = new UserObject();
	private OrganisationUnitDAO orgDAO = new OrganisationUnitDAO();
	private UserDAO userDAO = new UserDAO();
	private boolean status;
	private boolean status1;
	private boolean status2;
	private int orgId = 0;
	private List<String> orgNames = new ArrayList<>();
	private TranslationDAO tranDAO = new TranslationDAO();
	private String name = null;

	@PostConstruct
	public void init() {

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		orgId = (Integer) ec.getFlash().get("orgId");
		orgNames = orgDAO.getAllChildrenFormParentID(orgId);
		for (String orgName : orgNames) {
			name = tranDAO.getTranslation("organisation", "vi_VN", orgName);
			if (name != null) {
				provinces.put(name, name);
			} else {
				provinces.put(orgName, orgName);
			}
		}
	}

	public void onChanged() {
		name = tranDAO.getOrginalValue("organisation", "vi_VN", province);
		if (name != null)
			orgId = orgDAO.getOrgIdFromName(name, orgId);
		else
			orgId = orgDAO.getOrgIdFromName(province, orgId);
		orgNames = orgDAO.getAllChildrenFormParentID(orgId);
		for (String orgName : orgNames) {
			name = tranDAO.getTranslation("organisation", "vi_VN", orgName);
			if (name != null) {
				districts.put(name, name);
			} else {
				districts.put(orgName, orgName);
			}
		}
	}

	public void changeStated() {
		if (status1 == true)
			status = status2 = true;
		else
			status = status2 = false;

	}

	public void registerClick() {
		int keyId = 0;
		int id = 0;
		id = userDAO.getUserIdFromUserName(userObj.getUsername());
		if (id == 0) {
			keyId = General.createKeyId();
			User user = new User(keyId, userObj.getUsername(), userObj.getPassword(), null, null, null, null, null,
					null, null);
			userDAO.addUser(user);
			UserInfo userInfo = new UserInfo();
			if (status1 == false) {
				if (userObj.getPassword().equals(userObj.getRePassword()) == true) {
					if (status == true) {
						userInfo.setUserInfoId(keyId);
						userInfo.setOrganisationUnitId(orgId);
						userInfo.setFirstName(userObj.getName());
						userInfo.setSurName(userObj.getName());
						userDAO.addUser(userInfo);
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Đăng ký thành công"));

					} else {
						orgId = orgDAO.getOrgIdFromName(district, orgId);
						userInfo.setUserInfoId(keyId);
						userInfo.setOrganisationUnitId(orgId);
						userInfo.setFirstName(userObj.getName());
						userInfo.setSurName(userObj.getName());
						userDAO.addUser(userInfo);
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Đăng ký thành công"));
						try {
							FacesContext context = FacesContext.getCurrentInstance();
							context.addMessage(null, new FacesMessage("Đăng ký thành công"));
							context.getExternalContext().getFlash().setKeepMessages(true);
							context.getExternalContext()
									.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
											+ "/faces/home.xhtml");
						} catch (IOException e) {

							e.printStackTrace();
						}

					}

				} else {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Mật khẩu không khớp, Vui lòng nhập lại"));
				}
			} else {
				orgId = 1;
				userInfo.setUserInfoId(keyId);
				userInfo.setOrganisationUnitId(orgId);
				userInfo.setFirstName(userObj.getName());
				userInfo.setSurName(userObj.getName());
				userDAO.addUser(userInfo);
				try {
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage("Đăng ký thành công"));
					context.getExternalContext().getFlash().setKeepMessages(true);
					context.getExternalContext()
							.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
									+ "/faces/home.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}

			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tài khoản đã tồn tại"));
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public HashMap<String, String> getProvinces() {
		return provinces;
	}

	public void setProvinces(HashMap<String, String> provinces) {
		this.provinces = provinces;
	}

	public UserObject getUserObj() {
		return userObj;
	}

	public void setUserObj(UserObject userObj) {
		this.userObj = userObj;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isStatus1() {
		return status1;
	}

	public void setStatus1(boolean status1) {
		this.status1 = status1;
	}

	public boolean isStatus2() {
		return status2;
	}

	public void setStatus2(boolean status2) {
		this.status2 = status2;
	}

}
