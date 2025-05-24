package xyz.cursedman.gym_api.services.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.user.UserDto;
import xyz.cursedman.gym_api.domain.dtos.user.UserRequest;
import xyz.cursedman.gym_api.domain.entities.User;
import xyz.cursedman.gym_api.domain.entities.UserAccountConnection;
import xyz.cursedman.gym_api.exceptions.NotFoundException;
import xyz.cursedman.gym_api.mappers.UserMapper;
import xyz.cursedman.gym_api.repositories.UserAccountConnectionRepository;
import xyz.cursedman.gym_api.repositories.UserRepository;
import xyz.cursedman.gym_api.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final UserAccountConnectionRepository userAccountConnectionRepository;

	private final UserMapper userMapper;

	@Override
	public List<UserDto> listUsers() {
		return userRepository.findAll().stream().map(userMapper::toDtoFromEntity).toList();
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
	public User getUserByUuid(UUID id) {
		return userRepository.findById(id).orElseThrow(
			() -> new NotFoundException("User with ID " + id + " not found")
		);
	}
}
