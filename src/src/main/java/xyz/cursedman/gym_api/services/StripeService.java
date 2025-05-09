package xyz.cursedman.gym_api.services;

import com.stripe.model.Product;

import java.net.URI;
import java.util.Optional;

public interface StripeService {

	Optional<Product> getProductByName(String name);

	Optional<URI> getProductCheckoutUri(String productName);

	void handleWebhook(String payload, String sigHeader) throws Exception;
}
