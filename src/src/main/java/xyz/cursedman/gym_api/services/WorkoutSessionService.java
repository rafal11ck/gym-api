package xyz.cursedman.gym_api.services;

import jakarta.persistence.EntityNotFoundException;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionDto;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionRequest;

import java.util.List;
import java.util.UUID;

public interface WorkoutSessionService {
	List<WorkoutSessionDto> listWorkoutSessions();

	WorkoutSessionDto getWorkoutSession(UUID id) throws EntityNotFoundException;

	WorkoutSessionDto createWorkoutSession(WorkoutSessionRequest request);

	WorkoutSessionDto patchWorkoutSession(UUID id, WorkoutSessionRequest request) throws EntityNotFoundException;
}
