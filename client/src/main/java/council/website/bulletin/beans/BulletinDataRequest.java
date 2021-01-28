package council.website.bulletin.beans;

import java.io.Serializable;
import java.util.Date;

public class BulletinDataRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6792634499426317785L;
	
	public BulletinDataRequest() {
		this.clubName = null;
		this.startDate = null;
		this.endDate = null;
		this.status = null;
		this.fileName = null;
	}
	
	private String clubName; 
	private Date startDate;
	private Date endDate;
	private String status;
	private String fileName;
	
	public String getClubName() {
		return clubName;
	}
	public void setClubName(String clubName) {
		this.clubName = clubName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
