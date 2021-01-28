package council.website.constants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ErrorMessages {
	
	private static final Map<String, String> ERROR_MESSAGES = new ConcurrentHashMap<String, String>();

	public static final String USER_NOT_FOUND_IN_DATABASE_ERROR_MESSAGE = "User does not exist";

	public static final String USER_PASSWORD_NOT_SET_ERROR = "User password is not set. Please use the link to set a new password";

	public static final String USER_PASSWORD_RETRIEVAL_ERROR = "Error in retrieving password.";

	public static final String USER_PROFILE_MODIFICATION_ERROR = "Error in modifying user password";

	public static final String USER_PROFILE_RETRIEVAL_ERROR = "An error was encountered in retrieving the user profile. Please refresh the page and try again.";

	public static Boolean checkIfErrorMessage(String input) {
		if (ERROR_MESSAGES.isEmpty()) {
			ERROR_MESSAGES.put(USER_NOT_FOUND_IN_DATABASE_ERROR_MESSAGE, USER_NOT_FOUND_IN_DATABASE_ERROR_MESSAGE);
			ERROR_MESSAGES.put(USER_PASSWORD_NOT_SET_ERROR, USER_PASSWORD_NOT_SET_ERROR);
			ERROR_MESSAGES.put(USER_PASSWORD_RETRIEVAL_ERROR, USER_PASSWORD_RETRIEVAL_ERROR);
			ERROR_MESSAGES.put(USER_PROFILE_MODIFICATION_ERROR, USER_PROFILE_MODIFICATION_ERROR);
			ERROR_MESSAGES.put(USER_PROFILE_RETRIEVAL_ERROR, USER_PROFILE_RETRIEVAL_ERROR);
		}
		
		return ERROR_MESSAGES.containsKey(input);
	}
}
