package council.website.club.beans;

import java.io.Serializable;

public class Club implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1535035319031161526L;
	private String clubName;
	private String clubResource;
	
	public String getClubName() {
		return clubName;
	}
	public void setClubName(String clubName) {
		this.clubName = clubName;
	}
	public String getClubResource() {
		return clubResource;
	}
	public void setClubResource(String clubResource) {
		this.clubResource = clubResource;
	}
}
