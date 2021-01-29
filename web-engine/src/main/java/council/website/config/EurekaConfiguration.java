package council.website.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class EurekaConfiguration {

//	@Value("${logic.engine.app.name}")
//	private String logicEngineAppName;
//
//	private String logicEngineUrl;
//
//	@Autowired
//	private EurekaClient eurekaClient;
//
//	@Bean(name = "logicEngineUrl", autowire = Autowire.BY_NAME)
//	public String getLogicEngineUrl() {
//		if (logicEngineUrl == null) {
//			InstanceInfo instance = eurekaClient.getNextServerFromEureka(logicEngineAppName, false);
//			this.logicEngineUrl = instance.getHomePageUrl();
//		}
//		return logicEngineUrl;
//	}

}
