package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.payment.PaymentDto;
import xyz.cursedman.gym_api.domain.entities.PaymentExternalRefType;
import xyz.cursedman.gym_api.domain.entities.PaymentStatusEnum;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
	void changePaymentStatus(UUID paymentId, PaymentStatusEnum newStatus);

	public List<PaymentDto> listPaymentsForExternalRef(UUID externalRefId, PaymentExternalRefType refType);
}
