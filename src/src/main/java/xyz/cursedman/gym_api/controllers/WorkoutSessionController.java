package xyz.cursedman.gym_api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionAttendantRequest;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionDto;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionExerciseRequest;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionRequest;
import xyz.cursedman.gym_api.services.WorkoutSessionService;

import java.util.UUID;

@RestController
@RequestMapping(path = "/workout-sessions")
@RequiredArgsConstructor
public class WorkoutSessionController {
	private final WorkoutSessionService workoutSessionService;

	// workout session

	@GetMapping
	public ResponseEntity<Page<WorkoutSessionDto>> listWorkoutSessions(@ParameterObject Pageable pageable) {
		return ResponseEntity.ok(workoutSessionService.listWorkoutSessions(pageable));
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<WorkoutSessionDto> getWorkoutSession(@Valid @PathVariable UUID id) {
		WorkoutSessionDto sessionDto = workoutSessionService.getWorkoutSession(id);
		return ResponseEntity.ok(sessionDto);
	}

	@PostMapping
	public ResponseEntity<WorkoutSessionDto> createWorkoutSession(
		@Valid @RequestBody WorkoutSessionRequest request
	) {
		WorkoutSessionDto createdSession = workoutSessionService.createWorkoutSession(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<WorkoutSessionDto> updateCard(
		@Valid @PathVariable UUID id,
		@RequestBody WorkoutSessionRequest request
	) {
		return ResponseEntity.ok(workoutSessionService.patchWorkoutSession(id, request));
	}

	// session attendants

	@PostMapping("/{id}/attendants")
	public ResponseEntity<WorkoutSessionDto> addWorkoutSessionAttendant(
		@Valid @PathVariable UUID id,
		@RequestBody @Valid WorkoutSessionAttendantRequest request
	) {
		return ResponseEntity.ok(workoutSessionService.addAttendantToWorkoutSession(id, request));
	}

	@DeleteMapping("/{id}/attendants/{userId}")
	public ResponseEntity<Void> deleteWorkoutSessionAttendant(
		@Valid @PathVariable UUID id,
		@Valid @PathVariable UUID userId
	) {
		workoutSessionService.deleteWorkoutSessionAttendant(id, userId);
		return ResponseEntity.noContent().build();
	}

	// session exercises

	@PostMapping("/{id}/exercises")
	public ResponseEntity<WorkoutSessionDto> addWorkoutSessionExercise(
		@Valid @PathVariable UUID id,
		@RequestBody @Valid WorkoutSessionExerciseRequest request
	) {
		return ResponseEntity.ok(workoutSessionService.addExerciseToWorkoutSession(id, request));
	}

	@DeleteMapping("/{id}/exercises/{exerciseId}")
	public ResponseEntity<Void> deleteWorkoutSessionExercise(
		@Valid @PathVariable UUID id,
		@Valid @PathVariable UUID exerciseId
	) {
		workoutSessionService.deleteWorkoutSessionExercise(id, exerciseId);
		return ResponseEntity.noContent().build();
	}
}
