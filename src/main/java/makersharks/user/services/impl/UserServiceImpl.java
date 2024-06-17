package makersharks.user.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import makersharks.user.config.InternalServerException;
import makersharks.user.config.UnauthorizedException;
import makersharks.user.config.UserExistException;
import makersharks.user.config.UserNotFoundException;
import makersharks.user.entities.JwtRequest;
import makersharks.user.entities.JwtResponse;
import makersharks.user.entities.Role;
import makersharks.user.entities.User;
import makersharks.user.entities.UserInfo;
import makersharks.user.entities.UserRoles;
import makersharks.user.repository.RoleRepo;
import makersharks.user.repository.UserRepo;
import makersharks.user.security.JwtUtil;
import makersharks.user.services.UserDetailService;
import makersharks.user.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private User saveUser(User user, Set<UserRoles> userRoles) {
		for(UserRoles roleSet : userRoles) {
			this.roleRepo.save(roleSet.getRole());
		}
		user.getUserRoles().addAll(userRoles);
		user.setPassword(this.encoder.encode(user.getPassword()));
		User regUser = this.userRepo.save(user);
		return regUser;
	}

	@Override
	public ResponseEntity<String> register(User user) {
		User newUser = null;
		try {
			newUser = this.userRepo.findByUsername(user.getUsername());
			if(newUser!=null) {
				//user already exist
				throw new UserExistException("Username", "user", newUser.getUsername());
			}else {
				//register new user
				newUser = user;
				
				//more than one role can be given but for simplicity giving only one role. 
				//TODO: Give more than one role to a user.
				Role role = new Role();
				role.setRoleId(101l);
				role.setRoleName("USER");
				
				UserRoles userRole = new UserRoles();
				userRole.setRole(role);
				userRole.setUser(newUser);
				
				Set<UserRoles> userRoleSet = new HashSet<>();
				userRoleSet.add(userRole);
				
				User userRegistered = saveUser(newUser,userRoleSet);
				
				if(userRegistered==null) {
					throw new InternalServerException("Unable to register user!");
				}
				
			}
		} catch(UserExistException e) {
			throw new UserExistException(e.getMessage());
		} catch(Exception e) {
			throw new InternalServerException(e.getMessage());
		}
		 
		return new ResponseEntity<>("User registered successfully!",HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<UserInfo> getUser(String username) {
		
		User user = null;
		try {
			if(username!=null && !username.isBlank()) {
				user = this.userRepo.findByUsername(username);
			}else {
				throw new InternalServerException("Username is null or blank!");
			}
			if(user==null) {
				throw new UsernameNotFoundException("Username does not exist");
			}
		} catch(UsernameNotFoundException e) {
			throw new UserNotFoundException(e.getLocalizedMessage());
		} catch (Exception e) {
			throw new InternalServerException(e.getLocalizedMessage());
		}
		UserInfo userInfo = new UserInfo(user.getUsername(),user.getEmail(),user.getFirstName(),user.getLastName(),user.getAuthorities());
		return ResponseEntity.ok(userInfo);
	}
	
	private void authenticate(String username, String password) throws Exception{
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}  catch(BadCredentialsException e) {
			throw new Exception("Invalid Credentials! "+e.getMessage());
		} catch(DisabledException e) {
			throw new Exception("User disabled! "+e.getMessage());
		} catch (AuthenticationException e) {
			throw new Exception(e.getLocalizedMessage());
		} catch(Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}
	}

	@Override
	public ResponseEntity<JwtResponse> loginJwt(JwtRequest loginRequest) {
		try {
			authenticate(loginRequest.getUsername(), loginRequest.getPassword());
		}catch(UsernameNotFoundException e) {
			throw new UserNotFoundException("Invalid credentials or user does not exist!");
		} catch (Exception e) {
			throw new UnauthorizedException(e.getLocalizedMessage());
		}
		//successful authentication
		UserDetails userDetails = this.userDetailService.loadUserByUsername(loginRequest.getUsername());
		String token = this.jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

}
