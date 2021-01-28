package council.website.user.beans;

import java.io.Serializable;

public class UserDataRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -669921478699856980L;
	
	private String userEmail;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}
