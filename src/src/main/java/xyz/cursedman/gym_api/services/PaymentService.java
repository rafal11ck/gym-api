package xyz.cursedman.gym_api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.entities.Payment;
import xyz.cursedman.gym_api.domain.entities.PaymentStatusEnum;
import xyz.cursedman.gym_api.exceptions.NotFoundException;
import xyz.cursedman.gym_api.repositories.PaymentRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
/**
 * Service that handles bussiness logic of payments, not payment handling itself
 * @see PaymentProvider
 * @see PaymentCoordinator
 */
public class PaymentService {

	private final PaymentRepository paymentRepository;
	private final List<PaymentStatusHandler> handlers;

	@Transactional
	public void paymentStatusChange(UUID paymentId, PaymentStatusEnum newStatus) {
		Payment payment = paymentRepository.findById(paymentId)
			.orElseThrow(() -> new NotFoundException("Payment not found"));

		payment.setStatus(newStatus);
		paymentRepository.save(payment);

		// Delegate to all handlers that support this payment
		for (PaymentStatusHandler handler : handlers) {
			if (handler.supports(payment)) {
				handler.handle(payment, newStatus);
			}
		}
	}
}
