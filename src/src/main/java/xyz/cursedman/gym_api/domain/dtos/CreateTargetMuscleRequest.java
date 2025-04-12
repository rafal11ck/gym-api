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
public class CreateTargetMuscleRequest {

		
	@NotBlank(message = "Muscle name is required")
	private String name;
}
