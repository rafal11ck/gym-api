package xyz.cursedman.gym_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.cursedman.gym_api.domain.entities.UserRole;

import java.util.Optional;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
	boolean existsByRoleName(String roleName);

	Optional<UserRole> findByRoleNameEqualsIgnoreCase(String roleName);
}
