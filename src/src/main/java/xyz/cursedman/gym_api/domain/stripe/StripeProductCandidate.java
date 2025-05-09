package xyz.cursedman.gym_api.domain.stripe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StripeProductCandidate {
	private String name;
	private String description;
	private Long price;
}
