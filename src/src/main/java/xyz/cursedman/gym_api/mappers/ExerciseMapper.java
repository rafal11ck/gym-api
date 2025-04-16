package xyz.cursedman.gym_api.mappers;

import org.mapstruct.*;
import xyz.cursedman.gym_api.domain.dtos.exercise.CreateExerciseRequest;
import xyz.cursedman.gym_api.domain.dtos.exercise.ExerciseDto;
import xyz.cursedman.gym_api.domain.entities.Exercise;
import xyz.cursedman.gym_api.services.TargetMuscleService;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = TargetMuscleService.class)
public interface ExerciseMapper {


	ExerciseDto toDto(Exercise exercise);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Exercise toEntity(CreateExerciseRequest request);

}
