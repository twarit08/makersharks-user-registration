package makersharks.user.entities;

import java.util.Collection;

public class UserInfo {
	
	private String username;
	
	private String email;
	
	private String firstName;
	
	private String lastName;
	
	private Collection<?> authorities;

	public UserInfo() {
		
	}

	public UserInfo(String username, String email, String firstName, String lastName, Collection<?> authorities) {
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.authorities = authorities;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Collection<?> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<?> authorities) {
		this.authorities = authorities;
	}


}
