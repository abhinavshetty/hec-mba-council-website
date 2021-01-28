package council.website.action.beans;

import java.io.Serializable;

public class Comms implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7291111231831907456L;
	private String commsTo;
	private String commsSubject;
	private String commsDetailTemplate;
	public String getCommsTo() {
		return commsTo;
	}
	public void setCommsTo(String commsTo) {
		this.commsTo = commsTo;
	}
	public String getCommsSubject() {
		return commsSubject;
	}
	public void setCommsSubject(String commsSubject) {
		this.commsSubject = commsSubject;
	}
	public String getCommsDetailTemplate() {
		return commsDetailTemplate;
	}
	public void setCommsDetailTemplate(String commsDetailTemplate) {
		this.commsDetailTemplate = commsDetailTemplate;
	}
}
