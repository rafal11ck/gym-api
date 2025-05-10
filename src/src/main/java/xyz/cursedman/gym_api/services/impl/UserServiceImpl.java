package xyz.cursedman.gym_api.services.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerListParams;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.user.UserDto;
import xyz.cursedman.gym_api.domain.dtos.user.UserRequest;
import xyz.cursedman.gym_api.domain.entities.User;
import xyz.cursedman.gym_api.mappers.UserMapper;
import xyz.cursedman.gym_api.repositories.UserRepository;
import xyz.cursedman.gym_api.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private boolean addCustomerProfileToUser(User user) throws StripeException {
		CustomerListParams listParams = CustomerListParams.builder().build();
		List<Customer> customers = Customer.list(listParams).getData();

		String fullName = user.getFirstName() + " " + user.getLastName();
		Optional<Customer> foundCustomer = customers.stream()
			.filter(customer ->
				customer.getEmail().equals(user.getEmail())
				&& customer.getName().equals(fullName)
			).findFirst();

		if (foundCustomer.isPresent()) {
			user.setCustomerId(foundCustomer.get().getId());
			return false;
		}

		CustomerCreateParams params = CustomerCreateParams.builder()
			.setName(user.getFirstName() + " " + user.getLastName())
			.setEmail(user.getEmail())
			.build();

		Customer customer = Customer.create(params);
		user.setCustomerId(customer.getId());

		return true;
	}

	@Override
	public void createCustomerProfilesForExistingUsers() throws StripeException {
		for (User user : userRepository.findAll()) {
			boolean newCustomerCreated = addCustomerProfileToUser(user);
			userRepository.save(user);

			String fullName = user.getFirstName() + " " + user.getLastName();
			if (newCustomerCreated) {
				logger.info("Created Stripe customer for user '{}'.", fullName);
			} else {
				logger.info("Stripe customer profile already exists for user '{}'. Skipping creation.", fullName);
			}
		}
	}

	@Override
	public List<UserDto> listUsers() {
		return userRepository.findAll().stream().map(userMapper::toDtoFromEntity).toList();
	}

	@Override
	public UserDto getUser(UUID id) throws EntityNotFoundException {
		return userRepository
			.findById(id)
			.map(userMapper::toDtoFromEntity)
			.orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public UserDto createUser(UserRequest request) throws DataIntegrityViolationException {
		User user = userMapper.toEntityFromRequest(request);
		try {
			addCustomerProfileToUser(user);
			User result = userRepository.save(user);
			return userMapper.toDtoFromEntity(result);
		} catch (StripeException e) {
			throw new DataIntegrityViolationException(
				"Error while creating customer profile for user " + user.getUuid(), e
			);
		}
	}

	@Override
	public UserDto patchUser(UUID id, UserRequest request) throws EntityNotFoundException {
		User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		userMapper.updateFromRequest(request, user);

		User result = userRepository.save(user);
		return userMapper.toDtoFromEntity(result);
	}

	@Override
	public User getUserByUuid(UUID id) {
		return userRepository.findById(id).orElseThrow(
			() -> new EntityNotFoundException("User with ID " + id + " not found")
		);
	}
}
