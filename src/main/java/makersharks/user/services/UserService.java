package makersharks.user.services;

import org.springframework.http.ResponseEntity;

import makersharks.user.entities.JwtRequest;
import makersharks.user.entities.JwtResponse;
import makersharks.user.entities.User;
import makersharks.user.entities.UserInfo;

public interface UserService {
	
	ResponseEntity<String> register(User user);
	
	ResponseEntity<UserInfo> getUser(String username);
	
	ResponseEntity<JwtResponse> loginJwt(JwtRequest loginRequest);
	
	

}
