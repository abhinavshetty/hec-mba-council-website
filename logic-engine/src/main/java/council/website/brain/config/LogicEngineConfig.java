package council.website.brain.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class LogicEngineConfig {

//	@Value("${dao.interface.app.name}")
//	private String daoInterfaceAppName;
//
//	@Value("${mailing.project.app.name}")
//	private String mailingProjectAppName;
//
//	private String daoInterfaceUrl;
//
//	private String mailingProjectUrl;
//
//	@Autowired
//	private EurekaClient eurekaClient;
//
//	@Bean(name = "daoInterfaceUrl", autowire= Autowire.BY_NAME)
//	public String getDaoInterfaceUrl() {
//		if (this.daoInterfaceUrl == null) {
//			InstanceInfo instance = eurekaClient.getNextServerFromEureka(daoInterfaceAppName, false);
//			this.daoInterfaceUrl = instance.getHomePageUrl();
//		}
//		return this.daoInterfaceUrl;
//	}
//
//	@Bean(name = "mailingProjectUrl", autowire= Autowire.BY_NAME)
//	public String getMailingProjectUrl() {
//		if (this.mailingProjectUrl == null) {
//			InstanceInfo instance = eurekaClient.getNextServerFromEureka(mailingProjectAppName, false);
//			this.mailingProjectUrl = instance.getHomePageUrl();
//		}
//		return this.mailingProjectUrl;
//	}
}
