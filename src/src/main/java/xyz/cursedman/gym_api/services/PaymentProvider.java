package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.payment.PaymentDto;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Interface used by payment implementation
 *
 * @see PaymentCoordinator
 * @see PaymentService
 */
public interface PaymentProvider {

	/**
	 * @param payment dto of Payment to be processed
	 * @return new status of payment
	 */
	URI getPaymentUri(PaymentDto payment);
}
