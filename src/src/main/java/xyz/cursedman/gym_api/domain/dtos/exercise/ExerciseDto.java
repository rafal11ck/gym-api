package xyz.cursedman.gym_api.domain.dtos.exercise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.cursedman.gym_api.domain.dtos.targetMuscle.TargetMuscleDto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ExerciseDto {

	private UUID uuid;
	private String name;

	private Set<TargetMuscleDto> targetMuscles = new HashSet<>();
}
