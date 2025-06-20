package xyz.cursedman.gym_api.services.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.user.UserDto;
import xyz.cursedman.gym_api.domain.dtos.user.UserRequest;
import xyz.cursedman.gym_api.domain.entities.User;
import xyz.cursedman.gym_api.domain.entities.UserAccountConnection;
import xyz.cursedman.gym_api.domain.entities.UserRole;
import xyz.cursedman.gym_api.exceptions.NotFoundException;
import xyz.cursedman.gym_api.mappers.UserMapper;
import xyz.cursedman.gym_api.repositories.UserAccountConnectionRepository;
import xyz.cursedman.gym_api.repositories.UserRepository;
import xyz.cursedman.gym_api.services.UserService;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final UserAccountConnectionRepository userAccountConnectionRepository;

	private final UserMapper userMapper;

	@Override
	public Page<UserDto> listUsers(Pageable pageable) {
		return userRepository.findAll(pageable).map(userMapper::toDtoFromEntity);
	}

	@Override
	public boolean existsUser(UUID uuid) {
		return userRepository.existsById(uuid);
	}


	@Override
	public UserDto getUser(UUID id) {
		return userRepository
			.findById(id)
			.map(userMapper::toDtoFromEntity)
			.orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
	}

	@Override
	public UserDto createUser(UserRequest request) {
		User user = userMapper.toEntityFromRequest(request);
		User result = userRepository.save(user);
		return userMapper.toDtoFromEntity(result);
	}

	@Override
	@Transactional
	public UserDto createLinkedUser
		(UserRequest request, String externalAuthorizationProviderName, String externalId) {
		UserDto createdUser = createUser(request);
		Optional<User> user = userRepository.findById(createdUser.getUuid());
		if (user.isEmpty()) {
			throw new RuntimeException("This should never happen");
		}

		UserAccountConnection userAccountConnection = new UserAccountConnection();
		userAccountConnection.setUser(user.get());
		userAccountConnection.setExternalAuthorizationUserId(externalId);
		userAccountConnection.
			setExternalAuthorizationProviderName(externalAuthorizationProviderName);

		userAccountConnectionRepository.save(userAccountConnection);
		return userMapper.toDtoFromEntity(user.get());
	}

	@Transactional
	@Override
	public UserDto createOrUpdateLinkedUser
		(UserRequest request, String externalAuthorizationProviderName, String externalId) {
		Optional<UserDto> userDtoOptional =
			this.getUserByExternalAuthorizationId(externalAuthorizationProviderName, externalId);
		// if exists

		UserDto result;
		if (userDtoOptional.isPresent()) {
			result = patchUser(userDtoOptional.get().getUuid(), request);
		} else {
			result = createLinkedUser(request, externalAuthorizationProviderName, externalId);
		}
		return result;
	}


	@Override
	public UserDto patchUser(UUID id, UserRequest request) {
		User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
		userMapper.updateFromRequest(request, user);

		User result = userRepository.save(user);
		return userMapper.toDtoFromEntity(result);
	}

	@Override
	public Optional<UserDto> getUserByExternalAuthorizationId(String externalAuthorizationProviderName, String externalId) {
		// Get connection if exists
		Optional<UserAccountConnection> userAccountConnection = userAccountConnectionRepository
			.findByExternalAuthorizationProviderNameEqualsAndExternalAuthorizationUserIdEquals
				(externalAuthorizationProviderName, externalId);

		Optional<User> user = Optional.empty();
		// if found connection fetch user
		if (userAccountConnection.isPresent()) {
			user = Optional.ofNullable(userAccountConnection.get().getUser());
		}

		// Optional with User or empty
		return user.flatMap(entity -> Optional.ofNullable(userMapper.toDtoFromEntity(entity)));
	}

	@Override
	public Collection<String> getUserRoles(UUID userUuid) {
		return
			userRepository.findById(userUuid)
				.map(
					user -> user.getRoles().stream().map(UserRole::getRoleName)
						.collect(Collectors.toSet())
				)
				.orElseThrow(() -> new NotFoundException("User with id " + userUuid + " not found"));
	}


	@Override
	public User getUserByUuid(UUID id) {
		if (id == null) return null;
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public UserDto getCurrentUser() {
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		UUID uuid = UUID.fromString(userId);

		return userRepository.findById(uuid)
			.map(userMapper::toDtoFromEntity)
			.orElseThrow(() -> new RuntimeException("User with id " + uuid + " not found"));
	}

}
