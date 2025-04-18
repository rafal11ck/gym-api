package xyz.cursedman.gym_api.domain.dtos.membershipType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MembershipTypeDto {
	private UUID uuid;
	private String type;
	private Float price;
}
