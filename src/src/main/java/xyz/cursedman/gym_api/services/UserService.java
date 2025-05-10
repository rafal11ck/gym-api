package xyz.cursedman.gym_api.services;

import com.stripe.exception.StripeException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import xyz.cursedman.gym_api.domain.dtos.user.UserDto;
import xyz.cursedman.gym_api.domain.dtos.user.UserRequest;
import xyz.cursedman.gym_api.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

	List<UserDto> listUsers();

	UserDto getUser(UUID id) throws EntityNotFoundException;

	UserDto createUser(UserRequest request) throws DataIntegrityViolationException;

	UserDto patchUser(UUID id, UserRequest request) throws EntityNotFoundException;

	// used by mapper
	User getUserByUuid(UUID id);

	void createCustomerProfilesForExistingUsers() throws StripeException;
}
