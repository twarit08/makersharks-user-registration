package makersharks.user.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="user_data")
public class User implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(accessMode = AccessMode.READ_ONLY)
	private Long userId;
	
	@NotBlank(message = "username can't be blank.")
	@Column(nullable = false, unique=true)
	private String username;
	
	@NotBlank(message = "password can't be blank.")
	@Size(min = 6, message = "enter minimum six character password")
	@Column(nullable = false)
	private String password;
	
	@NotBlank(message = "email address can't be blank.")
	@Column(nullable = false, unique=true)
	private String email;
	
	@NotBlank(message = "first name can't be blank.")
	@Column(nullable = false)
	private String firstName;
	
	@NotBlank(message = "last name can't be blank.")
	@Column(nullable = false)
	private String lastName;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
	@JsonIgnore
	private Set<UserRoles> userRoles = new HashSet<>();

	public User() {
		super();
	}

	public User(Long userId, @NotBlank(message = "username can't be blank.") String username,
			@NotBlank(message = "password can't be blank.") @Size(min = 6, message = "enter minimum six character password") String password,
			@NotBlank(message = "email address can't be blank.") String email,
			@NotBlank(message = "first name can't be blank.") String firstName,
			@NotBlank(message = "last name can't be blank.") String lastName, Set<UserRoles> userRoles) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userRoles = userRoles;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Set<UserRoles> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRoles> userRoles) {
		this.userRoles = userRoles;
	}

	@Override
	@Schema(accessMode = AccessMode.READ_ONLY)
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Authority> auth = new HashSet<>();
		this.userRoles.forEach(roles -> {
			auth.add(new Authority(roles.getRole().getRoleName()));
		});
		return auth;
	}

	@Override
	@Schema(accessMode = AccessMode.READ_ONLY)
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@Schema(accessMode = AccessMode.READ_ONLY)
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@Schema(accessMode = AccessMode.READ_ONLY)
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@Schema(accessMode = AccessMode.READ_ONLY)
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	

}
