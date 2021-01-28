package council.website.starter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import council.website.starter.BaseStarter;

/**
 * Microservice for Web service data.
 * @author Abhinav Shetty
 */
@SpringBootApplication
@ComponentScan(basePackages = "council.website")
@EnableAutoConfiguration
public class WebApplicationStartPoint {
	
	public static final Logger LOG = LogManager.getLogger(WebApplicationStartPoint.class);

	public static void main(String[] args) {
		BaseStarter starter = new BaseStarter();
		starter.init(WebApplicationStartPoint.class, args);
		LOG.info("Started application : " + WebApplicationStartPoint.class);
	}
}
