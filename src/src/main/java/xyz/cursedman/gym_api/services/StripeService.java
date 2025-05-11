package xyz.cursedman.gym_api.services;

import com.stripe.model.Product;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeRequest;

import java.net.URI;
import java.util.UUID;

public interface StripeService {
	Product createProductFromMembershipTypeRequest(MembershipTypeRequest request) throws Exception;

	URI createCheckoutSessionUri(UUID membershipTypeId, UUID userId) throws Exception;

	void handleWebhook(String payload, String sigHeader) throws Exception;
}
