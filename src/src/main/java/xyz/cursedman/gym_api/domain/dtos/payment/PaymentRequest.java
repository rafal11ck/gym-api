package xyz.cursedman.gym_api.domain.dtos.payment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
	@NotNull
	private UUID cardUuid;

	@NotNull
	private UUID statusUuid;

	@NotNull
	private UUID membershipUuid;
}
