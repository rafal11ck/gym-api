package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionExerciseDto;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionExerciseRequest;
import xyz.cursedman.gym_api.domain.entities.WorkoutSession;

import java.util.List;
import java.util.UUID;

public interface WorkoutSessionExerciseService {
    List<WorkoutSessionExerciseDto> listExercisesForWorkoutSession(UUID workoutSessionId);

    WorkoutSessionExerciseDto createWorkoutSessionExercise(
        WorkoutSessionExerciseRequest request,
        WorkoutSession workoutSession
    );

    void deleteWorkoutSessionExercise(UUID workoutSessionExerciseId);
}
