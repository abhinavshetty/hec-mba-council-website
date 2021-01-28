package council.website.user.beans;

import java.io.Serializable;

public class UserLoginRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3967519769922865244L;
	private String userEmail;
	private String userPassword;
	private String oldUserPassword;
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getOldUserPassword() {
		return oldUserPassword;
	}
	public void setOldUserPassword(String oldUserPassword) {
		this.oldUserPassword = oldUserPassword;
	}
}
