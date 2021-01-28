package council.website.event.beans;

import java.math.BigDecimal;
import java.util.Date;

import council.website.workflow.beans.Workflow;

public class EventWorkflow extends Workflow {

	private static final long serialVersionUID = -3116320121024222215L;
	
	private String eventType;
	private String host;
	private String eventName;
	private String eventDesc;
	private String eventPartners;
	private String eventLanguage;
	private String eventPosterLoc;
	private String eventAddress;
	private int maxTargetAudience;
	private BigDecimal ticketPrice;
	private String ticketPurchaseLink;
	
	/*
	 * Time related variables 
	 */
	private Date eventStartTime;
	private Date eventRegistrationStartTime;
	private Date eventRegistrationEndTime;
	private Date eventEndTime;
	private Date dateToShare;
	/*
	 * Social media links
	 */
	private String facebookLink;
	private String instagramLink;
	private String twitterLink;

	public int getEventWorkflowId() {
		return this.getWorkflowId();
	}
	
	public void setEventWorkflowId(int eventWorkflowId) {
		this.setWorkflowId(eventWorkflowId);
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventPartners() {
		return eventPartners;
	}

	public void setEventPartners(String eventPartners) {
		this.eventPartners = eventPartners;
	}

	public String getEventLanguage() {
		return eventLanguage;
	}

	public void setEventLanguage(String eventLanguage) {
		this.eventLanguage = eventLanguage;
	}

	public String getEventAddress() {
		return eventAddress;
	}

	public void setEventAddress(String eventAddress) {
		this.eventAddress = eventAddress;
	}

	public int getMaxTargetAudience() {
		return maxTargetAudience;
	}

	public void setMaxTargetAudience(int maxTargetAudience) {
		this.maxTargetAudience = maxTargetAudience;
	}

	public BigDecimal getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(BigDecimal ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public String getTicketPurchaseLink() {
		return ticketPurchaseLink;
	}

	public void setTicketPurchaseLink(String ticketPurchaseLink) {
		this.ticketPurchaseLink = ticketPurchaseLink;
	}

	public Date getEventStartTime() {
		return eventStartTime;
	}

	public void setEventStartTime(Date eventStartTime) {
		this.eventStartTime = eventStartTime;
	}

	public Date getEventRegistrationStartTime() {
		return eventRegistrationStartTime;
	}

	public void setEventRegistrationStartTime(Date eventRegistrationStartTime) {
		this.eventRegistrationStartTime = eventRegistrationStartTime;
	}

	public Date getEventRegistrationEndTime() {
		return eventRegistrationEndTime;
	}

	public void setEventRegistrationEndTime(Date eventRegistrationEndTime) {
		this.eventRegistrationEndTime = eventRegistrationEndTime;
	}

	public Date getEventEndTime() {
		return eventEndTime;
	}

	public void setEventEndTime(Date eventEndTime) {
		this.eventEndTime = eventEndTime;
	}

	public Date getDateToShare() {
		return dateToShare;
	}

	public void setDateToShare(Date dateToShare) {
		this.dateToShare = dateToShare;
	}

	public String getFacebookLink() {
		return facebookLink;
	}

	public void setFacebookLink(String facebookLink) {
		this.facebookLink = facebookLink;
	}

	public String getInstagramLink() {
		return instagramLink;
	}

	public void setInstagramLink(String instagramLink) {
		this.instagramLink = instagramLink;
	}

	public String getTwitterLink() {
		return twitterLink;
	}

	public void setTwitterLink(String twitterLink) {
		this.twitterLink = twitterLink;
	}

	public String getEventPosterLoc() {
		return eventPosterLoc;
	}

	public void setEventPosterLoc(String eventPosterLoc) {
		this.eventPosterLoc = eventPosterLoc;
	}

	public String getEventDesc() {
		return eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}
}
