package xyz.cursedman.gym_api.services.impl;

import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.payment.PaymentDto;
import xyz.cursedman.gym_api.services.PaymentProvider;

import java.net.URI;

@Service
public class PaymentProviderImpl implements PaymentProvider {
	@Override
	public URI getPaymentUri(PaymentDto payment) {
		return URI.create("http://example.com");
	}
}
