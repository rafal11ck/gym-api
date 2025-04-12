package xyz.cursedman.gym_api.services.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.entities.TargetMuscle;
import xyz.cursedman.gym_api.repositories.TargetMuscleRepository;
import xyz.cursedman.gym_api.services.TargetMuscleService;

import java.util.List;

@Service
@AllArgsConstructor
public class TargetMuscleServiceImpl implements TargetMuscleService {
	TargetMuscleRepository targetMuscleRepository;

	@Override
	public List<TargetMuscle> listTargetMuscles() {
		return targetMuscleRepository.findAll();
	}

	@Override
	@Transactional
	public TargetMuscle createTargetMuscle(TargetMuscle targetMuscle) {
		String targetMuscleName = targetMuscle.getName();
		if (targetMuscleRepository.existsByNameIgnoreCase(targetMuscleName)) {
			throw new IllegalArgumentException("Target Muscle already exists with name "
				+ targetMuscleName);
		}

		return targetMuscleRepository.save(targetMuscle);
	}


}
