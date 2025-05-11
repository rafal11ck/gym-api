package xyz.cursedman.gym_api.startup;

import com.stripe.model.Product;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xyz.cursedman.gym_api.config.DevelopmentProperties;
import xyz.cursedman.gym_api.config.StripeProperties;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeRequest;
import xyz.cursedman.gym_api.domain.entities.MembershipType;
import xyz.cursedman.gym_api.domain.entities.User;
import xyz.cursedman.gym_api.mappers.MembershipTypeMapper;
import xyz.cursedman.gym_api.repositories.MembershipTypeRepository;
import xyz.cursedman.gym_api.repositories.StripeRepository;
import xyz.cursedman.gym_api.repositories.UserRepository;
import xyz.cursedman.gym_api.services.StripeService;

import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StripeDevelopmentInitializer implements CommandLineRunner {

	private final DevelopmentProperties developmentProperties;

	private final StripeProperties stripeProperties;

	private final StripeRepository stripeRepository;

	private final StripeService stripeService;

	private final MembershipTypeMapper membershipTypeMapper;

	private final MembershipTypeRepository membershipTypeRepository;

	private final UserRepository userRepository;

	private final Logger logger = LoggerFactory.getLogger(StripeDevelopmentInitializer.class);

	@Override
	public void run(String... args) throws Exception {
		if (!developmentProperties.getStripe().isTestingEnabled() || !stripeProperties.isStripeConfigurationValid()) {
			return;
		}

		String productName = "Membership";
		Optional<Product> foundProduct = stripeRepository.findFirstProductByName(productName);
		String productId;

		MembershipType membershipType = MembershipType.builder()
			.type(productName)
			.price(1000L)
			.build();

		if (foundProduct.isPresent() && foundProduct.get().getActive()) {
			productId = foundProduct.get().getId();
			logger.info("Skipping Stripe product creation: product for '{}' already exists.", productName);
		} else {
			MembershipTypeRequest membershipTypeRequest = membershipTypeMapper.toRequestFromEntity(membershipType);
			productId = stripeService.createProductFromMembershipTypeRequest(membershipTypeRequest).getId();
			logger.info("Creating new Stripe product for '{}'.", productName);
		}

		membershipType.setProductId(productId);
		MembershipType savedMembershipType = membershipTypeRepository.save(membershipType);

		User user = User.builder()
			.firstName("John")
			.lastName("Doe")
			.dateOfBirth(new Date())
			.build();

		User savedUser = userRepository.save(user);

		logger.info(
			"Stripe development data for '{}' created.\nTest payment with user ID: {} and membership type ID: {}\n(http://localhost:8080/users/{}/subscriptions/{}/setup-subscription)",
			productName,
			savedUser.getUuid(),
			savedMembershipType.getUuid(),
			savedUser.getUuid(),
			savedMembershipType.getUuid()
		);
	}
}
