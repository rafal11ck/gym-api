package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.user.UserDto;
import xyz.cursedman.gym_api.domain.dtos.user.UserRequest;
import xyz.cursedman.gym_api.domain.entities.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
	List<UserDto> listUsers();

	boolean existsUser(UUID uuid);

	UserDto getUser(UUID id);

	UserDto createUser(UserRequest request);

	public UserDto createLinkedUser
		(UserRequest request, String externalAuthorizationProviderName, String externalId);

	public UserDto createOrUpdateLinkedUser
		(UserRequest request, String externalAuthorizationProviderName, String externalId);

	UserDto patchUser(UUID id, UserRequest request);

	Optional<UserDto> getUserByExternalAuthorizationId(String externalAuthorizationProviderName, String externalId);

	Collection<String> getUserRoles(UUID userUuid);

	// used by mapper
	User getUserByUuid(UUID id);
}
