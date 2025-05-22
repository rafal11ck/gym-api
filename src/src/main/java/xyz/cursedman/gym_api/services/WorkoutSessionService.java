package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionAttendantRequest;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionDto;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionExerciseRequest;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionRequest;
import xyz.cursedman.gym_api.domain.entities.WorkoutSession;

import java.util.List;
import java.util.UUID;

public interface WorkoutSessionService {
	List<WorkoutSessionDto> listWorkoutSessions();

	WorkoutSessionDto getWorkoutSession(UUID id);

	WorkoutSessionDto createWorkoutSession(WorkoutSessionRequest request);

	WorkoutSessionDto patchWorkoutSession(UUID id, WorkoutSessionRequest request);

	WorkoutSessionDto addAttendantToWorkoutSession(UUID workoutSessionId, WorkoutSessionAttendantRequest request);

	void deleteWorkoutSessionAttendant(UUID workoutSessionId, UUID userId);

	WorkoutSessionDto addExerciseToWorkoutSession(UUID workoutSessionId, WorkoutSessionExerciseRequest request);

	void deleteWorkoutSessionExercise(UUID workoutSessionId, UUID exerciseId);

	// used by mapper
	WorkoutSession getWorkoutSessionEntity(UUID id);
}
