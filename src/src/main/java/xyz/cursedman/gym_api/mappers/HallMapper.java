package xyz.cursedman.gym_api.mappers;

import org.mapstruct.*;
import xyz.cursedman.gym_api.domain.dtos.hall.HallDto;
import xyz.cursedman.gym_api.domain.dtos.hall.HallRequest;
import xyz.cursedman.gym_api.domain.entities.Hall;
import xyz.cursedman.gym_api.services.HallTypeService;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = HallTypeService.class)
public interface HallMapper extends EntityRequestMapper<Hall, HallDto, HallRequest> {
	@Override
	@Mapping(target = "hallTypeUuid", source = "hallType.uuid")
	HallRequest toRequestFromEntity(Hall entity);

	@Override
	@Mapping(target = "hallType", source = "hallTypeUuid")
	Hall toEntityFromRequest(HallRequest request);

	@Override
	@Mapping(target = "hallType", source = "hallTypeUuid")
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromRequest(HallRequest request, @MappingTarget Hall entity);
}
