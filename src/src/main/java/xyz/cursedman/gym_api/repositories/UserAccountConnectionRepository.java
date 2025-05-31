package xyz.cursedman.gym_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.cursedman.gym_api.domain.entities.UserAccountConnection;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountConnectionRepository extends JpaRepository<UserAccountConnection, UUID> {

	Optional<UserAccountConnection>
	findByExternalAuthorizationProviderNameEqualsAndExternalAuthorizationUserIdEquals
		(String externalAuthorizationProviderName, String externalAuthorizationUserId);
}
