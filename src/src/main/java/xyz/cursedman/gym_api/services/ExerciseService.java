package xyz.cursedman.gym_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.cursedman.gym_api.domain.dtos.exercise.CreateExerciseRequest;
import xyz.cursedman.gym_api.domain.dtos.exercise.ExerciseDto;
import xyz.cursedman.gym_api.domain.entities.Exercise;

import java.util.UUID;

public interface ExerciseService {
	Page<ExerciseDto> listExercises(Pageable pageable);

	void deleteExercise(UUID id);

	ExerciseDto createExercise(CreateExerciseRequest request);

	// used by mapper
	Exercise getExerciseByUuid(UUID id);
}
