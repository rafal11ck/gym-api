package xyz.cursedman.gym_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionAttendantRequest;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionDto;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionExerciseRequest;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionRequest;
import xyz.cursedman.gym_api.domain.entities.User;
import xyz.cursedman.gym_api.domain.entities.WorkoutSession;
import xyz.cursedman.gym_api.mappers.WorkoutSessionMapper;
import xyz.cursedman.gym_api.repositories.WorkoutSessionRepository;
import xyz.cursedman.gym_api.services.UserService;
import xyz.cursedman.gym_api.services.WorkoutSessionExerciseService;
import xyz.cursedman.gym_api.services.WorkoutSessionService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkoutSessionServiceImpl implements WorkoutSessionService {

	private final WorkoutSessionRepository workoutSessionRepository;

	private final WorkoutSessionMapper workoutSessionMapper;

	private final WorkoutSessionExerciseService workoutSessionExerciseService;

	private final UserService userService;


	@Override
	public List<WorkoutSessionDto> listWorkoutSessions() {
		return workoutSessionRepository
			.findAll()
			.stream()
			.map(workoutSessionMapper::toDtoFromEntity)
			.toList();
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
		throws EntityNotFoundException {
		WorkoutSession workoutSession = workoutSessionRepository
			.findById(id)
			.orElseThrow(EntityNotFoundException::new);

		workoutSessionMapper.updateFromRequest(request, workoutSession);

		WorkoutSession result = workoutSessionRepository.save(workoutSession);
		return workoutSessionMapper.toDtoFromEntity(result);
	}

	@Override
	public WorkoutSessionDto addAttendantToWorkoutSession(UUID workoutSessionId, WorkoutSessionAttendantRequest request)
		throws EntityNotFoundException {
		WorkoutSession session = workoutSessionRepository.findById(workoutSessionId)
			.orElseThrow(EntityNotFoundException::new);

		User user = userService.getUserByUuid(request.getUserUuid());

		session.getAttendants().add(user);
		WorkoutSession updatedSession = workoutSessionRepository.save(session);

		return workoutSessionMapper.toDtoFromEntity(updatedSession);
	}

	@Override
	public void deleteWorkoutSessionAttendant(UUID workoutSessionId, UUID userId)
		throws EntityNotFoundException {
		WorkoutSession session = workoutSessionRepository.findById(workoutSessionId)
			.orElseThrow(EntityNotFoundException::new);

		User user = userService.getUserByUuid(userId);

		boolean deleted = session.getAttendants().remove(user);
		if (!deleted) {
			throw new EntityNotFoundException();
		}

		workoutSessionRepository.save(session);
	}

	@Override
	public WorkoutSessionDto addExerciseToWorkoutSession(UUID workoutSessionId, WorkoutSessionExerciseRequest request)
		throws EntityNotFoundException {
		workoutSessionExerciseService.createWorkoutSessionExercise(request, getWorkoutSessionEntity(workoutSessionId));
		return getWorkoutSession(workoutSessionId);
	}

	@Override
	public void deleteWorkoutSessionExercise(UUID workoutSessionId, UUID exerciseId) throws EntityNotFoundException {
		if (!workoutSessionRepository.existsById(workoutSessionId)) {
			throw new EntityNotFoundException();
		}

		workoutSessionExerciseService.deleteWorkoutSessionExercise(exerciseId);
	}

	@Override
	public WorkoutSession getWorkoutSessionEntity(UUID id) {
		return workoutSessionRepository.findById(id).orElseThrow(
			() -> new EntityNotFoundException("Workout session with ID " + id + " not found")
		);
	}
}
