package council.website.workflow.beans;

import java.io.Serializable;
import java.util.Date;

public class Workflow implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5527818263864572211L;
	
	private int workflowId;
	private String submitterUserName;
	private String reviewerUserName;
	private String reviewStatus;
	private String reviewComments;
	
	private Date submissionTime;
	private Date reviewTime;
	
	public int getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(int workflowId) {
		this.workflowId = workflowId;
	}
	public String getReviewStatus() {
		return reviewStatus;
	}
	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	public Date getSubmissionTime() {
		return submissionTime;
	}
	public void setSubmissionTime(Date submissionTime) {
		this.submissionTime = submissionTime;
	}
	public Date getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}
	public String getReviewComments() {
		return reviewComments;
	}
	public void setReviewComments(String reviewComments) {
		this.reviewComments = reviewComments;
	}
	public String getSubmitterUserName() {
		return submitterUserName;
	}
	public void setSubmitterUserName(String submitterUserName) {
		this.submitterUserName = submitterUserName;
	}
	public String getReviewerUserName() {
		return reviewerUserName;
	}
	public void setReviewerUserName(String reviewerUserName) {
		this.reviewerUserName = reviewerUserName;
	}
}
