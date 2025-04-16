package xyz.cursedman.gym_api.domain.dtos.exercise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateExerciseRequest {
	String name;

	/**
	 * targetMuscles targeted by Exercise
	 */
	Set<UUID> targetMuscles = new HashSet<>();
}
