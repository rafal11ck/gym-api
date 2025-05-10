package xyz.cursedman.gym_api.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.PriceCollection;
import com.stripe.model.Product;
import com.stripe.model.ProductCollection;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.PriceListParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.ProductListParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.config.StripeProperties;
import xyz.cursedman.gym_api.domain.stripe.RegisteredStripeProduct;
import xyz.cursedman.gym_api.domain.stripe.StripeProductCandidate;
import xyz.cursedman.gym_api.services.StripeService;
import xyz.cursedman.gym_api.services.UserService;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StripeServiceImpl implements StripeService {

	private final ObjectMapper objectMapper;

	private final StripeProperties stripeProperties;

	private final UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(StripeServiceImpl.class);

	@Getter
	private final List<RegisteredStripeProduct> registeredProducts = new ArrayList<>();

	@PostConstruct
	private void initializeStripe()  {
		Stripe.apiKey = stripeProperties.getApiKey();
		registerProducts();
		registerCustomers();
	}

	private void registerCustomers() {
		try {
			userService.createCustomerProfilesForExistingUsers();
		} catch (StripeException e) {
			throw new RuntimeException("Error while creating customer profiles for existing users.", e);
		}
	}

	private void registerProducts() {
		List<StripeProductCandidate> products = loadProductsFromJson();
		for (StripeProductCandidate product : products) {
			Optional<Product> existingProduct = getProductByName(product.getName());
			boolean productActive = existingProduct.map(Product::getActive).orElse(false);

			if (productActive) {
				registerExistingProduct(existingProduct.get());
				logger.info("Product '{}' already exists in Stripe. Skipping creation.", product.getName());
			} else {
				registerNewProduct(product);
				logger.info("Product '{}' created in Stripe.", product.getName());
			}
		}
	}

	private List<StripeProductCandidate> loadProductsFromJson() {
		String path = stripeProperties.getProductsFilePath();
		ClassPathResource resource = new ClassPathResource(path);

		try {
			return objectMapper.readValue(
				resource.getInputStream(),
				objectMapper.getTypeFactory().constructCollectionType(List.class, StripeProductCandidate.class)
			);
		} catch (IOException e) {
			logger.error("Error reading stripe products from path '{}'", path, e);
			return List.of();
		}
	}

	private void registerExistingProduct(Product product) {
		try {
			PriceListParams listParams = PriceListParams.builder().build();
			PriceCollection prices = Price.list(listParams);
			Price foundPrice = prices.getData()
				.stream()
				.filter(price -> price.getProduct().equals(product.getId()))
				.findFirst()
				.orElseThrow(Exception::new);

			registeredProducts.add(new RegisteredStripeProduct(product, foundPrice));
		} catch (Exception e) {
			logger.error("Error reading existing product '{}' price.", product.getName(), e);
		}

	}

	private void registerNewProduct(StripeProductCandidate product) {
		try {
			ProductCreateParams productCreateParams = ProductCreateParams.builder()
				.setName(product.getName())
				.setDescription(product.getDescription())
				.build();

			Product createdProduct = Product.create(productCreateParams);

			PriceCreateParams.Recurring recurring = PriceCreateParams.Recurring.builder()
				.setInterval(PriceCreateParams.Recurring.Interval.MONTH)
				.build();

			PriceCreateParams priceCreateParams = PriceCreateParams.builder()
				.setCurrency("pln")
				.setRecurring(recurring)
				.setProduct(createdProduct.getId())
				.setUnitAmount(product.getPrice())
				.build();

			Price createdPrice = Price.create(priceCreateParams);

			registeredProducts.add(new RegisteredStripeProduct(createdProduct, createdPrice));
		} catch (StripeException e) {
			logger.error("Error creating product '{}'", product.getName(), e);
		}
	}

	@Override
	public Optional<Product> getProductByName(String name) {
		try {
			ProductListParams params = ProductListParams.builder().build();
			ProductCollection products = Product.list(params);
			return products.getData()
				.stream()
				.filter(product -> product.getName().equalsIgnoreCase(name))
				.findFirst();
		} catch (StripeException e) {
			logger.error("Error while retrieving product '{}'.", name, e);
			return Optional.empty();
		}
	}

	@Override
	public Optional<URI> createCheckoutSession(String productName, UUID userId) {
		Optional<Price> priceOptional = registeredProducts.stream()
			.filter(product -> product.getProduct().getName().equalsIgnoreCase(productName))
			.map(RegisteredStripeProduct::getPrice)
			.findFirst();

		if (priceOptional.isEmpty()) {
			return Optional.empty();
		}

		Price price = priceOptional.get();
		String customerId = userService.getUserByUuid(userId).getCustomerId();
		SessionCreateParams params = SessionCreateParams.builder()
			.setMode(SessionCreateParams.Mode.SUBSCRIPTION)
			.setSuccessUrl(stripeProperties.getSuccessRedirectUrl())
			.setCancelUrl(stripeProperties.getFailureRedirectUrl())
			.setCustomer(customerId)
			.addLineItem(
				SessionCreateParams.LineItem.builder()
					.setQuantity(1L)
					.setPrice(price.getId())
					.build()
			).build();

		try {
			Session session = Session.create(params);
			URI uri = URI.create(session.getUrl());
			return Optional.of(uri);
		} catch (StripeException e) {
			logger.error(
				"Error while creating stripe session for user '{}' (product: '{}').",
				userId,
				price.getProduct(),
				e
			);
			return Optional.empty();
		}
	}
}
