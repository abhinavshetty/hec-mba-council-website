package council.website.comms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import council.website.action.beans.Comms;

@Service
public class CommsWebService {
	
	@Value("${logic.engine.location}")
	private String logicEngineUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String COMMS_CONTROLLER_PATH = "/comms";
	
	public Comms getEmailToShareToAdmin() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		Comms result = restTemplate
				.exchange(logicEngineUrl + COMMS_CONTROLLER_PATH + "/getEmailToShareToAdmin",
						HttpMethod.GET, requestEntity, Comms.class)
				.getBody();
		return result;
	}
	
	public Boolean editEmailToShareToAdmin(Comms comms) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<Comms> requestEntity = new HttpEntity<Comms>(comms, headers);

		Boolean result = restTemplate
				.exchange(logicEngineUrl + COMMS_CONTROLLER_PATH + "/editEmailToShareToAdmin",
						HttpMethod.POST, requestEntity, Boolean.class)
				.getBody();
		return result;
	}

}
