package xyz.cursedman.gym_api.mappers;

import lombok.AllArgsConstructor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.cursedman.gym_api.domain.dtos.CreateExerciseRequest;
import xyz.cursedman.gym_api.domain.dtos.ExerciseDto;
import xyz.cursedman.gym_api.domain.entities.Exercise;
import xyz.cursedman.gym_api.domain.entities.TargetMuscle;
import xyz.cursedman.gym_api.services.TargetMuscleService;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = TargetMuscleService.class)
public interface ExerciseMapper {


	ExerciseDto toDto(Exercise exercise);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Exercise toEntity(CreateExerciseRequest request);

}
