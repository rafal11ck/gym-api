package xyz.cursedman.gym_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionAttendantRequest;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionDto;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionExerciseRequest;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionRequest;
import xyz.cursedman.gym_api.domain.entities.WorkoutSession;

import java.util.List;
import java.util.UUID;

public interface WorkoutSessionService {
	Page<WorkoutSessionDto> listWorkoutSessions(Pageable pageable);

	List<WorkoutSessionDto> listUserWorkoutSessions(UUID userId);

	WorkoutSessionDto getWorkoutSession(UUID id);

	WorkoutSessionDto getUserLastWorkoutSession(UUID userId);

	WorkoutSessionDto createWorkoutSession(WorkoutSessionRequest request);

	WorkoutSessionDto patchWorkoutSession(UUID id, WorkoutSessionRequest request);

	WorkoutSessionDto addAttendantToWorkoutSession(UUID workoutSessionId, WorkoutSessionAttendantRequest request);

	void deleteWorkoutSessionAttendant(UUID workoutSessionId, UUID userId);

	WorkoutSessionDto addExerciseToWorkoutSession(UUID workoutSessionId, WorkoutSessionExerciseRequest request);

	void deleteWorkoutSessionExercise(UUID workoutSessionId, UUID exerciseId);

	List<WorkoutSession> findWorkoutSessionsByAttendantUuid(UUID userId);

	// used by mapper
	WorkoutSession getWorkoutSessionEntity(UUID id);
}
