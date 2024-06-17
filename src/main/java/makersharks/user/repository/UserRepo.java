package makersharks.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import makersharks.user.entities.User;

public interface UserRepo extends JpaRepository<User, Long>{
	User findByUsernameOrEmail(String username, String email);
	User findByUsername(String username);
}
