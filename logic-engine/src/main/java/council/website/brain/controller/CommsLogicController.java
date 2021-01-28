package council.website.brain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import council.website.action.beans.Comms;
import council.website.brain.service.CommsLogicService;

@RestController
@RequestMapping("/comms")
@CrossOrigin
public class CommsLogicController {
	
	@Autowired
	private CommsLogicService commsService;

	@GetMapping(path = "/getEmailToShareToAdmin", produces = "application/json")
	public Comms getEmailToShareToAdmin() {
		return commsService.getEmailToShareToAdmin();
	}
	
	@PostMapping(path = "/editEmailToShareToAdmin", consumes = "application/json", produces = "application/json")
	public Boolean editEmailToShareToAdmin(@RequestBody Comms comms) {
		return commsService.editEmailToShareToAdmin(comms);
	}
}
