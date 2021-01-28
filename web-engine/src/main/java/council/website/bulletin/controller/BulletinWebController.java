package council.website.bulletin.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import council.website.action.beans.ActionResponse;
import council.website.bulletin.beans.Bulletin;
import council.website.bulletin.beans.BulletinConcise;
import council.website.bulletin.beans.BulletinDataRequest;
import council.website.bulletin.beans.BulletinWorkflow;
import council.website.bulletin.service.BulletinWebService;

@RestController
@RequestMapping("/bulletin")
@CrossOrigin
public class BulletinWebController {

	@Autowired
	private BulletinWebService bulletinService;

	@PostMapping(path = "/getBulletinsForRealTime", consumes = "application/json", produces = "application/json")
	public List<Bulletin> getBulletinsForRealTime() {
		return bulletinService.getBulletinsForRealTime();
	}

	@PostMapping(path = "/getNewBulletinsSincePastDate", consumes = "application/json", produces = "application/json")
	public List<BulletinConcise> getNewBulletinsSincePastDate() {
		return null;
	}

	@PostMapping(path = "/getApprovedBulletinsForClub", consumes = "application/json", produces = "application/json")
	public List<Bulletin> getApprovedBulletinsForClub(@RequestBody BulletinDataRequest request) {
		return bulletinService.getApprovedBulletinsForClub(request);
	}

	@PostMapping(path = "/getPendingBulletinsForSubmitter", consumes = "application/json", produces = "application/json")
	public List<BulletinWorkflow> getPendingBulletinsForSubmitter(@RequestBody BulletinDataRequest request) {
		return bulletinService.getPendingBulletinsForSubmitter(request);
	}

	@PostMapping(path = "/getPendingBulletinsForApprover", consumes = "application/json", produces = "application/json")
	public List<BulletinWorkflow> getPendingBulletinsForApprover() {
		return bulletinService.getPendingBulletinsForApprover();
	}

	@PostMapping(path = "/addNewBulletin", consumes = "application/json", produces = "application/json")
	public ActionResponse addNewBulletin(@RequestBody BulletinWorkflow request) {
		return bulletinService.addNewBulletin(request);
	}

	@PostMapping(path = "/addOrUpdatePosterForBulletin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ActionResponse addOrUpdatePosterForBulletin(@RequestParam("file") MultipartFile file,
			@RequestParam("bulletinTitle") String bulletinTitle) throws IOException, AmazonServiceException, AmazonClientException, IllegalStateException, InterruptedException {
		return bulletinService.addOrUpdatePosterForBulletin(file, bulletinTitle);
	}
	
	@RequestMapping(path = "/getPosterForBulletin")
	public ResponseEntity<Resource> getPosterForBulletin(@RequestParam String fileName) throws MalformedURLException, AmazonServiceException, AmazonClientException, InterruptedException {
		return bulletinService.getPosterForBulletin(fileName);
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
