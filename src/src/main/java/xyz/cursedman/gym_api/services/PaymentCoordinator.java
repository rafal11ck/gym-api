package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.entities.Payment;
import xyz.cursedman.gym_api.domain.entities.PaymentStatusEnum;
import xyz.cursedman.gym_api.mappers.PaymentMapper;

import java.net.URI;
import java.util.UUID;

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
	PaymentMapper paymentMapper;

	URI getPaymentUri(Payment payment) {
		return paymentProvider.getPaymentUri(paymentMapper.toPaymentDto(payment));
	}

	void notifyPaymentStatusChange(UUID paymentId, PaymentStatusEnum newStatus) {
		paymentService.paymentStatusChange(paymentId, newStatus);
	}
}
