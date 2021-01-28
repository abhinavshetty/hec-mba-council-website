package council.website.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import council.website.user.beans.User;
import council.website.user.beans.UserLoginRequest;

@Mapper
public interface UserDataMapper {
	
	public List<User> getUserProfile(@Param("userEmail") String userEmail);
	
	public List<User> getAllUsers();
	
	public String getHashedPassword(@Param("userEmail") String userEmail);
	
	public int modifyUserProfile(@Param("user") User user);
	
	public int addNewUser(@Param("user") User user);
	
	public int modifyUserPassword(@Param("request") UserLoginRequest request);
	
	public Boolean modifyUserAccess(@Param("user") User user);

	public int addNewUserPassword(@Param("request") UserLoginRequest request);

	public List<UserLoginRequest> getUserCredentials(@Param("request") UserLoginRequest request);
	
}
