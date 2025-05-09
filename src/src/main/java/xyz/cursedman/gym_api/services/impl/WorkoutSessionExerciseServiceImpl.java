package xyz.cursedman.gym_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionExerciseDto;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionExerciseRequest;
import xyz.cursedman.gym_api.domain.entities.WorkoutSession;
import xyz.cursedman.gym_api.domain.entities.WorkoutSessionExercise;
import xyz.cursedman.gym_api.mappers.WorkoutSessionExerciseMapper;
import xyz.cursedman.gym_api.repositories.WorkoutSessionExerciseRepository;
import xyz.cursedman.gym_api.services.ExerciseService;
import xyz.cursedman.gym_api.services.WorkoutSessionExerciseService;

import java.util.List;
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
    public WorkoutSessionExerciseDto createWorkoutSessionExercise(
            WorkoutSessionExerciseRequest request,
            WorkoutSession workoutSession
    ) {
        WorkoutSessionExercise exercise = WorkoutSessionExercise.builder()
            .workoutSession(workoutSession)
            .exercise(exerciseService.getExerciseByUuid(request.getExerciseUuid()))
            .reps(request.getReps())
            .exerciseOrder(request.getExerciseOrder())
            .build();

        WorkoutSessionExercise savedExercise = workoutSessionExerciseRepository.save(exercise);

        return workoutSessionExerciseMapper.toDtoFromEntity(savedExercise);
    }

    @Override
    public void deleteWorkoutSessionExercise(UUID workoutSessionExerciseId) {
        if (!workoutSessionExerciseRepository.existsById(workoutSessionExerciseId)) {
            throw new EntityNotFoundException();
        }

        workoutSessionExerciseRepository.deleteById(workoutSessionExerciseId);
    }
}
