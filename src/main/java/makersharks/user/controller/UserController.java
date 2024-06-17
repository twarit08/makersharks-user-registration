package makersharks.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import makersharks.user.entities.JwtRequest;
import makersharks.user.entities.JwtResponse;
import makersharks.user.entities.User;
import makersharks.user.entities.UserInfo;
import makersharks.user.services.UserService;

@RestController
@RequestMapping("/api/user")
@Tag(name="UserController", description = "API's for user registration.")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	@Operation(summary="User registration", description="Endpoint to register a new user.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "User registered successfully!"),
			@ApiResponse(responseCode = "409", description = "User already exist!"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error!")
	})
	public ResponseEntity<String> userRegistration(@Valid @RequestBody User user){
		return this.userService.register(user);
	}
	
	@GetMapping("/fetch")
	@Operation(summary="fetch user details", description="Enpoint to fetch existing user details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User details fetched successfully!"),
			@ApiResponse(responseCode = "401", description = "Unauthorized User!"),
			@ApiResponse(responseCode = "404", description = "Username not found!"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error!")
	})
	public ResponseEntity<UserInfo> fetchUser(@RequestParam(required = true,name = "username") String username){
		return this.userService.getUser(username);
	}
	
	@PostMapping("/auth")
	@Operation(summary="login authorization", description="Authorizes users by credentials and give a token in response.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Authorization successful. Token Generated!"),
			@ApiResponse(responseCode = "401", description = "Unauthorized User!"),
			@ApiResponse(responseCode = "404", description = "Username not found!"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error!")
	})
	public ResponseEntity<JwtResponse> login(@Valid @RequestBody JwtRequest loginRequest){
		return this.userService.loginJwt(loginRequest);
	}
	
	
	
	
	
	

}
