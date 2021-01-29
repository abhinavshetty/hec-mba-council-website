package council.website.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import council.website.action.beans.ActionResponse;
import council.website.user.beans.User;
import council.website.user.beans.UserDataRequest;
import council.website.user.beans.UserLoginRequest;

@Service
public class UserWebService {

	@Value("${logic.engine.location}")
	private String logicEngineUrl;

	private static final String USER_CONTROLLER_PATH = "/user";

	@Autowired
	private RestTemplate restTemplate;
	
	public User getUserProfile(UserDataRequest request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<UserDataRequest> requestEntity = new HttpEntity<UserDataRequest>(request, headers);

		User result = restTemplate.exchange(logicEngineUrl + UserWebService.USER_CONTROLLER_PATH + "/getUserProfile",
				HttpMethod.POST, requestEntity, User.class).getBody();
		return result;
	}

	public ActionResponse registerNewUser(User request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<User> requestEntity = new HttpEntity<User>(request, headers);

		ActionResponse result = restTemplate.exchange(logicEngineUrl + UserWebService.USER_CONTROLLER_PATH + "/registerNewUser",
				HttpMethod.POST, requestEntity, ActionResponse.class).getBody();
		return result;
	}

	public ActionResponse resetPasswordForUser(UserDataRequest request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<UserDataRequest> requestEntity = new HttpEntity<UserDataRequest>(request, headers);

		ActionResponse result = restTemplate.exchange(logicEngineUrl + UserWebService.USER_CONTROLLER_PATH + "/resetPasswordForUser",
				HttpMethod.POST, requestEntity, ActionResponse.class).getBody();
		return result;
	}

	public ActionResponse modifyUserProfile(User request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<User> requestEntity = new HttpEntity<User>(request, headers);

		ActionResponse result = restTemplate.exchange(logicEngineUrl + UserWebService.USER_CONTROLLER_PATH + "/modifyUserProfile",
				HttpMethod.POST, requestEntity, ActionResponse.class).getBody();
		return result;
	}

	public ActionResponse modifyUserPassword(UserLoginRequest request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<UserLoginRequest> requestEntity = new HttpEntity<UserLoginRequest>(request, headers);

		ActionResponse result = restTemplate.exchange(logicEngineUrl + UserWebService.USER_CONTROLLER_PATH + "/modifyUserPassword",
				HttpMethod.POST, requestEntity, ActionResponse.class).getBody();
		return result;
	}

	public User authenticateUserCredentials(UserLoginRequest request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<UserLoginRequest> requestEntity = new HttpEntity<UserLoginRequest>(request, headers);

		User result = restTemplate.exchange(logicEngineUrl + UserWebService.USER_CONTROLLER_PATH + "/authenticateUserCredentials",
				HttpMethod.POST, requestEntity, User.class).getBody();
		return result;
	}

	public List<User> getAllUsers() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		List<User> result = restTemplate
				.exchange(logicEngineUrl + USER_CONTROLLER_PATH + "/getAllUsers",
						HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<User>>() {
						})
				.getBody();
		return result;
	}
	
	public List<UserLoginRequest> getSystemCredentials() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		List<UserLoginRequest> result = restTemplate
				.exchange(logicEngineUrl + USER_CONTROLLER_PATH + "/getSystemCredentials",
						HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<UserLoginRequest>>() {
						})
				.getBody();
		return result;
	}

}
