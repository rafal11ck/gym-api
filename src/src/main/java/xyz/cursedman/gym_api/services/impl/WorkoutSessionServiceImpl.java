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
import xyz.cursedman.gym_api.exceptions.NotFoundException;
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
	public WorkoutSessionDto getWorkoutSession(UUID id) {
		return workoutSessionRepository
			.findById(id)
			.map(workoutSessionMapper::toDtoFromEntity)
			.orElseThrow(() -> new NotFoundException("Workout session with id " + id + " not found"));
	}

	@Override
	public WorkoutSessionDto createWorkoutSession(WorkoutSessionRequest request) {
		WorkoutSession workoutSession = workoutSessionMapper.toEntityFromRequest(request);
		WorkoutSession result = workoutSessionRepository.save(workoutSession);
		return workoutSessionMapper.toDtoFromEntity(result);
	}

	@Override
	public WorkoutSessionDto patchWorkoutSession(UUID id, WorkoutSessionRequest request)
		 {
		WorkoutSession workoutSession = workoutSessionRepository
			.findById(id)
			.orElseThrow(() -> new NotFoundException("Workout session with id " + id + " not found"));

		workoutSessionMapper.updateFromRequest(request, workoutSession);

		WorkoutSession result = workoutSessionRepository.save(workoutSession);
		return workoutSessionMapper.toDtoFromEntity(result);
	}

	@Override
	public WorkoutSessionDto addAttendantToWorkoutSession
		(UUID workoutSessionId, WorkoutSessionAttendantRequest request) {
		WorkoutSession session = workoutSessionRepository.findById(workoutSessionId)
			.orElseThrow(() -> new NotFoundException("Workout session with id " + workoutSessionId + " not found"));

		User user = userService.getUserByUuid(request.getUserUuid());

		session.getAttendants().add(user);
		WorkoutSession updatedSession = workoutSessionRepository.save(session);

		return workoutSessionMapper.toDtoFromEntity(updatedSession);
	}

	@Override
	public void deleteWorkoutSessionAttendant(UUID workoutSessionId, UUID userId) {
		WorkoutSession session = workoutSessionRepository.findById(workoutSessionId)
			.orElseThrow(() -> new NotFoundException("Workout session with id " + workoutSessionId + " not found"));

		User user = userService.getUserByUuid(userId);

		boolean deleted = session.getAttendants().remove(user);
		if (!deleted) {
			throw new NotFoundException("User with id " + userId + " not found");
		}

		workoutSessionRepository.save(session);
	}

	@Override
	public WorkoutSessionDto addExerciseToWorkoutSession(UUID workoutSessionId, WorkoutSessionExerciseRequest request) {
		if(!workoutSessionRepository.existsById(workoutSessionId)) {
			throw new NotFoundException("Workout session with id " + workoutSessionId + " not found");
		}
		workoutSessionExerciseService.createWorkoutSessionExercise(request, getWorkoutSessionEntity(workoutSessionId));
		return getWorkoutSession(workoutSessionId);
	}

	@Override
	public void deleteWorkoutSessionExercise(UUID workoutSessionId, UUID exerciseId) throws EntityNotFoundException {
		if (!workoutSessionRepository.existsById(workoutSessionId)) {
			throw new NotFoundException("Workout session with id " + workoutSessionId + " not found");
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
