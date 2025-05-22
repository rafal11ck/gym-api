package xyz.cursedman.gym_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.user.UserDto;
import xyz.cursedman.gym_api.domain.dtos.user.UserRequest;
import xyz.cursedman.gym_api.domain.entities.User;
import xyz.cursedman.gym_api.exceptions.NotFoundException;
import xyz.cursedman.gym_api.mappers.UserMapper;
import xyz.cursedman.gym_api.repositories.UserRepository;
import xyz.cursedman.gym_api.services.UserService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	@Override
	public List<UserDto> listUsers() {
		return userRepository.findAll().stream().map(userMapper::toDtoFromEntity).toList();
	}

	@Override
	public UserDto getUser(UUID id)  {
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
	public UserDto patchUser(UUID id, UserRequest request) {
		User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
		userMapper.updateFromRequest(request, user);

		User result = userRepository.save(user);
		return userMapper.toDtoFromEntity(result);
	}

	@Override
	public User getUserByUuid(UUID id) throws EntityNotFoundException {
		return userRepository.findById(id).orElseThrow(
			() -> new NotFoundException("User with ID " + id + " not found")
		);
	}

	@Override
	public void updateUserCustomerId(User user, String newCustomerId) {
		user.setCustomerId(newCustomerId);
		userRepository.save(user);
	}
}
