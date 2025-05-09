package xyz.cursedman.gym_api.services;

import jakarta.persistence.EntityNotFoundException;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionAttendantRequest;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionDto;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionExerciseRequest;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionRequest;
import xyz.cursedman.gym_api.domain.entities.WorkoutSession;

import java.util.List;
import java.util.UUID;

public interface WorkoutSessionService {
	List<WorkoutSessionDto> listWorkoutSessions();

	WorkoutSessionDto getWorkoutSession(UUID id) throws EntityNotFoundException;

	WorkoutSessionDto createWorkoutSession(WorkoutSessionRequest request);

	WorkoutSessionDto patchWorkoutSession(UUID id, WorkoutSessionRequest request) throws EntityNotFoundException;

	WorkoutSessionDto addAttendantToWorkoutSession(UUID workoutSessionId, WorkoutSessionAttendantRequest request)
		throws EntityNotFoundException;

	void deleteWorkoutSessionAttendant(UUID workoutSessionId, UUID userId)
		throws EntityNotFoundException;

	WorkoutSessionDto addExerciseToWorkoutSession(UUID workoutSessionId, WorkoutSessionExerciseRequest request)
		throws EntityNotFoundException;

	void deleteWorkoutSessionExercise(UUID workoutSessionId, UUID exerciseId)
		throws EntityNotFoundException;

	// used by mapper
	WorkoutSession getWorkoutSessionByUuid(UUID id);
}
