package xyz.cursedman.gym_api.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.entities.Exercise;
import xyz.cursedman.gym_api.exceptions.ExerciseNotFoundException;
import xyz.cursedman.gym_api.repositories.ExerciseRepository;
import xyz.cursedman.gym_api.services.ExerciseService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

	private final ExerciseRepository exerciseRepository;

	@Override
	public List<Exercise> listExercises() {
		return exerciseRepository.findAll();
	}

	@Override
	public void deleteExercise(UUID id) {
		exerciseRepository.deleteById(id);
	}

	@Override
	public Exercise createExercise(Exercise exercise) {
		return exerciseRepository.save(exercise);
	}

	@Override
	public Exercise getExerciseByUuid(UUID id) {
		return exerciseRepository.findById(id).orElseThrow(
			() -> new ExerciseNotFoundException("Exercise with ID " + id + " not found")
		);
	}
}

