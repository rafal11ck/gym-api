package xyz.cursedman.gym_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionExerciseDto;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionExerciseRequest;
import xyz.cursedman.gym_api.domain.entities.WorkoutSession;
import xyz.cursedman.gym_api.domain.entities.WorkoutSessionExercise;
import xyz.cursedman.gym_api.exceptions.NotFoundException;
import xyz.cursedman.gym_api.mappers.WorkoutSessionExerciseMapper;
import xyz.cursedman.gym_api.repositories.WorkoutSessionExerciseRepository;
import xyz.cursedman.gym_api.services.ExerciseService;
import xyz.cursedman.gym_api.services.WorkoutSessionExerciseService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkoutSessionExerciseServiceImpl implements WorkoutSessionExerciseService {

	private final WorkoutSessionExerciseRepository workoutSessionExerciseRepository;

	private final ExerciseService exerciseService;

	private final WorkoutSessionExerciseMapper workoutSessionExerciseMapper;

	@Override
	public List<WorkoutSessionExerciseDto> listExercisesForWorkoutSession(UUID workoutSessionId) {
		return workoutSessionExerciseRepository
			.findByWorkoutSession_Uuid(workoutSessionId)
			.stream()
			.map(workoutSessionExerciseMapper::toDtoFromEntity)
			.toList();
	}

	@Override
	public List<WorkoutSessionExercise> getExercisesFromSessionsInDateRange(
		List<WorkoutSession> sessions,
		LocalDate from,
		LocalDate to
	) {
		List<WorkoutSession> filteredSessions = sessions.stream()
			.filter(session -> {
				LocalDate date = session.getDate();
				return date != null && !date.isBefore(from) && !date.isAfter(to);
			})
			.toList();

		return workoutSessionExerciseRepository
			.findByWorkoutSessionIn(filteredSessions);
	}

	@Override
	public Float getExerciseMaxWeightFromSessionsInDateRange(
		UUID exerciseId,
		List<WorkoutSession> sessions,
		LocalDate from,
		LocalDate to
	) {
		List<WorkoutSessionExercise> exercises = getExercisesFromSessionsInDateRange(sessions, from, to);
		return exercises.stream()
			.filter(
				e -> e.getExercise() != null && exerciseId.equals(e.getExercise().getUuid())
			).map(WorkoutSessionExercise::getWeight)
			.filter(Objects::nonNull)
			.max(Float::compareTo)
			.orElse(0f);
	}

	@Override
	public WorkoutSessionExerciseDto createWorkoutSessionExercise(
		WorkoutSessionExerciseRequest request,
		WorkoutSession workoutSession
	) {
		WorkoutSessionExercise exercise = WorkoutSessionExercise.builder()
			.workoutSession(workoutSession)
			.exercise(exerciseService.getExerciseByUuid(request.getExerciseUuid()))
			.reps(request.getReps())
			.weight(request.getWeight())
			.exerciseOrder(request.getExerciseOrder())
			.build();

		WorkoutSessionExercise savedExercise = workoutSessionExerciseRepository.save(exercise);

		return workoutSessionExerciseMapper.toDtoFromEntity(savedExercise);
	}

	@Override
	public void deleteWorkoutSessionExercise(UUID workoutSessionExerciseId) {
		if (!workoutSessionExerciseRepository.existsById(workoutSessionExerciseId)) {
			throw new NotFoundException();
		}

		workoutSessionExerciseRepository.deleteById(workoutSessionExerciseId);
	}
}
