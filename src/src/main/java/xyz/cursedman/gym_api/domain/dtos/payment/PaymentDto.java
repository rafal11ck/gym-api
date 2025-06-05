package xyz.cursedman.gym_api.domain.dtos.payment;

import xyz.cursedman.gym_api.domain.entities.PaymentStatusEnum;
import xyz.cursedman.gym_api.domain.entities.PaymentType;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class PaymentDto {
	UUID paymentId;

	Currency currency;

	BigDecimal amount;

	PaymentType paymentType;

	PaymentStatusEnum status;
}
