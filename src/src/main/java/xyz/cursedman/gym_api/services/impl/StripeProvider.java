package xyz.cursedman.gym_api.services.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.MetadataStore;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.*;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionRetrieveParams;
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

@Service
@RequiredArgsConstructor
public class StripeProvider implements PaymentProvider {

	private final StripeConfig stripeConfig;

	@PostConstruct
	private void initializeStripe() {
		Stripe.apiKey = stripeConfig.getApiKey();
	}

	private String getPaymentPriceId(PaymentDto payment) {
		try {
			long amountInMinorUnits = payment.getPrice()
				.multiply(BigDecimal.valueOf(100))
				.setScale(0, RoundingMode.HALF_UP)
				.longValue();

			PriceCreateParams createParams = PriceCreateParams.builder()
				.setCurrency(payment.getCurrency().toString().toLowerCase())
				.setProductData(PriceCreateParams.ProductData.builder().setName(stripeConfig.getPaymentTitle()).build())
				.setUnitAmount(amountInMinorUnits)
				.build();

			return Price.create(createParams).getId();
		} catch (StripeException e) {
			throw new ExternalProviderException("Error while trying to create Stripe price.", e);
		}
	}

	private void fulfillCheckout(String sessionId) throws StripeException {
		SessionRetrieveParams params = SessionRetrieveParams.builder().addExpand("line_items").build();
		Session checkoutSession = Session.retrieve(sessionId, params, null);
	}

	public void handleWebhook(String payload, String sigHeader) {
		try {
			Event event = Webhook.constructEvent(payload, sigHeader, stripeConfig.getWebhookSecret());

			if ("checkout.session.completed".equals(event.getType())
				|| "checkout.session.async_payment_succeeded".equals(event.getType())
			) {
				Session sessionEvent = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
				if (sessionEvent != null) {
					fulfillCheckout(sessionEvent.getId());
				}
			}
		} catch (StripeException e) {
			throw new ExternalProviderException("Error while trying to handle Stripe webhook.", e);
		}
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

