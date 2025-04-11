package xyz.cursedman.gym_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import xyz.cursedman.gym_api.domain.dtos.ExerciseDto;
import xyz.cursedman.gym_api.domain.entities.Exercise;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExerciseMapper {

	ExerciseDto toDto(Exercise exercise);

}
