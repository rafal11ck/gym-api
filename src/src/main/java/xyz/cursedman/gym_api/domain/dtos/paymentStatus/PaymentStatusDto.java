package xyz.cursedman.gym_api.domain.dtos.paymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentStatusDto {
	private UUID uuid;
	private String status;
}
