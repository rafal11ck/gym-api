package xyz.cursedman.gym_api.domain.dtos.payment;

import lombok.Builder;
import lombok.Data;
import xyz.cursedman.gym_api.domain.entities.PaymentExternalRefType;
import xyz.cursedman.gym_api.domain.entities.PaymentStatusEnum;
import xyz.cursedman.gym_api.domain.entities.PaymentType;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Data
public class PaymentDto {
	UUID paymentId;

	Currency currency;

	BigDecimal price;

	PaymentType paymentType;

	PaymentStatusEnum status;

	UUID externalRefId;

	PaymentExternalRefType externalRefType;
}
