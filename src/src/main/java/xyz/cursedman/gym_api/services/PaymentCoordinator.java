package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.entities.Payment;

/**
 * Class that uses abstracted away paymentProvider for payments and handles coordination
 * between business logic and implementations
 *
 * @see PaymentService
 * @see PaymentProvider
 */
public class PaymentCoordinator {
	PaymentProvider paymentProvider;
	PaymentService paymentService;


	void handlePayment(Payment payment) {
		var result = paymentProvider.processPayment(payment);
		paymentService.paymentStatusChange(payment.getPaymentId(), result);
	}
}
