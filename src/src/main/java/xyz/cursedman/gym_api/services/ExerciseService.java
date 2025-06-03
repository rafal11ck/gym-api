package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.exercise.CreateExerciseRequest;
import xyz.cursedman.gym_api.domain.dtos.exercise.ExerciseDto;
import xyz.cursedman.gym_api.domain.entities.Exercise;

import java.util.List;
import java.util.UUID;

public interface ExerciseService {
	List<ExerciseDto> listExercises();

	void deleteExercise(UUID id);

	ExerciseDto createExercise(CreateExerciseRequest request);

	// used by mapper
	Exercise getExerciseByUuid(UUID id);
}
