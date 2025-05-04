package xyz.cursedman.gym_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import xyz.cursedman.gym_api.domain.dtos.hallType.HallTypeDto;
import xyz.cursedman.gym_api.domain.entities.HallType;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HallTypeMapper extends EntityMapper<HallType, HallTypeDto> {
}
