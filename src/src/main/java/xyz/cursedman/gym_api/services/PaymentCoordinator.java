package xyz.cursedman.gym_api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
@Service
@RequiredArgsConstructor
public class PaymentCoordinator {
	PaymentProvider paymentProvider;
	PaymentService paymentService;
	PaymentMapper paymentMapper;

	public void notifyPaymentStatusChange(UUID paymentId, PaymentStatusEnum newStatus) {
		paymentService.paymentStatusChange(paymentId, newStatus);
	}

	public URI getPaymentUri(Payment payment) {
		return paymentProvider.getPaymentUri(paymentMapper.toPaymentDto(payment));
	}
}
