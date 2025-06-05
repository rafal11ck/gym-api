package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.entities.Payment;
import xyz.cursedman.gym_api.domain.entities.PaymentStatusEnum;

public interface PaymentStatusHandler {
	boolean supports(Payment payment);

	void handle(Payment payment, PaymentStatusEnum newStatus);
}
