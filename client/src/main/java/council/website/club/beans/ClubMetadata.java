package council.website.club.beans;

import java.io.Serializable;

public class ClubMetadata implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7955406945990216393L;
	private String clubName;
	private String metadataName;
	private String metadataDetail;
	public String getMetadataName() {
		return metadataName;
	}
	public void setMetadataName(String metadataName) {
		this.metadataName = metadataName;
	}
	public String getMetadataDetail() {
		return metadataDetail;
	}
	public void setMetadataDetail(String metadataDetail) {
		this.metadataDetail = metadataDetail;
	}
	public String getClubName() {
		return clubName;
	}
	public void setClubName(String clubName) {
		this.clubName = clubName;
	}
}
