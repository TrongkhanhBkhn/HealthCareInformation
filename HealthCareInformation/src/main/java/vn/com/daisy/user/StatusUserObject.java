package vn.com.daisy.user;

public class StatusUserObject {
	private String org;
	private String user;
	private boolean status;
	
	public StatusUserObject(String org, String user, boolean status) {
		super();
		this.org = org;
		this.user = user;
		this.status = status;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
