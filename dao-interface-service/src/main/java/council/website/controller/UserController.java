package council.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import council.website.user.beans.User;
import council.website.user.beans.UserDataRequest;
import council.website.user.beans.UserLoginRequest;
import council.website.user.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping(path = "/getUserProfile", produces = "application/json")
	public User getUserProfile(@RequestBody UserDataRequest request) {
		return userService.getUserProfile(request.getUserEmail());
	}
	
	@PostMapping(path = "/addNewUser", produces = "application/json")
	public Boolean addNewUser(@RequestBody User request) {
		return userService.addNewUser(request);
	}
	
	@PostMapping(path = "/addNewUserPassword", produces = "application/json")
	public Boolean addNewUserPassword(@RequestBody UserLoginRequest request) {
		return userService.addNewUserPassword(request);
	}
	
	@PostMapping(path = "/modifyUserProfile", produces = "application/json")
	public Boolean modifyUserProfile(@RequestBody User request) {
		return userService.modifyUserProfile(request);
	}
	
	@PostMapping(path = "/modifyUserPassword", produces = "application/json")
	public Boolean modifyUserPassword(@RequestBody UserLoginRequest request) {
		return userService.modifyUserPassword(request);
	}
	
	@PostMapping(path = "/getUserCredentials", produces = "application/json")
	public List<UserLoginRequest> getUserCredentials(@RequestBody UserLoginRequest request) {
		return userService.getUserCredentials(request);
	}
	
	@GetMapping(path = "/getSystemCredentials", produces = "application/json")
	public List<UserLoginRequest> getSystemCredentials() {
		return userService.getSystemCredentials();
	}
	
	@GetMapping(path = "/getAllUsers", produces = "application/json")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
}
