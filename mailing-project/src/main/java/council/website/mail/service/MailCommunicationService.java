package council.website.mail.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import council.website.action.beans.Comms;

@Service
public class MailCommunicationService {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmailNotice(Comms communication) {
		SimpleMailMessage msg = new SimpleMailMessage();
		
		if (communication.getCommsTo().contains(";")) {
			// to handle multiple recipients
			List<String> recipients = new ArrayList<String>();
			for (int i =0; i< communication.getCommsTo().length();) {
				String recipient = communication.getCommsTo().substring(i, communication.getCommsTo().indexOf(";", i));
				recipients.add(recipient);
				i = 1 + communication.getCommsTo().indexOf(";", i);
			}
			String recipientList[] = new String[recipients.size()];
			msg.setTo(recipients.toArray(recipientList));
		} else {
			msg.setTo(communication.getCommsTo());
		}
		msg.setSubject(communication.getCommsSubject());
		msg.setText(communication.getCommsDetailTemplate());
		javaMailSender.send(msg);
	}

}
