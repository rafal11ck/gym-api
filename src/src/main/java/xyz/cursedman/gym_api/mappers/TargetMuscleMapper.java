package xyz.cursedman.gym_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import xyz.cursedman.gym_api.domain.dtos.targetMuscle.CreateTargetMuscleRequest;
import xyz.cursedman.gym_api.domain.dtos.targetMuscle.TargetMuscleDto;
import xyz.cursedman.gym_api.domain.entities.TargetMuscle;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TargetMuscleMapper {
	TargetMuscleDto toDto(TargetMuscle targetMuscle);

	TargetMuscle toEntity(CreateTargetMuscleRequest createTargetMuscleRequest);
}
