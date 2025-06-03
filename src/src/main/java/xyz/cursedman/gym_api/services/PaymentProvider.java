package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.entities.Payment;
import xyz.cursedman.gym_api.domain.entities.PaymentStatusEnum;

/**
 * Interface used by payment implementation
 *
 * @see PaymentCoordinator
 * @see PaymentService
 */
public interface PaymentProvider {

	/**
	 * @param payment Payment to be processed
	 * @return new status of payment
	 */
	PaymentStatusEnum processPayment(Payment payment);
}
