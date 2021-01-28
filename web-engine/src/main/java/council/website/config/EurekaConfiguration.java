package council.website.config;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@Configuration
public class EurekaConfiguration {

	@Value("${logic.engine.app.name}")
	private String logicEngineAppName;

	private String logicEngineUrl;

	@Autowired
	private EurekaClient eurekaClient;

	@Bean(name = "logicEngineUrl", autowire = Autowire.BY_NAME)
	public String getLogicEngineUrl() {
		if (logicEngineUrl == null) {
			InstanceInfo instance = eurekaClient.getNextServerFromEureka(logicEngineAppName, false);
			this.logicEngineUrl = instance.getHomePageUrl();
		}
		return logicEngineUrl;
	}

}
