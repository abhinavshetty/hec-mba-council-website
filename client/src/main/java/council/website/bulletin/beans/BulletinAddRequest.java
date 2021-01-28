package council.website.bulletin.beans;

import java.io.Serializable;
import java.util.Date;

public class BulletinAddRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -368330606075468641L;

	private int bulletinId;
	private String bulletinTitle;
	private String bulletinDesc;
	private Date bulletinStartTime;
	private Date bulletinEndTime;
	private String clubName;
	private String bulletinPosterLoc;
	
	public int getBulletinId() {
		return bulletinId;
	}
	public void setBulletinId(int bulletinId) {
		this.bulletinId = bulletinId;
	}
	public String getBulletinTitle() {
		return bulletinTitle;
	}
	public void setBulletinTitle(String bulletinTitle) {
		this.bulletinTitle = bulletinTitle;
	}
	public String getBulletinDesc() {
		return bulletinDesc;
	}
	public void setBulletinDesc(String bulletinDesc) {
		this.bulletinDesc = bulletinDesc;
	}
	public Date getBulletinStartTime() {
		return bulletinStartTime;
	}
	public void setBulletinStartTime(Date bulletinStartTime) {
		this.bulletinStartTime = bulletinStartTime;
	}
	public Date getBulletinEndTime() {
		return bulletinEndTime;
	}
	public void setBulletinEndTime(Date bulletinEndTime) {
		this.bulletinEndTime = bulletinEndTime;
	}
	public String getClubName() {
		return clubName;
	}
	public void setClubName(String clubName) {
		this.clubName = clubName;
	}
	public String getBulletinPosterLoc() {
		return bulletinPosterLoc;
	}
	public void setBulletinPosterLoc(String bulletinPosterLoc) {
		this.bulletinPosterLoc = bulletinPosterLoc;
	}
	
}
