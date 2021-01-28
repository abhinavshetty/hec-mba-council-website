package council.website.brain.startup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import council.website.starter.BaseStarter;


/**
 * Microservice for Bot instance warehouse. Exposes trade information controller
 * for UI consolidation later
 * 
 * @author Abhinav Shetty
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = "council.website")
@EnableAutoConfiguration
@EnableScheduling
public class LogicEngineStartPoint {

	public static final Logger LOG = LogManager.getLogger(LogicEngineStartPoint.class);

	public static void main(String[] args) {
		BaseStarter starter = new BaseStarter();
		starter.init(LogicEngineStartPoint.class, args);
		LOG.info("Started application : " + LogicEngineStartPoint.class);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**");
			}
		};
	}
}
