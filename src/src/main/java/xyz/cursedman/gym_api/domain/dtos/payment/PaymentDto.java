package xyz.cursedman.gym_api.domain.dtos.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.cursedman.gym_api.domain.entities.Card;
import xyz.cursedman.gym_api.domain.entities.Membership;
import xyz.cursedman.gym_api.domain.entities.PaymentStatus;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDto {
	private UUID uuid;

	private Card card;

	private PaymentStatus status;

	private Membership membership;
}
