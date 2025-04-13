package xyz.cursedman.gym_api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.domain.dtos.CreateTargetMuscleRequest;
import xyz.cursedman.gym_api.domain.dtos.TargetMuscleDto;
import xyz.cursedman.gym_api.domain.entities.TargetMuscle;
import xyz.cursedman.gym_api.mappers.TargetMuscleMapper;
import xyz.cursedman.gym_api.services.TargetMuscleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/target-muscles")
@AllArgsConstructor
public class TargetMuscleController {
	private final TargetMuscleService targetMuscleService;
	private final TargetMuscleMapper targetMuscleMapper;

	@GetMapping()
	ResponseEntity<List<TargetMuscleDto>> listTargetMuscles() {
		List<TargetMuscleDto> targetMuscles = targetMuscleService.listTargetMuscles().stream()
			.map(targetMuscleMapper::toDto).toList();
		return ResponseEntity.ok(targetMuscles);
	}

	@PostMapping
	public ResponseEntity<TargetMuscleDto> createTargetMuscle(
		@Valid @RequestBody CreateTargetMuscleRequest request) {
		TargetMuscle targetMuscleToCreate = targetMuscleMapper.toEntity(request);

		TargetMuscle savedTargetMuscle = targetMuscleService.createTargetMuscle(targetMuscleToCreate);
		return new ResponseEntity<>(
			targetMuscleMapper.toDto(savedTargetMuscle)
			, HttpStatus.CREATED);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTargetMuscle(
		@Valid @PathVariable UUID id
	) {
		targetMuscleService.deleteTargetMuscle(id);
		return ResponseEntity.noContent().build();
	}

}
