package council.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import council.website.bulletin.beans.Bulletin;
import council.website.bulletin.beans.BulletinDataRequest;
import council.website.bulletin.beans.BulletinWorkflow;
import council.website.bulletin.service.BulletinService;

@RestController
@RequestMapping("/bulletin")
@CrossOrigin
public class BulletinController {
	
	@Autowired
	private BulletinService bulletinService;

	@PostMapping(path = "/getBulletinsForDateRange", consumes = "application/json", produces = "application/json")
	public List<Bulletin> getBulletinsForDateRange(@RequestBody BulletinDataRequest request) {
		return bulletinService.getBulletinsForDateRange(request.getClubName(), request.getStartDate(), request.getEndDate());
	}

	@PostMapping(path = "/getWorkflowBulletinsOfStatus", consumes = "application/json", produces = "application/json")
	public List<BulletinWorkflow> getWorkflowBulletinsOfStatus(@RequestBody BulletinDataRequest request) {
		return bulletinService.getWorkflowBulletinsOfStatus(request.getClubName(), request.getStatus());
	}

	@PostMapping(path = "/addNewBulletin", consumes = "application/json", produces = "application/json")
	public Boolean addNewBulletin(@RequestBody BulletinWorkflow request) {
		return bulletinService.addNewBulletin(request);
	}
	
	@PostMapping(path = "/updateBulletinWorkflow", consumes = "application/json", produces = "application/json")
	public Boolean updateBulletinWorkflow(@RequestBody BulletinWorkflow request) {
		return bulletinService.updateBulletinWorkflow(request);
	}
	
	@PostMapping(path = "/approveBulletin", consumes = "application/json", produces = "application/json")
	public Boolean approveBulletin(@RequestBody BulletinWorkflow request) {
		return bulletinService.approveBulletin(request);
	}
	
	@PostMapping(path = "/rejectBulletin", consumes = "application/json", produces = "application/json")
	public Boolean rejectBulletin(@RequestBody BulletinWorkflow request) {
		return bulletinService.rejectBulletin(request);
	}

	
}
