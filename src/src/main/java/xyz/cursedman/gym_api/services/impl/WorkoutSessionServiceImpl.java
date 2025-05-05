package xyz.cursedman.gym_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionDto;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionRequest;
import xyz.cursedman.gym_api.domain.entities.WorkoutSession;
import xyz.cursedman.gym_api.mappers.WorkoutSessionMapper;
import xyz.cursedman.gym_api.repositories.WorkoutSessionRepository;
import xyz.cursedman.gym_api.services.WorkoutSessionService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkoutSessionServiceImpl implements WorkoutSessionService {
	private final WorkoutSessionRepository workoutSessionRepository;

	private final WorkoutSessionMapper workoutSessionMapper;

	@Override
	public List<WorkoutSessionDto> listWorkoutSessions() {
		return workoutSessionRepository.findAll().stream().map(workoutSessionMapper::toDtoFromEntity).toList();
	}

	@Override
	public WorkoutSessionDto getWorkoutSession(UUID id) throws EntityNotFoundException {
		return workoutSessionRepository
			.findById(id)
			.map(workoutSessionMapper::toDtoFromEntity)
			.orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public WorkoutSessionDto createWorkoutSession(WorkoutSessionRequest request) {
		WorkoutSession workoutSession = workoutSessionMapper.toEntityFromRequest(request);
		WorkoutSession result = workoutSessionRepository.save(workoutSession);
		return workoutSessionMapper.toDtoFromEntity(result);
	}

	@Override
	public WorkoutSessionDto patchWorkoutSession(UUID id, WorkoutSessionRequest request)
		throws EntityNotFoundException
	{
		WorkoutSession workoutSession = workoutSessionRepository
			.findById(id)
			.orElseThrow(EntityNotFoundException::new);

		workoutSessionMapper.updateFromRequest(request, workoutSession);

		WorkoutSession result = workoutSessionRepository.save(workoutSession);
		return workoutSessionMapper.toDtoFromEntity(result);
	}
}
