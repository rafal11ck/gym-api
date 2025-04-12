package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.entities.TargetMuscle;

import java.util.List;

public interface TargetMuscleService {
	List<TargetMuscle> listTargetMuscles();

	TargetMuscle createTargetMuscle(TargetMuscle targetMuscleToCreate);
}
