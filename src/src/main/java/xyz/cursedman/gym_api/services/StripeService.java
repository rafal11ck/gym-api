package xyz.cursedman.gym_api.services;

import com.stripe.model.Product;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

public interface StripeService {

	Optional<Product> getProductByName(String name);

	Optional<URI> createCheckoutSession(String productName, UUID userId);
}
