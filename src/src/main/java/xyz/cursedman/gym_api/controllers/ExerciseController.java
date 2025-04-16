package xyz.cursedman.gym_api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.domain.dtos.exercise.CreateExerciseRequest;
import xyz.cursedman.gym_api.domain.dtos.exercise.ExerciseDto;
import xyz.cursedman.gym_api.domain.entities.Exercise;
import xyz.cursedman.gym_api.mappers.ExerciseMapper;
import xyz.cursedman.gym_api.services.ExerciseService;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(path = "/exercises")
@RequiredArgsConstructor
public class ExerciseController {

	private final ExerciseService exerciseService;
	private final ExerciseMapper exerciseMapper;

	@GetMapping
	public ResponseEntity<List<ExerciseDto>> listExercises() {
		List<ExerciseDto> exercises = exerciseService.listExercises()
			.stream().map(exerciseMapper::toDto).toList();

		return ResponseEntity.ok(exercises);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deleteExercise(@PathVariable UUID id) {
		exerciseService.deleteExercise(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<ExerciseDto> createExercise(@Valid @RequestBody CreateExerciseRequest request) {
		Exercise exercise = exerciseMapper.toEntity(request);
		Exercise savedExercise = exerciseService.createExercise(exercise);
		return ResponseEntity.ok(exerciseMapper.toDto(savedExercise));
	}

}
