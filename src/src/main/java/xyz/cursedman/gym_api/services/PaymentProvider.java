package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.payment.PaymentDto;
import xyz.cursedman.gym_api.services.impl.PaymentServiceImpl;

import java.net.URI;

/**
 * Interface used by payment implementation
 *
 * @see PaymentServiceImpl
 */
public interface PaymentProvider {

	/**
	 * @param payment dto of Payment to be processed
	 * @return new status of payment
	 */
	URI getPaymentUri(PaymentDto payment);
}
