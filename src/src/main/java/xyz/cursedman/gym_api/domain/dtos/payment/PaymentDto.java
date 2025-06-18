package xyz.cursedman.gym_api.domain.dtos.payment;

import lombok.Data;
import xyz.cursedman.gym_api.domain.entities.PaymentStatusEnum;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Data
public class PaymentDto {
	UUID paymentId;

	Currency currency;

	BigDecimal price;

	PaymentStatusEnum status;
}
