package makersharks.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import makersharks.user.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{

}
