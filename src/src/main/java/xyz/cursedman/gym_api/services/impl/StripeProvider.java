package xyz.cursedman.gym_api.services.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.MetadataStore;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.*;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.config.StripeConfig;
import xyz.cursedman.gym_api.domain.dtos.payment.PaymentDto;
import xyz.cursedman.gym_api.exceptions.ExternalProviderException;
import xyz.cursedman.gym_api.services.PaymentProvider;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StripeProvider implements PaymentProvider {

	private final StripeConfig stripeConfig;

	@PostConstruct
	private void initializeStripe() {
		Stripe.apiKey = stripeConfig.getApiKey();
	}

	public record StripeMetadata(String key, String value) {
		public <T extends MetadataStore<T>> boolean matches(MetadataStore<T> store) {
			return value.equals(store.getMetadata().get(key));
		}
	}

	private boolean retrieveProductAndCheckActive(String productId) {
		try {
			return Product.retrieve(productId).getActive().equals(true);
		} catch (StripeException e) {
			throw new ExternalProviderException("Error while trying to retrieve Stripe product.", e);
		}
	}

	private Optional<Price> findPriceByMetadata(StripeMetadata metadata) {
		try {
			List<Price> prices = Price.list(PriceListParams.builder().build()).getData();
			return prices.stream()
				.filter(price -> price.getActive().equals(true))
				.filter(price -> retrieveProductAndCheckActive(price.getProduct()))
				.filter(metadata::matches)
				.findFirst();
		} catch (StripeException e) {
			throw new ExternalProviderException("Error while trying to get Stripe prices.", e);
		}
	}

	private Optional<Product> findProductByMetadata(StripeMetadata metadata) {
		try {
			List<Product> products = Product.list(ProductListParams.builder().build()).getData();
			return products.stream()
				.filter(product -> product.getActive().equals(true))
				.filter(metadata::matches)
				.findFirst();
		} catch (StripeException e) {
			throw new ExternalProviderException("Error while trying to get Stripe products.", e);
		}
	}

	private Product createProductWithMetadata(String name, StripeMetadata metadata) {
		try {
			ProductCreateParams createParams = ProductCreateParams.builder()
				.setName(name)
				.putMetadata(metadata.key(), metadata.value())
				.build();

			return Product.create(createParams);
		} catch (StripeException e) {
			throw new ExternalProviderException(
				"Error while trying to create Stripe product \"" + metadata.value() + "\"", e);
		}
	}

	private Price createProductPriceForPayment(Product product, PaymentDto payment, StripeMetadata metadata) {
		try {
			long amountInMinorUnits = payment.getPrice()
				.multiply(BigDecimal.valueOf(100))
				.setScale(0, RoundingMode.HALF_UP)
				.longValue();

			PriceCreateParams createParams = PriceCreateParams.builder()
				.setCurrency(payment.getCurrency().toString().toLowerCase())
				.setProduct(product.getId())
				.setUnitAmount(amountInMinorUnits)
				.putMetadata(metadata.key(), metadata.value())
				.build();

			return Price.create(createParams);
		} catch (StripeException e) {
			throw new ExternalProviderException("Error while trying to create Stripe price.", e);
		}
	}

	private Price createPriceForPaymentWithExistingOrNewProduct(PaymentDto payment, StripeMetadata metadata) {
		return findProductByMetadata(metadata)
			.map(product -> createProductPriceForPayment(product, payment, metadata))
			.orElseGet(() -> {
				Product createdProduct = createProductWithMetadata(payment.getExternalRefType().toString(), metadata);
				return createProductPriceForPayment(createdProduct, payment, metadata);
			});
	}

	private String getPaymentPriceId(PaymentDto payment) {
		StripeMetadata metadata = new StripeMetadata(
			stripeConfig.getMetadataKey(),
			payment.getExternalRefId().toString()
		);

		return findPriceByMetadata(metadata)
			.map(Price::getId)
			.orElseGet(() -> createPriceForPaymentWithExistingOrNewProduct(payment, metadata).getId());
	}

	@Override
	public URI getPaymentUri(PaymentDto payment) {
		SessionCreateParams sessionCreateParams = SessionCreateParams.builder()
			.setMode(SessionCreateParams.Mode.PAYMENT)
			.setSuccessUrl(stripeConfig.getSuccessRedirectUrl())
			.setCancelUrl(stripeConfig.getCancelRedirectUrl())
			.addLineItem(
				SessionCreateParams.LineItem.builder()
					.setQuantity(1L)
					.setPrice(getPaymentPriceId(payment))
					.build()
			)
			.build();

		try {
			Session createdSession = Session.create(sessionCreateParams);
			return new URI(createdSession.getUrl());
		} catch (Exception e) {
			throw new ExternalProviderException("Failed to create Stripe session.", e);
		}
	}
}

