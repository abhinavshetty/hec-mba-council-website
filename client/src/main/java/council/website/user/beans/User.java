package council.website.user.beans;

import java.io.Serializable;

/**
 * Contains user specific data for application processing
 * @author Abhinav Shetty
 * @version 1.0
 */
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2481110791459441126L;
	
	protected String userName;
	protected String userEmail;
	protected String userRole;
	protected String userClub;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getUserClub() {
		return userClub;
	}
	public void setUserClub(String userClub) {
		this.userClub = userClub;
	}
}
