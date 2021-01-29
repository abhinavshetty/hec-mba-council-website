package council.website.mail.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import council.website.user.beans.UserLoginRequest;

@Service
public class MailingPropertyService {

	@Value("${dao.interface.service.location}")
	private String daoInterfaceUrl;

	private static final String USER_CONTROLLER_PATH = "/user";

	@Autowired
	private RestTemplate restTemplate;

	public List<UserLoginRequest> getSystemCredentials() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		List<UserLoginRequest> result = restTemplate
				.exchange(daoInterfaceUrl + MailingPropertyService.USER_CONTROLLER_PATH + "/getSystemCredentials", HttpMethod.GET,
						requestEntity, new ParameterizedTypeReference<List<UserLoginRequest>>() {
						})
				.getBody();

		return result;
	}
}
