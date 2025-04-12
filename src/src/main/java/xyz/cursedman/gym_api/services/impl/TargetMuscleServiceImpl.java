package xyz.cursedman.gym_api.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.entities.TargetMuscle;
import xyz.cursedman.gym_api.repositories.TargetMuscleRepository;
import xyz.cursedman.gym_api.services.TargetMuscleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TargetMuscleServiceImpl implements TargetMuscleService {
	TargetMuscleRepository targetMuscleRepository;

	@Override
	public List<TargetMuscle> listTargetMuscles() {
		return targetMuscleRepository.findAll();
	}
}
