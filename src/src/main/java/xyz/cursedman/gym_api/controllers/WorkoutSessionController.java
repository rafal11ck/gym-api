package xyz.cursedman.gym_api.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionDto;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionExerciseRequest;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionRequest;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionAttendantRequest;
import xyz.cursedman.gym_api.services.WorkoutSessionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/workout-sessions")
@RequiredArgsConstructor
public class WorkoutSessionController {
	private final WorkoutSessionService workoutSessionService;

	// workout session

	@GetMapping
	public ResponseEntity<List<WorkoutSessionDto>> listWorkoutSessions() {
		return ResponseEntity.ok(workoutSessionService.listWorkoutSessions());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<WorkoutSessionDto> getWorkoutSession(@Valid @PathVariable UUID id) {
		try {
			WorkoutSessionDto sessionDto = workoutSessionService.getWorkoutSession(id);
			return ResponseEntity.ok(sessionDto);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
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
		try {
			return ResponseEntity.ok(workoutSessionService.patchWorkoutSession(id, request));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	// session attendants

	@PostMapping("/{id}/attendants")
	public ResponseEntity<WorkoutSessionDto> addWorkoutSessionAttendant(
		@Valid @PathVariable UUID id,
		@RequestBody @Valid WorkoutSessionAttendantRequest request
	) {
		try {
			return ResponseEntity.ok(workoutSessionService.addAttendantToWorkoutSession(id, request));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}/attendants/{userId}")
	public ResponseEntity<Void> deleteWorkoutSessionAttendant(
		@Valid @PathVariable UUID id,
		@Valid @PathVariable UUID userId
	) {
		try {
			workoutSessionService.deleteWorkoutSessionAttendant(id, userId);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	// session exercises

	@PostMapping("/{id}/exercises")
	public ResponseEntity<WorkoutSessionDto> addWorkoutSessionExercise(
		@Valid @PathVariable UUID id,
		@RequestBody @Valid WorkoutSessionExerciseRequest request
	) {
		try {
			return ResponseEntity.ok(workoutSessionService.addExerciseToWorkoutSession(id, request));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}/exercises/{exerciseId}")
	public ResponseEntity<Void> deleteWorkoutSessionExercise(
		@Valid @PathVariable UUID id,
		@Valid @PathVariable UUID exerciseId
	) {
		try {
			workoutSessionService.deleteWorkoutSessionExercise(id, exerciseId);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
