package council.website.dao.startup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import council.website.starter.BaseStarter;



/**
 * Microservice for Database related ops. Used by all components
 * @author Abhinav Shetty
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = "council.website")
@EnableAutoConfiguration
public class DaoApplicationStartPoint {
	
	public static final Logger LOG = LogManager.getLogger(DaoApplicationStartPoint.class);

	public static void main(String[] args) {
		BaseStarter starter = new BaseStarter();
		starter.init(DaoApplicationStartPoint.class, args);
		LOG.info("Started application : " + DaoApplicationStartPoint.class);
	}
	
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedOrigins("http://localhost:46111", "http://localhost:47111", "http://localhost:48111");
//			}
//		};
//	}
}
