package council.website.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import council.website.action.beans.Comms;
import council.website.mail.service.MailCommunicationService;

@RestController
@RequestMapping("/mail")
public class MailController {
	
	@Autowired
	private MailCommunicationService mailService;

	@PostMapping(path = "/sendEmail", consumes = "application/json", produces = "application/json")
	public Boolean sendEmail(@RequestBody Comms profile) {
		mailService.sendEmailNotice(profile);
		return true;
	}
}
