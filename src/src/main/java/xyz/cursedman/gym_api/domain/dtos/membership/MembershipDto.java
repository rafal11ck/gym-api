package xyz.cursedman.gym_api.domain.dtos.membership;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.cursedman.gym_api.domain.entities.MembershipType;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipDto {
	private UUID uuid;

	private ZonedDateTime validFrom;

	private ZonedDateTime validUntil;

	private boolean isValid;

	private MembershipType membershipType;
}
