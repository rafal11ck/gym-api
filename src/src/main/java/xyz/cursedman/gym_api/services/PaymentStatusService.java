package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.paymentStatus.PaymentStatusDto;

import java.util.List;

public interface PaymentStatusService {
	List<PaymentStatusDto> listPaymentStatuses();
}
