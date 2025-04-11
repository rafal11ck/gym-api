package xyz.cursedman.gym_api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cursedman.gym_api.domain.dtos.ExerciseDto;
import xyz.cursedman.gym_api.mappers.ExerciseMapper;
import xyz.cursedman.gym_api.services.ExerciseService;

import java.util.List;


@RestController
@RequestMapping(path = "/exercises")
@RequiredArgsConstructor
public class ExerciseController {

	private final ExerciseService exerciseService;
	private final ExerciseMapper exerciseMapper;

	@GetMapping
	public ResponseEntity<List<ExerciseDto>> listCategories() {
		List<ExerciseDto> exercises = exerciseService.listExercises()
			.stream().map(exerciseMapper::toDto).toList();

		return ResponseEntity.ok(exercises);
	}

}
