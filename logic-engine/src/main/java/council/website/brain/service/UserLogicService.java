package council.website.brain.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import council.website.action.beans.ActionResponse;
import council.website.action.beans.ActionStatus;
import council.website.constants.UserRole;
import council.website.user.beans.User;
import council.website.user.beans.UserDataRequest;
import council.website.user.beans.UserLoginRequest;

@Service
public class UserLogicService {

	@Autowired
	@Qualifier("daoInterfaceUrl")
	private String daoInterfaceUrl;

	private static final String USER_CONTROLLER_PATH = "/user";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CommsLogicService commsService;

	public User getUserProfile(UserDataRequest request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<UserDataRequest> requestEntity = new HttpEntity<UserDataRequest>(request, headers);

		User result = restTemplate.exchange(daoInterfaceUrl + UserLogicService.USER_CONTROLLER_PATH + "/getUserProfile",
				HttpMethod.POST, requestEntity, User.class).getBody();
		return result;
	}

	public ActionResponse registerNewUser(User request) {
		ActionResponse response;
		if (request == null) {
			response = new ActionResponse();
			response.setResponseDetail(ActionStatus.REQUEST_IS_EMPTY);
			response.setResponseMessage(ActionStatus.ACTION_ERROR);
			return response;
		} else {
			if (request.getUserEmail().endsWith("@hec.edu")) {
				// check if the user already exists.
				List<User> allUsers = this.getAllUsers();
				boolean isExisting = false;
				for (User existingUser : allUsers) {
					if (request.getUserEmail().equalsIgnoreCase(existingUser.getUserEmail())) {
						isExisting = true;
					}
				}

				if (isExisting) {
					// user already exists. Use forgot password utility to retrieve a new password
					response = new ActionResponse();
					response.setResponseDetail(ActionStatus.USER_ALREADY_EXISTS);
					response.setResponseMessage(ActionStatus.ACTION_ERROR);
					return response;
				} else {
					// add a new user and password.
					MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
					headers.set("content-type", "application/json");
					request.setUserRole(UserRole.STUDENT_ACCESS);
					request.setUserClub(null);
					HttpEntity<User> addUserRequestEntity = new HttpEntity<User>(request, headers);

					Boolean userAddResult = restTemplate
							.exchange(daoInterfaceUrl + UserLogicService.USER_CONTROLLER_PATH + "/addNewUser",
									HttpMethod.POST, addUserRequestEntity, Boolean.class)
							.getBody();

					UserLoginRequest passwordAddRequest = new UserLoginRequest();
					passwordAddRequest.setUserEmail(request.getUserEmail());
					passwordAddRequest.setUserPassword(generateRandomPassword());
					HttpEntity<UserLoginRequest> passwordAddRequestEntity = new HttpEntity<UserLoginRequest>(
							passwordAddRequest, headers);

					Boolean passwordAddResult = restTemplate
							.exchange(daoInterfaceUrl + UserLogicService.USER_CONTROLLER_PATH + "/addNewUserPassword",
									HttpMethod.POST, passwordAddRequestEntity, Boolean.class)
							.getBody();

					if (userAddResult && passwordAddResult) {
						// successfully added a new user! send the email to user with new credentials
						request.setUserClub(passwordAddRequest.getUserPassword());
						commsService.sendEmailToNewUser(request);
						response = new ActionResponse();
						response.setResponseDetail(ActionStatus.CREATE_NEW_USER);
						response.setResponseMessage(ActionStatus.CREATE_NEW_USER);
						return response;
					} else {
						// error in adding a new user was encountered. Contact the system admin.
						response = new ActionResponse();
						response.setResponseDetail(ActionStatus.CREATE_NEW_USER_UNEXPECTED_ERROR);
						response.setResponseMessage(ActionStatus.CREATE_NEW_USER_UNEXPECTED_ERROR);
						return response;
					}
				}

			} else {
				response = new ActionResponse();
				response.setResponseDetail(ActionStatus.NON_HEC_EMAIL_FOR_NEW_USER);
				response.setResponseMessage(ActionStatus.NON_HEC_EMAIL_FOR_NEW_USER);
				return response;
			}
		}
	}

	public ActionResponse modifyUserProfile(User request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<User> requestEntity = new HttpEntity<User>(request, headers);

		restTemplate.exchange(daoInterfaceUrl + UserLogicService.USER_CONTROLLER_PATH + "/modifyUserProfile",
				HttpMethod.POST, requestEntity, Boolean.class).getBody();

		ActionResponse result = new ActionResponse();
		result.setResponseDetail(ActionStatus.USER_DATA_MODIFICATION_SUCCESS);
		result.setResponseMessage(ActionStatus.USER_DATA_MODIFICATION_SUCCESS);
		return result;
	}

