package council.website.action.beans;

import java.io.Serializable;

public class ActionResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3208282736131408630L;
	private String responseMessage;
	private String responseDetail;

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getResponseDetail() {
		return responseDetail;
	}

	public void setResponseDetail(String responseDetail) {
		this.responseDetail = responseDetail;
	}

}
