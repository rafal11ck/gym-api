package xyz.cursedman.gym_api.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.payment.PaymentDto;
import xyz.cursedman.gym_api.domain.entities.Payment;
import xyz.cursedman.gym_api.domain.entities.PaymentExternalRefType;
import xyz.cursedman.gym_api.domain.entities.PaymentStatusEnum;
import xyz.cursedman.gym_api.exceptions.NotFoundException;
import xyz.cursedman.gym_api.mappers.PaymentMapper;
import xyz.cursedman.gym_api.repositories.PaymentRepository;
import xyz.cursedman.gym_api.services.PaymentProvider;
import xyz.cursedman.gym_api.services.PaymentService;
import xyz.cursedman.gym_api.services.PaymentStatusHandler;

import java.util.List;
import java.util.UUID;

/**
 * Service that handles business logic of payments, not payment handling itself
 *
 * @see PaymentProvider
 */
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;

	private final List<PaymentStatusHandler> handlers;

	private final PaymentMapper paymentMapper;

	@Transactional
	public void changePaymentStatus(UUID paymentId, PaymentStatusEnum newStatus) {
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

	@Override
	public List<PaymentDto> listPaymentsForExternalRef(UUID externalRefId, PaymentExternalRefType refType) {
		return paymentRepository
			.findByExternalRefIdEqualsAndExternalRefTypeEquals(externalRefId, refType)
			.stream()
			.map(paymentMapper::toPaymentDto)
			.toList();
	}
}
