package xyz.cursedman.gym_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionDto;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionExerciseRequest;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionRequest;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionUserRequest;
import xyz.cursedman.gym_api.domain.entities.Exercise;
import xyz.cursedman.gym_api.domain.entities.User;
import xyz.cursedman.gym_api.domain.entities.WorkoutSession;
import xyz.cursedman.gym_api.mappers.WorkoutSessionMapper;
import xyz.cursedman.gym_api.repositories.ExerciseRepository;
import xyz.cursedman.gym_api.repositories.UserRepository;
import xyz.cursedman.gym_api.repositories.WorkoutSessionRepository;
import xyz.cursedman.gym_api.services.WorkoutSessionService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkoutSessionServiceImpl implements WorkoutSessionService {

	private final WorkoutSessionRepository workoutSessionRepository;

	private final WorkoutSessionMapper workoutSessionMapper;

	private final UserRepository userRepository;

	private final ExerciseRepository exerciseRepository;

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

	@Override
	public WorkoutSessionDto addAttendantToWorkoutSession(UUID workoutSessionId, WorkoutSessionUserRequest request)
		throws EntityNotFoundException
	{
		WorkoutSession session = workoutSessionRepository.findById(workoutSessionId)
			.orElseThrow(EntityNotFoundException::new);

		User user = userRepository.findById(request.getUserUuid())
			.orElseThrow(EntityNotFoundException::new);

		session.getAttendants().add(user);
		WorkoutSession updatedSession = workoutSessionRepository.save(session);

		return workoutSessionMapper.toDtoFromEntity(updatedSession);
	}

	@Override
	public void deleteWorkoutSessionAttendant(UUID workoutSessionId, UUID userId)
		throws EntityNotFoundException
	{
		WorkoutSession session = workoutSessionRepository.findById(workoutSessionId)
			.orElseThrow(EntityNotFoundException::new);

		User user = userRepository.findById(userId)
			.orElseThrow(EntityNotFoundException::new);

		boolean deleted = session.getAttendants().remove(user);
		if (!deleted) {
			throw new EntityNotFoundException();
		}

		workoutSessionRepository.save(session);
	}

	@Override
	public WorkoutSessionDto addExerciseToWorkoutSession(UUID workoutSessionId, WorkoutSessionExerciseRequest request)
		throws EntityNotFoundException
	{
		WorkoutSession session = workoutSessionRepository.findById(workoutSessionId)
			.orElseThrow(EntityNotFoundException::new);

		Exercise exercise = exerciseRepository.findById(request.getExerciseUuid())
			.orElseThrow(EntityNotFoundException::new);

		session.getExercises().add(exercise);
		WorkoutSession updatedSession = workoutSessionRepository.save(session);

		return workoutSessionMapper.toDtoFromEntity(updatedSession);
	}

	@Override
	public void deleteWorkoutSessionExercise(UUID workoutSessionId, UUID exerciseId) throws EntityNotFoundException {
		WorkoutSession session = workoutSessionRepository.findById(workoutSessionId)
			.orElseThrow(EntityNotFoundException::new);

		Exercise exercise = exerciseRepository.findById(exerciseId)
			.orElseThrow(EntityNotFoundException::new);

		boolean deleted = session.getExercises().remove(exercise);
		if (!deleted) {
			throw new EntityNotFoundException();
		}

		workoutSessionRepository.save(session);
	}
}
