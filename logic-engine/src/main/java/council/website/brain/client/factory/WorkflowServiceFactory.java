package council.website.brain.client.factory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import council.website.brain.client.WorkflowService;

@Component
public class WorkflowServiceFactory {

	@Autowired
	private List<? extends WorkflowService> workflowServices;
	
	public WorkflowService getWorkflowService(String workflowName) {
		 for (WorkflowService client: workflowServices) {
			 if (client.getWorkflowName().equalsIgnoreCase(workflowName)) {
				 return client;
			 }
		 }
		 return null;
	}

}
