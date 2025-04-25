package xyz.cursedman.gym_api.domain.dtos.membershipType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MembershipTypeRequest {
	@NotNull
	private String type;

	@NotNull
	private Float price;
}
