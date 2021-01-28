package council.website.event.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Event instance for new, existing and deleted events.
 * @author Abhinav Shetty
 * @version 1.0
 */
public class Event implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 573476505428471028L;
	
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
	public Date getEventStartTime() {
		return eventStartTime;
	}
	public void setEventStartTime(Date eventStartTime) {
		this.eventStartTime = eventStartTime;
	}
	public Date getEventEndTime() {
		return eventEndTime;
	}
	public void setEventEndTime(Date eventEndTime) {
		this.eventEndTime = eventEndTime;
	}
	public String getEventAddress() {
		return eventAddress;
	}
	public void setEventAddress(String eventAddress) {
		this.eventAddress = eventAddress;
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
	public Date getDateToShare() {
		return dateToShare;
	}
	public void setDateToShare(Date dateToShare) {
		this.dateToShare = dateToShare;
	}
	public int getMaxTargetAudience() {
		return maxTargetAudience;
	}
	public void setMaxTargetAudience(int maxTargetAudience) {
		this.maxTargetAudience = maxTargetAudience;
	}
	public String getEventDesc() {
		return eventDesc;
	}
	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}
	public String getEventPosterLoc() {
		return eventPosterLoc;
	}
	public void setEventPosterLoc(String eventPosterLoc) {
		this.eventPosterLoc = eventPosterLoc;
	}
	
}
