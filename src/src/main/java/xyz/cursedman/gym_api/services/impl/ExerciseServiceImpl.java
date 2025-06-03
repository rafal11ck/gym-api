package xyz.cursedman.gym_api.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.exercise.CreateExerciseRequest;
import xyz.cursedman.gym_api.domain.dtos.exercise.ExerciseDto;
import xyz.cursedman.gym_api.domain.entities.Exercise;
import xyz.cursedman.gym_api.exceptions.NotFoundException;
import xyz.cursedman.gym_api.mappers.ExerciseMapper;
import xyz.cursedman.gym_api.repositories.ExerciseRepository;
import xyz.cursedman.gym_api.services.ExerciseService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

	private final ExerciseRepository exerciseRepository;

	private final ExerciseMapper exerciseMapper;

	@Override
	public List<ExerciseDto> listExercises() {
		return exerciseRepository.findAll().stream().map(exerciseMapper::toDto).toList();
	}

	@Override
	public void deleteExercise(UUID id) {
		exerciseRepository.deleteById(id);
	}

	@Override
	public ExerciseDto createExercise(CreateExerciseRequest request) {
		Exercise exercise = exerciseMapper.toEntity(request);
		Exercise savedExercise = exerciseRepository.save(exercise);
		return exerciseMapper.toDto(savedExercise);
	}

	@Override
	public Exercise getExerciseByUuid(UUID id) {
		return exerciseRepository.findById(id).orElseThrow(
			() -> new NotFoundException("Exercise with ID " + id + " not found"));
	}
}

