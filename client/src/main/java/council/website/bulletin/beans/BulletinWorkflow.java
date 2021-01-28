package council.website.bulletin.beans;

import java.util.Date;

import council.website.workflow.beans.Workflow;

public class BulletinWorkflow extends Workflow {

	private static final long serialVersionUID = -8848180082212043300L;
	private String bulletinTitle;
	private String bulletinDesc;
	private String bulletinPosterLoc;
	private String clubName;
	private Date bulletinStartTime;
	private Date bulletinEndTime;
	
	public int getBulletinWorkflowId() {
		return this.getWorkflowId();
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

	public String getBulletinPosterLoc() {
		return bulletinPosterLoc;
	}

	public void setBulletinPosterLoc(String bulletinPosterLoc) {
		this.bulletinPosterLoc = bulletinPosterLoc;
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
