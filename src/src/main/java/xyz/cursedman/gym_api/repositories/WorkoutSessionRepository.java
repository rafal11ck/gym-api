package xyz.cursedman.gym_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.cursedman.gym_api.domain.entities.User;
import xyz.cursedman.gym_api.domain.entities.WorkoutSession;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, UUID> {
	List<WorkoutSession> findWorkoutSessionsByAttendantsContains(User user);
}
