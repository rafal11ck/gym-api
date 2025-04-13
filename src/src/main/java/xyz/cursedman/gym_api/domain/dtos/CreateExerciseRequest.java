package xyz.cursedman.gym_api.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateExerciseRequest {
	String name;

	Set<TargetMuscleDto> targetMuscles = new HashSet<>();
}
