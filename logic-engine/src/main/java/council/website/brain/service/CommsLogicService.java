package council.website.brain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import council.website.action.beans.ActionStatus;
import council.website.action.beans.Comms;
import council.website.event.beans.Event;
import council.website.user.beans.User;

@Service
public class CommsLogicService {

	@Autowired
	@Qualifier("daoInterfaceUrl")
	private String daoInterfaceUrl;

	@Autowired
	@Qualifier("mailingProjectUrl")
	private String mailingProjectUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private EventLogicService eventService;

	private static final String COMMS_CONTROLLER_PATH = "/comms";

	public Comms getEmailToShareToAdmin() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		List<Comms> result = restTemplate.exchange(daoInterfaceUrl + COMMS_CONTROLLER_PATH + "/getEmailToShareToAdmin",
				HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Comms>>() {
				}).getBody();
		return result.get(0);
	}

	public Boolean editEmailToShareToAdmin(Comms comms) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<Comms> requestEntity = new HttpEntity<Comms>(comms, headers);

		Boolean result = restTemplate.exchange(daoInterfaceUrl + COMMS_CONTROLLER_PATH + "/editEmailToShareToAdmin",
				HttpMethod.POST, requestEntity, Boolean.class).getBody();
		return result;
	}

	public void sendEmailToNewUser(User user) {
		Comms email = new Comms();
		email.setCommsTo(user.getUserEmail());
		email.setCommsSubject(ActionStatus.PASSWORD_RESET_SUBJECT);
		String mailContent = "Hi " + user.getUserName() + ",\n\n";
		mailContent = mailContent
				+ "A new user has been created on the HEC MBA life website for you! Please use your HEC email and password "
				+ user.getUserClub() + "\n\n";
		mailContent = mailContent + "You can change this password from your profile page after logging in.";
		email.setCommsDetailTemplate(mailContent);

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<Comms> requestEntity = new HttpEntity<Comms>(email, headers);

		restTemplate.exchange(mailingProjectUrl + "/mail/sendEmail", HttpMethod.POST, requestEntity, Boolean.class)
				.getBody();
	}
	
	public void sendResetPasswordToCurrentUser(User user) {
		Comms email = new Comms();
		email.setCommsTo(user.getUserEmail());
		email.setCommsSubject(ActionStatus.CREATE_NEW_USER);
		String mailContent = "Hi " + user.getUserName() + ",\n\n";
		mailContent = mailContent
				+ "Your password on the HEC MBA life website has been reset! Please use your HEC email and password "
				+ user.getUserClub() + "\n\n";
		mailContent = mailContent + "You can change this password from your profile page after logging in.";
		email.setCommsDetailTemplate(mailContent);

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<Comms> requestEntity = new HttpEntity<Comms>(email, headers);

		restTemplate.exchange(mailingProjectUrl + "/mail/sendEmail", HttpMethod.POST, requestEntity, Boolean.class)
				.getBody();
	}

	@Scheduled(fixedRate = 86400000)
	public void sendEmailToAdmin() {
		Comms email = this.getEmailToShareToAdmin();
		List<Event> eventsToShare = this.eventService.getEventsForSocialMediaShare();
		if (eventsToShare != null && eventsToShare.size() != 0) {
			email.setCommsDetailTemplate(email.getCommsDetailTemplate() + "\n");
			eventsToShare.forEach(item -> {
				String lineItem = "Event : " + item.getEventName();
				lineItem = lineItem + "  :: Facebook: " + item.getFacebookLink();
				lineItem = lineItem + "  :: Instagram: " + item.getInstagramLink();
				lineItem = lineItem + "  :: Twitter: " + item.getTwitterLink();
				lineItem = lineItem + "\n";
				email.setCommsDetailTemplate(email.getCommsDetailTemplate() + lineItem);

			});

			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			headers.set("content-type", "application/json");
			HttpEntity<Comms> requestEntity = new HttpEntity<Comms>(email, headers);

			restTemplate.exchange(mailingProjectUrl + "/mail/sendEmail", HttpMethod.POST, requestEntity, Boolean.class)
					.getBody();
		}
		return;
	}
}
