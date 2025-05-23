package xyz.cursedman.gym_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.cursedman.gym_api.domain.entities.TargetMuscle;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TargetMuscleRepository extends JpaRepository<TargetMuscle, UUID> {

	boolean existsByNameIgnoreCase(String name);

	Optional<TargetMuscle> findByNameIgnoreCase(String name);
}
