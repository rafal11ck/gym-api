package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.paymentStatus.PaymentStatusDto;
import xyz.cursedman.gym_api.domain.entities.PaymentStatus;

import java.util.List;
import java.util.UUID;

public interface PaymentStatusService {
	List<PaymentStatusDto> listPaymentStatuses();

	// used by mapper
	PaymentStatus getPaymentStatusByUuid(UUID id);
}
