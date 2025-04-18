package xyz.cursedman.gym_api.domain.dtos.membershipType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MembershipTypeRequest {
	private String type;
	private Float price;
}
