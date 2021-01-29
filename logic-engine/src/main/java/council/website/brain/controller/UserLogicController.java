package council.website.brain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import council.website.action.beans.ActionResponse;
import council.website.brain.service.UserLogicService;
import council.website.user.beans.User;
import council.website.user.beans.UserDataRequest;
import council.website.user.beans.UserLoginRequest;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserLogicController {
	
	@Autowired
	private UserLogicService userService;

	@PostMapping(path = "/getUserProfile", produces = "application/json")
	public User getUserProfile(@RequestBody UserDataRequest request) {
		return userService.getUserProfile(request);
	}
	
	@PostMapping(path = "/registerNewUser", produces = "application/json")
	public ActionResponse registerNewUser(@RequestBody User request) {
		return userService.registerNewUser(request);
	}
	
	@PostMapping(path = "/resetPasswordForUser", produces = "application/json")
	public ActionResponse resetPasswordForUser(@RequestBody UserDataRequest request) {
		return userService.resetPasswordForUser(request);
	}
	
	@PostMapping(path = "/modifyUserProfile", produces = "application/json")
	public ActionResponse modifyUserProfile(@RequestBody User request) {
		return userService.modifyUserProfile(request);
	}
	
	@PostMapping(path = "/modifyUserPassword", produces = "application/json")
	public ActionResponse modifyUserPassword(@RequestBody UserLoginRequest request) {
		return userService.modifyUserPassword(request);
	}
	
	@PostMapping(path = "/authenticateUserCredentials", produces = "application/json")
	public User authenticateUserCredentials(@RequestBody UserLoginRequest request) {
		return userService.authenticateUserCredentials(request);
	}
	
	@GetMapping(path = "/getAllUsers", produces = "application/json")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping(path = "/getSystemCredentials", produces = "application/json")
	public List<UserLoginRequest> getSystemCredentials() {
		return userService.getSystemCredentials();
	}
}
