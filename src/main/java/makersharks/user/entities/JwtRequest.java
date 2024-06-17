package makersharks.user.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class JwtRequest {
	
	@NotBlank(message = "username can't be blank!")
	private String username;
	
	@NotBlank(message = "password can't be blank.")
	@Size(min = 6, message = "enter minimum six character password")
	private String password;

	public JwtRequest() {
		super();
	}

	public JwtRequest(@NotBlank(message = "username can't be blank!") String username,
			@NotBlank(message = "password can't be blank.") @Size(min = 6, message = "enter minimum six character password") String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
