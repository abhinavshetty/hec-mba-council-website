package council.website.event.beans;

import java.util.Date;

/**
 * Instance of Approved event
 * @author Abhinav Shetty
 *
 */
public class ApprovedEvent extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8014601549983859110L;
	
	private Date creationTime;
	private Date lastUpdated;
	private int eventId;

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
}
