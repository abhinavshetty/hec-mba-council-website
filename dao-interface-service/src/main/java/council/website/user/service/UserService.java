package council.website.user.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import council.website.constants.ErrorMessages;
import council.website.user.beans.User;
import council.website.user.beans.UserLoginRequest;
import council.website.user.mapper.UserDataMapper;

/**
 * Implementation for user data
 * 
 * @author Abhinav Shetty
 * @version 1.0
 */
@Service
public class UserService {

	private static final Logger LOG = LogManager.getLogger(UserService.class);

	@PostConstruct
	public void initComplete() {
		LOG.info("UserDaoManager implementation : " + UserService.class.getSimpleName() + " ready");
	}

	@Autowired
	private UserDataMapper userDataMapper;

	public User getUserProfile(String userEmail) {
		LOG.traceEntry();
		User user = null;
		try {
			List<User> users = userDataMapper.getUserProfile(userEmail);

			if (users == null) {
				throw new NullPointerException();
			} else {
				if (users.size() != 0) {
					user = users.get(0);
				} else {
					throw new NullPointerException();
				}
			}
		} catch (NullPointerException e) {
			LOG.info("User profile for " + userEmail + " could not be fetched.");
			user = new User();
			user.setUserName(ErrorMessages.USER_NOT_FOUND_IN_DATABASE_ERROR_MESSAGE);
		} catch (Exception e) {
			LOG.error(ErrorMessages.USER_PROFILE_RETRIEVAL_ERROR);
			user = new User();
			user.setUserName(ErrorMessages.USER_PROFILE_RETRIEVAL_ERROR);
		}
		LOG.traceExit();
		return user;
	}

	public List<User> getAllUsers() {
		LOG.traceEntry();
		try {
			List<User> users = userDataMapper.getAllUsers();
			LOG.traceExit();
			return users;
		} catch (NullPointerException e) {
			LOG.info("User profiles could not be fetched.");
			return null;
		} catch (Exception e) {
			LOG.error(ErrorMessages.USER_PROFILE_RETRIEVAL_ERROR);
			return null;
		}
	}

	public String getHashedPassword(String userEmail) {
		LOG.traceEntry();
		String hashedPassword = null;
		try {
			hashedPassword = userDataMapper.getHashedPassword(userEmail);
			if (hashedPassword == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			LOG.info(ErrorMessages.USER_PASSWORD_NOT_SET_ERROR + userEmail);
			hashedPassword = ErrorMessages.USER_PASSWORD_NOT_SET_ERROR;
		} catch (Exception e) {
			LOG.error(ErrorMessages.USER_PASSWORD_RETRIEVAL_ERROR + " For user " + userEmail);
			hashedPassword = ErrorMessages.USER_PASSWORD_RETRIEVAL_ERROR;
		}
		LOG.traceExit();
		return hashedPassword;
	}

	public Boolean addNewUser(User user) {
		// modify user profile
		LOG.traceEntry();
		Boolean result = false;
		userDataMapper.addNewUser(user);
		result = true;
		LOG.traceExit();
		return result;
	}

	public Boolean modifyUserProfile(User user) {
		// modify user profile
		LOG.traceEntry();
		Boolean result = false;

		userDataMapper.modifyUserProfile(user);
		result = true;

		LOG.traceExit();
		return result;
	}

	public Boolean modifyUserPassword(UserLoginRequest request) {
		// modify user password from one time portal link
		LOG.traceEntry();

		Boolean result = false;
		userDataMapper.modifyUserPassword(request);
		result = true;

		LOG.traceExit();
		return result;
	}

	public Boolean addNewUserPassword(UserLoginRequest request) {
		userDataMapper.addNewUserPassword(request);
		return true;
	}

	public List<UserLoginRequest> getUserCredentials(UserLoginRequest request) {
		return userDataMapper.getUserCredentials(request);
	}

}
