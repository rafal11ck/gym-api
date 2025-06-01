package xyz.cursedman.gym_api.services.impl;

import com.stripe.Stripe;
import com.stripe.model.Event;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.config.StripeProperties;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeRequest;
import xyz.cursedman.gym_api.domain.entities.MembershipType;
import xyz.cursedman.gym_api.domain.entities.User;
import xyz.cursedman.gym_api.repositories.StripeRepository;
import xyz.cursedman.gym_api.services.MembershipTypeService;
import xyz.cursedman.gym_api.services.UserService;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StripeServiceImpl {

	private final StripeProperties stripeProperties;

	private final StripeRepository stripeRepository;

	private final UserService userService;

	private final MembershipTypeService membershipTypeService;

	@PostConstruct
	private void initializeStripe() {
		Stripe.apiKey = stripeProperties.getApiKey();
	}

	public Product createProductFromMembershipTypeRequest(MembershipTypeRequest request) throws Exception {
		ProductCreateParams productCreateParams = ProductCreateParams.builder()
			.setName(request.getType())
			.build();

		Product createdProduct = stripeRepository.saveProduct(productCreateParams);

		PriceCreateParams priceCreateParams = PriceCreateParams.builder()
			.setCurrency("pln")
			.setProduct(createdProduct.getId())
			.setRecurring(
				PriceCreateParams.Recurring.builder()
					.setInterval(PriceCreateParams.Recurring.Interval.MONTH)
					.build()
			).setUnitAmount(request.getPrice())
			.build();

		stripeRepository.savePrice(priceCreateParams);
		return createdProduct;
	}

	public URI createCheckoutSessionUri(UUID membershipTypeId, UUID userId) throws Exception {
		MembershipType membershipType = membershipTypeService.getMembershipTypeByUuid(membershipTypeId);

		Optional<Product> product = stripeRepository.findProductById(membershipType.getProductId());
		if (product.isEmpty()) {
			throw new Exception("Product for membership type " + membershipTypeId + " not found");
		}

		Optional<Price> price = stripeRepository.findPriceByProductId(product.get().getId());
		if (price.isEmpty()) {
			throw new Exception("Price for membership type " + membershipTypeId + " not found");
		}

		SessionCreateParams.Builder sessionCreateParams = SessionCreateParams.builder()
			.setMode(SessionCreateParams.Mode.SUBSCRIPTION)
			.setSuccessUrl(stripeProperties.getSuccessRedirectUrl())
			.setCancelUrl(stripeProperties.getCancelRedirectUrl())
			.setClientReferenceId(userId.toString())
			.addLineItem(
				SessionCreateParams.LineItem.builder()
					.setQuantity(1L)
					.setPrice(price.get().getId())
					.build()
			);

		String customerId = userService.getUserByUuid(userId).getCustomerId();
		if (customerId != null) {
			sessionCreateParams.setCustomer(customerId);
		}

		Session savedSession = stripeRepository.saveSession(sessionCreateParams.build());
		return URI.create(savedSession.getUrl());
	}

	public void handleWebhook(String payload, String sigHeader) throws Exception {
		Event event = Webhook.constructEvent(payload, sigHeader, stripeProperties.getWebhookSecret());

		if ("checkout.session.completed".equals(event.getType())
			|| "checkout.session.async_payment_succeeded".equals(event.getType())
		) {
			Session sessionEvent = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
			if (sessionEvent != null) {
				fulfillCheckout(sessionEvent.getId());
			}
		}
	}

	private void fulfillCheckout(String sessionId) throws Exception {
		Optional<Session> foundSession = stripeRepository.findSessionById(sessionId);

		if (foundSession.isEmpty()) {
			return;
		}

		Session checkoutSession = foundSession.get();
		UUID userId = UUID.fromString(checkoutSession.getClientReferenceId());
		User user = userService.getUserByUuid(userId);

		if (user.getCustomerId() == null) {
			userService.updateUserCustomerId(user, checkoutSession.getCustomer());
		}
	}
}
