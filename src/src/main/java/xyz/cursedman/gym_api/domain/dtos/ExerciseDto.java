package xyz.cursedman.gym_api.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.cursedman.gym_api.domain.entities.TargetMuscle;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDto {

	private UUID uuid;
	private String name;

	private Set<TargetMuscle> targetMuscles = new HashSet<>();
}
