package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.entities.TargetMuscle;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TargetMuscleService {
	List<TargetMuscle> listTargetMuscles();

	TargetMuscle createTargetMuscle(TargetMuscle targetMuscleToCreate);

	void deleteTargetMuscle(UUID id);

	Set<TargetMuscle> findAllById(Set<UUID> ids);
}
