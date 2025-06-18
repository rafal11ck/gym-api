package xyz.cursedman.gym_api.services.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
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
import xyz.cursedman.gym_api.domain.entities.PaymentStatusEnum;
import xyz.cursedman.gym_api.exceptions.ExternalProviderException;
import xyz.cursedman.gym_api.services.PaymentProvider;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StripeProvider implements PaymentProvider {

	private final StripeConfig stripeConfig;

	private final PaymentServiceImpl paymentService;

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

	public void dispatchStripeEvent(Event event, PaymentStatusEnum newPaymentStatus) {
		Session sessionEvent = (Session) event.getDataObjectDeserializer().getObject().orElse(null);

		if (sessionEvent == null) {
			throw new ExternalProviderException("Could not deserialize Stripe session event.");
		}

		UUID paymentId = UUID.fromString(sessionEvent.getClientReferenceId());
		paymentService.changePaymentStatus(paymentId, newPaymentStatus);
	}

	public void handleWebhook(String payload, String sigHeader) {
		try {
			Event event = Webhook.constructEvent(payload, sigHeader, stripeConfig.getWebhookSecret());

			switch (event.getType()) {
				case "checkout.session.completed":
				case "checkout.session.async_payment_succeeded":
					dispatchStripeEvent(event, PaymentStatusEnum.SUCCEEDED);
					break;

				case "checkout.session.expired":
					dispatchStripeEvent(event, PaymentStatusEnum.EXPIRED);
					break;

				case "checkout.session.async_payment_failed":
					dispatchStripeEvent(event, PaymentStatusEnum.FAILED);
					break;
			}

		} catch (StripeException e) {
			throw new ExternalProviderException(
				"Error while trying to handle Stripe webhook. Is STRIPE_WEBHOOK_SECRET set?", e);
		}
	}

	@Override
	public URI getPaymentUri(PaymentDto payment) {
		SessionCreateParams sessionCreateParams = SessionCreateParams.builder()
			.setMode(SessionCreateParams.Mode.PAYMENT)
			.setClientReferenceId(payment.getPaymentId().toString())
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

