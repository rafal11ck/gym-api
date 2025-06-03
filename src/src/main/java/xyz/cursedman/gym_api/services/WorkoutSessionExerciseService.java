package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionExerciseDto;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionExerciseRequest;
import xyz.cursedman.gym_api.domain.entities.WorkoutSession;
import xyz.cursedman.gym_api.domain.entities.WorkoutSessionExercise;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface WorkoutSessionExerciseService {
    List<WorkoutSessionExerciseDto> listExercisesForWorkoutSession(UUID workoutSessionId);

		List<WorkoutSessionExercise> getExercisesFromSessionsInDateRange(
			List<WorkoutSession> sessions,
			LocalDate from,
			LocalDate to
		);

    WorkoutSessionExerciseDto createWorkoutSessionExercise(
        WorkoutSessionExerciseRequest request,
        WorkoutSession workoutSession
    );

    void deleteWorkoutSessionExercise(UUID workoutSessionExerciseId);
}
