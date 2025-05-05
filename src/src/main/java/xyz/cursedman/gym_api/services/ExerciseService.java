package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.entities.Exercise;

import java.util.List;
import java.util.UUID;

public interface ExerciseService {
	List<Exercise> listExercises();

	void deleteExercise(UUID id);

	Exercise createExercise(Exercise exercise);

	// used by mapper
	Exercise getExerciseByUuid(UUID id);
}
