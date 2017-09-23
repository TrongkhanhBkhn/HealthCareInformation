package vn.com.daisy.user;


public class UserObject {
	private String name;
	private String phoneNum;
	private String email;
	private String username;
	private String password;
	private String rePassword;
	private int orgId;
	
	
	
	public UserObject(){
		
	}
	
	public UserObject(String name, String phoneNum, String email, String username, String password, String rePassword,
			int orgId) {
		super();
		this.name = name;
		this.phoneNum = phoneNum;
		this.email = email;
		this.username = username;
		this.password = password;
		this.rePassword = rePassword;
		this.orgId = orgId;
	}

	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRePassword() {
		return rePassword;
	}
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	
}
