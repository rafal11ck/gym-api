package xyz.cursedman.gym_api.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.entities.Exercise;
import xyz.cursedman.gym_api.repositories.ExerciseRepository;
import xyz.cursedman.gym_api.services.ExerciseService;

import java.util.List;

@Service
@AllArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

	private final ExerciseRepository exerciseRepository;

	@Override
	public List<Exercise> listExercises() {
		return exerciseRepository.findAll();
	}
}

