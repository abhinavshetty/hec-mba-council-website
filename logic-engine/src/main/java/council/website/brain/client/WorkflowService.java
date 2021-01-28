package council.website.brain.client;

public interface WorkflowService {
	
	public String getWorkflowName();

	public Object createNewWorkflow();
	
	public Object updateWorkflowStatus();
	
	public Boolean approveWorkflow();
	
	
}
