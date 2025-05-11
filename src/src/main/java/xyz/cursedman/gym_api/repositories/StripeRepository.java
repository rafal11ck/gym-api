package xyz.cursedman.gym_api.repositories;

import com.stripe.model.Customer;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import java.util.Optional;

public interface StripeRepository {
	Optional<Product> findProductById(String productId) throws Exception;

	Optional<Price> findPriceByProductId(String productId) throws Exception;

	Optional<Session> findSessionById(String sessionId) throws Exception;

	Product saveProduct(ProductCreateParams params) throws Exception;

	Price savePrice(PriceCreateParams params) throws Exception;

	Customer saveCustomer(CustomerCreateParams params) throws Exception;

	Session saveSession(SessionCreateParams params) throws Exception;

	Optional<Product> findFirstProductByName(String productName) throws Exception;
}
