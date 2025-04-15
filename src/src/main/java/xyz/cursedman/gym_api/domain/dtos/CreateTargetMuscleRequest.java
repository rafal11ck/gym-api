package xyz.cursedman.gym_api.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public final class CreateTargetMuscleRequest {

	/**
	 * Name of target muscle to be created.
	 */
	@NotBlank(message = "Muscle name is required")
	private String name;
}
