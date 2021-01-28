package council.website.brain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import council.website.action.beans.ActionResponse;
import council.website.brain.service.BulletinLogicService;
import council.website.bulletin.beans.Bulletin;
import council.website.bulletin.beans.BulletinDataRequest;
import council.website.bulletin.beans.BulletinWorkflow;

@RestController
@RequestMapping("/bulletin")
@CrossOrigin
public class BulletinLogicController {

	@Autowired
	private BulletinLogicService bulletinService;
	
	@PostMapping(path = "/getBulletinsForRealTime", consumes = "application/json", produces = "application/json")
	public List<Bulletin> getBulletinsForRealTime() {
		return bulletinService.getBulletinsForRealTime();
	}
	
	@PostMapping(path = "/getApprovedBulletinsForClub", consumes = "application/json", produces = "application/json")
	public List<Bulletin> getApprovedBulletinsForClub(@RequestBody BulletinDataRequest request) {
		return bulletinService.getApprovedBulletinsForClub(request);
	}
	
	@PostMapping(path = "/getPendingBulletinsForApprover", consumes = "application/json", produces = "application/json")
	public List<BulletinWorkflow> getPendingBulletinsForApprover(@RequestBody BulletinDataRequest request) {
		return bulletinService.getPendingBulletinsForApprover(request);
	}
	
	@PostMapping(path = "/getPendingBulletinsForSubmitter", consumes = "application/json", produces = "application/json")
	public List<BulletinWorkflow> getPendingBulletinsForSubmitter(@RequestBody BulletinDataRequest request) {
		return bulletinService.getPendingBulletinsForSubmitter(request);
	}
	
	@PostMapping(path = "/addNewBulletin", consumes = "application/json", produces = "application/json")
	public ActionResponse addNewBulletin(@RequestBody BulletinWorkflow request) {
		return bulletinService.addNewBulletin(request);
	}
	
	@PostMapping(path = "/updateBulletinWorkflow", consumes = "application/json", produces = "application/json")
	public ActionResponse updateBulletinWorkflow(@RequestBody BulletinWorkflow request) {
		return bulletinService.updateBulletinWorkflow(request);
	}
	
	@PostMapping(path = "/approveBulletin", consumes = "application/json", produces = "application/json")
	public ActionResponse approveBulletin(@RequestBody BulletinWorkflow request) {
		return bulletinService.approveBulletin(request);
	}
	
	@PostMapping(path = "/rejectBulletin", consumes = "application/json", produces = "application/json")
	public ActionResponse rejectBulletin(@RequestBody BulletinWorkflow request) {
		return bulletinService.rejectBulletin(request);
	}
}
