package xyz.cursedman.gym_api.services;

import jakarta.persistence.EntityNotFoundException;
import xyz.cursedman.gym_api.domain.dtos.payment.PaymentDto;
import xyz.cursedman.gym_api.domain.dtos.payment.PaymentRequest;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
	List<PaymentDto> listPayments();

	PaymentDto getPayment(UUID id);

	PaymentDto createPayment(PaymentRequest request);

	PaymentDto patchPayment(UUID id, PaymentRequest request);
}
