package xyz.cursedman.gym_api.domain.dtos.membership;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MembershipRequest {
	@NotNull
	private UUID membershipTypeUuid;
}
