package xyz.cursedman.gym_api.domain.dtos.workoutSession;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WorkoutSessionExerciseRequest {
	@NotNull
	private UUID exerciseUuid;

	@NotNull
	private Integer reps;

	@NotNull
	private Float weight;

	@NotNull
	private Integer exerciseOrder;
}
