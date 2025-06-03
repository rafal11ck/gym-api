package xyz.cursedman.gym_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.cursedman.gym_api.domain.entities.Exercise;
import xyz.cursedman.gym_api.domain.entities.WorkoutSessionExercise;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
}
