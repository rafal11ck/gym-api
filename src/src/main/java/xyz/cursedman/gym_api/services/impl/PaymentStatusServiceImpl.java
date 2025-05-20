package xyz.cursedman.gym_api.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.paymentStatus.PaymentStatusDto;
import xyz.cursedman.gym_api.domain.entities.PaymentStatus;
import xyz.cursedman.gym_api.exceptions.NotFoundException;
import xyz.cursedman.gym_api.mappers.PaymentStatusMapper;
import xyz.cursedman.gym_api.repositories.PaymentStatusRepository;
import xyz.cursedman.gym_api.services.PaymentStatusService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentStatusServiceImpl implements PaymentStatusService {

	private final PaymentStatusRepository paymentStatusRepository;

	private final PaymentStatusMapper paymentStatusMapper;

	@Override
	public List<PaymentStatusDto> listPaymentStatuses() {
		return paymentStatusRepository
			.findAll()
			.stream()
			.map(paymentStatusMapper::toDto)
			.toList();
	}

	@Override
	public PaymentStatus getPaymentStatusByUuid(UUID id) {
		return paymentStatusRepository.findById(id).orElseThrow(
			() -> new NotFoundException("Payment status with ID " + id + " not found")
		);
	}
}
