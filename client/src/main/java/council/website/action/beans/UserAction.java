package council.website.action.beans;

import council.website.user.beans.User;

public class UserAction extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2083482864148348660L;
	
	private String passwordHashed;
	private String publicKey;
	
	public String getPasswordHashed() {
		return passwordHashed;
	}
	public void setPasswordHashed(String passwordHashed) {
		this.passwordHashed = passwordHashed;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

}
