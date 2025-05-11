package xyz.cursedman.gym_api.repositories.impl;

import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.param.*;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionRetrieveParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xyz.cursedman.gym_api.config.StripeProperties;
import xyz.cursedman.gym_api.repositories.StripeRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StripeRepositoryImpl implements StripeRepository {

	private final StripeProperties stripeProperties;

	@Override
	public Optional<Product> findProductById(String productId) throws Exception {
		ProductListParams params = ProductListParams.builder().build();
		ProductCollection products = Product.list(params);
		return products.getData()
			.stream()
			.filter(product -> product.getId().equals(productId))
			.findFirst();
	}

	@Override
	public Optional<Price> findPriceByProductId(String productId) throws Exception {
		PriceListParams params = PriceListParams.builder().build();
		PriceCollection prices = Price.list(params);
		return prices.getData()
			.stream()
			.filter(price -> price.getProduct().equals(productId))
			.findFirst();
	}

	@Override
	public Optional<Session> findSessionById(String sessionId) throws Exception {
		SessionRetrieveParams params = SessionRetrieveParams.builder().addExpand("line_items").build();
		Session checkoutSession = Session.retrieve(sessionId, params, null);

		if (checkoutSession == null) {
			return Optional.empty();
		} else {
			return Optional.of(checkoutSession);
		}
	}

	@Override
	public Optional<Product> findFirstProductByName(String productName) throws Exception {
		ProductListParams params = ProductListParams.builder().build();
		ProductCollection products = Product.list(params);
		return products.getData()
			.stream()
			.filter(product -> product.getName().equals(productName))
			.findFirst();
	}

	@Override
	public Product saveProduct(ProductCreateParams params) throws Exception {
		return Product.create(params);
	}

	@Override
	public Price savePrice(PriceCreateParams params) throws Exception {
		return Price.create(params);
	}

	@Override
	public Session saveSession(SessionCreateParams params) throws Exception {
		return Session.create(params);
	}

	@Override
	public Customer saveCustomer(CustomerCreateParams params) throws Exception {
		return Customer.create(params);
	}
}
