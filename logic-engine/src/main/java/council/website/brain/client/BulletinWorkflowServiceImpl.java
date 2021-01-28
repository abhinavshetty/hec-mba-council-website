package council.website.brain.client;

import org.springframework.stereotype.Service;

/**
 * Bulletin workflow management service
 * 
 * @author Abhinav Shetty
 *
 */
@Service
public class BulletinWorkflowServiceImpl implements WorkflowService {
	
	private static final String WORKFLOW_NAME = "BULLETIN";

	/**
	 * Create a new bulletin workflow
	 */
	@Override
	public Object createNewWorkflow() {
		// creates a new bulletin workflow

		// confirm whether all necessary fields are present within the input

		// write bulletin to staging table

		// write new workflow in pending status to workflow table

		// consolidate a response based on any errors/successful implementation in the
		// process

		return null;
	}

	/**
	 * Update an existing bulletin workflow and take appropriate actions
	 */
	@Override
	public Object updateWorkflowStatus() {
		// updates the status of bulletin workflow and takes appropriate actions

		// confirm whether all necessary fields are present within the input

		// update status of bulletin workflow

		// take actions based on update made

		// consolidate a response based on any errors/successful implementation in the process

		return null;
	}

	/**
	 * Take actions based on an approve action for a bulletin
	 */
	@Override
	public Boolean approveWorkflow() {
		// contains the actions to take if a bulletin is approved
		
		// constructs a response based on errors/successful execution
		return null;
	}

	@Override
	public String getWorkflowName() {
		return BulletinWorkflowServiceImpl.WORKFLOW_NAME;
	}

}
