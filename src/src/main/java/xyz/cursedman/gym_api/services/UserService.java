package xyz.cursedman.gym_api.services;

import jakarta.persistence.EntityNotFoundException;
import xyz.cursedman.gym_api.domain.dtos.user.UserDto;
import xyz.cursedman.gym_api.domain.dtos.user.UserRequest;
import xyz.cursedman.gym_api.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
	List<UserDto> listUsers();

	UserDto getUser(UUID id);

	UserDto createUser(UserRequest request);

	UserDto patchUser(UUID id, UserRequest request);

	// used by mapper
	User getUserByUuid(UUID id);
}
