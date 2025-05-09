package xyz.cursedman.gym_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.cursedman.gym_api.domain.entities.WorkoutSessionExercise;

import java.util.List;
import java.util.UUID;

public interface WorkoutSessionExerciseRepository extends JpaRepository<WorkoutSessionExercise, UUID> {
	List<WorkoutSessionExercise> findByWorkoutSession_Uuid(UUID workoutSessionUuid);
}
