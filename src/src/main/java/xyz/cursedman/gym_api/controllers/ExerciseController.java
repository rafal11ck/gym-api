package xyz.cursedman.gym_api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.domain.dtos.exercise.CreateExerciseRequest;
import xyz.cursedman.gym_api.domain.dtos.exercise.ExerciseDto;
import xyz.cursedman.gym_api.services.ExerciseService;

import java.util.UUID;


@RestController
@RequestMapping(path = "/exercises")
@RequiredArgsConstructor
public class ExerciseController {

	private final ExerciseService exerciseService;

	@GetMapping
	public ResponseEntity<Page<ExerciseDto>> listExercises(@ParameterObject Pageable pageable) {
		return ResponseEntity.ok(exerciseService.listExercises(pageable));
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deleteExercise(@PathVariable UUID id) {
		exerciseService.deleteExercise(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<ExerciseDto> createExercise(@Valid @RequestBody CreateExerciseRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(exerciseService.createExercise(request));
	}
}
