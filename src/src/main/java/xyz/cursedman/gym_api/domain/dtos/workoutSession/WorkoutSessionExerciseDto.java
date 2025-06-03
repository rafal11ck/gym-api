package xyz.cursedman.gym_api.domain.dtos.workoutSession;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.cursedman.gym_api.domain.entities.Exercise;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionExerciseDto {
	private UUID uuid;

	private Exercise exercise;

	private Integer reps;

	private Float weight;

	private Integer exerciseOrder;
}