	public ActionResponse modifyUserPassword(UserLoginRequest request) {
		ActionResponse result = new ActionResponse();

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		
		UserLoginRequest checkRequest = new UserLoginRequest();
		checkRequest.setUserEmail(request.getUserEmail());
		checkRequest.setUserPassword(request.getOldUserPassword());
		
		HttpEntity<UserLoginRequest> checkRequestEntity = new HttpEntity<UserLoginRequest>(checkRequest, headers);
		List<UserLoginRequest> userCreds = restTemplate
				.exchange(daoInterfaceUrl + UserLogicService.USER_CONTROLLER_PATH + "/getUserCredentials",
						HttpMethod.POST, checkRequestEntity, new ParameterizedTypeReference<List<UserLoginRequest>>() {
						})
				.getBody();

		UserLoginRequest response = userCreds.get(0);
		if (response.getUserPassword().equalsIgnoreCase(request.getOldUserPassword())) {
			// passwords match! allow change
			UserLoginRequest updateRequest = new UserLoginRequest();
			updateRequest.setUserEmail(request.getUserEmail());
			updateRequest.setUserPassword(request.getUserPassword());
			HttpEntity<UserLoginRequest> updateRequestEntity = new HttpEntity<UserLoginRequest>(updateRequest, headers);
			
			restTemplate.exchange(daoInterfaceUrl + UserLogicService.USER_CONTROLLER_PATH + "/modifyUserPassword",
					HttpMethod.POST, updateRequestEntity, Boolean.class).getBody();

			result.setResponseDetail(ActionStatus.USER_DATA_MODIFICATION_SUCCESS);
			result.setResponseMessage(ActionStatus.ACTION_SUCCESS);
		} else {
			// passwords do not match! reject the login
			result.setResponseDetail(ActionStatus.PASSWORD_INCORRECT);
			result.setResponseMessage(ActionStatus.ACTION_ERROR);
		}
		
		return result;
	}

	public List<User> getAllUsers() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		List<User> result = restTemplate
				.exchange(daoInterfaceUrl + UserLogicService.USER_CONTROLLER_PATH + "/getAllUsers", HttpMethod.GET,
						requestEntity, new ParameterizedTypeReference<List<User>>() {
						})
				.getBody();

		return result;
	}

	private String generateRandomPassword() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

	public User authenticateUserCredentials(UserLoginRequest request) {
		User result;

		if (request != null) {
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			headers.set("content-type", "application/json");
			HttpEntity<UserLoginRequest> requestEntity = new HttpEntity<UserLoginRequest>(request, headers);

			List<UserLoginRequest> userCreds = restTemplate
					.exchange(daoInterfaceUrl + UserLogicService.USER_CONTROLLER_PATH + "/getUserCredentials",
							HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<UserLoginRequest>>() {
							})
					.getBody();

			if (userCreds != null && userCreds.size() != 0) {
				UserLoginRequest response = userCreds.get(0);
				if (response.getUserPassword().equalsIgnoreCase(request.getUserPassword())) {
					// passwords match! allow login
					UserDataRequest userRequest = new UserDataRequest();
					userRequest.setUserEmail(request.getUserEmail());

					result = this.getUserProfile(userRequest);
				} else {
					// passwords do not match! reject the login
					result = new User();
					result.setUserName(ActionStatus.PASSWORD_INCORRECT);
				}
			} else {
				result = new User();
				result.setUserName(ActionStatus.USERNAME_INCORRECT);
			}
		} else {
			result = new User();
			result.setUserName(ActionStatus.REQUEST_IS_EMPTY);
		}

		return result;
	}

	public ActionResponse resetPasswordForUser(UserDataRequest request) {
		ActionResponse response;
		if (request.getUserEmail().endsWith("@hec.edu")) {
			// check if the user already exists.
			List<User> allUsers = this.getAllUsers();
			boolean isExisting = false;
			User currentUser = null;
			for (User existingUser : allUsers) {
				if (request.getUserEmail().equalsIgnoreCase(existingUser.getUserEmail())) {
					isExisting = true;
					currentUser = existingUser;
				}
			}

			if (isExisting) {
				// user already exists. Reset the password for the user
				MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
				headers.set("content-type", "application/json");
				UserLoginRequest passwordChangeRequest = new UserLoginRequest();
				passwordChangeRequest.setUserEmail(request.getUserEmail());
				passwordChangeRequest.setUserPassword(generateRandomPassword());
				HttpEntity<UserLoginRequest> passwordChangeRequestEntity = new HttpEntity<UserLoginRequest>(
						passwordChangeRequest, headers);

				restTemplate.exchange(daoInterfaceUrl + UserLogicService.USER_CONTROLLER_PATH + "/modifyUserPassword",
						HttpMethod.POST, passwordChangeRequestEntity, Boolean.class).getBody();

				currentUser.setUserClub(passwordChangeRequest.getUserPassword());
				commsService.sendResetPasswordToCurrentUser(currentUser);

				response = new ActionResponse();
				response.setResponseDetail(ActionStatus.NEW_PASSWORD_SENT);
				response.setResponseMessage(ActionStatus.NEW_PASSWORD_SENT);
			} else {
				// user does not exist
				response = new ActionResponse();
				response.setResponseDetail(ActionStatus.USERNAME_INCORRECT);
				response.setResponseMessage(ActionStatus.USERNAME_INCORRECT);
			}

		} else {
			response = new ActionResponse();
			response.setResponseDetail(ActionStatus.NON_HEC_EMAIL_FOR_NEW_USER);
			response.setResponseMessage(ActionStatus.NON_HEC_EMAIL_FOR_NEW_USER);
		}

		return response;
	}
}
