package council.website.mail.config;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import council.website.user.beans.UserLoginRequest;
 
@Configuration
public class MailConfiguration {
	
	@Autowired
	private MailingPropertyService propertyService;
 
    @Autowired
    private Environment env;
 
    @Bean
    public JavaMailSender getMailSender() {
    	
    	List<UserLoginRequest> properties = propertyService.getSystemCredentials();
		String accessKey = null, secretKey = null;
		for (UserLoginRequest property : properties) {
			if (property.getUserEmail().equalsIgnoreCase("spring.mail.username")) {
				accessKey = property.getUserPassword();
			}
			if (property.getUserEmail().equalsIgnoreCase("spring.mail.password")) {
				secretKey = property.getUserPassword();
			}
		}
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
 
        mailSender.setHost(env.getProperty("spring.mail.host"));
        mailSender.setPort(Integer.valueOf(env.getProperty("spring.mail.port")));
        mailSender.setUsername(accessKey);
        mailSender.setPassword(secretKey);
 
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");
 
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }
}