package council.website.bulletin.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import council.website.bulletin.beans.Bulletin;
import council.website.bulletin.beans.BulletinWorkflow;
import council.website.bulletin.mapper.BulletinMapper;

@Service
public class BulletinService {

	@Autowired
	private BulletinMapper bulletinMapper;
	
	public List<Bulletin> getBulletinsForDateRange(String clubName, Date startDate, Date endDate) {
		// makes SQL call to get the data.
		List<Bulletin> result = bulletinMapper.getBulletinsForDateRange(clubName, startDate, endDate);
		return result;
	}

	public List<BulletinWorkflow> getWorkflowBulletinsOfStatus(String clubName, String status) {
		// gets the bulletins for different status for the submitter
		List<BulletinWorkflow> result = bulletinMapper.getWorkflowBulletinsOfStatus(clubName, status);
		return result;
	}

	public Boolean addNewBulletin(BulletinWorkflow workflow) {
		// adds a new bulletin to the table, status will be pending
		workflow.setReviewComments("START");
		bulletinMapper.addNewBulletin(workflow);
		bulletinMapper.addNewBulletinWorkflow(workflow);
		return true;
	}
	
	public Boolean approveBulletin(BulletinWorkflow workflow) {
		// approves the bulletin in workflow, performs post approval move as well
		bulletinMapper.updateBulletinWorkflow(workflow);
		bulletinMapper.moveBulletinPostApproval(workflow);
		return true;
	}
	
	public Boolean rejectBulletin(BulletinWorkflow workflow) {
		// rejects the submitted bulletin workflow
		// updates workflow table
		bulletinMapper.updateBulletinWorkflow(workflow);
		return true;
	}

	public Boolean updateBulletinWorkflow(BulletinWorkflow request) {
		// updates a bulletin workflow, whether by submitter or approver.
		// updates staging and workflow tables
		bulletinMapper.updateBulletinInStaging(request);
		bulletinMapper.updateBulletinWorkflow(request);
		return true;
	}

}
