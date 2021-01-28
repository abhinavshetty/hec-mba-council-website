package council.website.mail.starter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import council.website.starter.BaseStarter;

/**
 * Mailing project for erudite labs
 * @author Abhinav Shetty
 */
@SpringBootApplication(exclude = {JmxAutoConfiguration.class})
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan(basePackages = {"council.website"})
public class MailingProjectStartPoint
{
	public static final Logger LOG = LogManager.getLogger(MailingProjectStartPoint.class);
	
	public static void main(String[] args) {
		BaseStarter starter = new BaseStarter();
		starter.init(MailingProjectStartPoint.class, args);
		LOG.info("Started application : " + MailingProjectStartPoint.class);
	}
}
