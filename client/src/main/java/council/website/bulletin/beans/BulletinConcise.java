package council.website.bulletin.beans;

import java.io.Serializable;
import java.util.Date;

public class BulletinConcise implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7738291657205505242L;
	
	protected int bulletinId;
	protected String bulletinTitle;
	protected String clubName;
	protected Date bulletinStartTime;
	protected Date bulletinEndTime;
	
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
	public String getClubName() {
		return clubName;
	}
	public void setClubName(String clubName) {
		this.clubName = clubName;
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

}
