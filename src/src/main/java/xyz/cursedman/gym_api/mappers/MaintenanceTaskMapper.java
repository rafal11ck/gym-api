package xyz.cursedman.gym_api.mappers;

import org.mapstruct.*;
import xyz.cursedman.gym_api.domain.dtos.maintenanceTask.MaintenanceTaskDto;
import xyz.cursedman.gym_api.domain.dtos.maintenanceTask.MaintenanceTaskRequest;
import xyz.cursedman.gym_api.domain.entities.MaintenanceTask;
import xyz.cursedman.gym_api.services.HallService;
import xyz.cursedman.gym_api.services.UserService;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	uses = {  UserService.class, HallService.class }
)
public interface MaintenanceTaskMapper extends EntityRequestMapper<
	MaintenanceTask, MaintenanceTaskDto, MaintenanceTaskRequest
> {
	@Override
	@Mapping(target = "maintainerUuid", source = "maintainer.uuid")
	@Mapping(target = "hallUuid", source = "maintenanceHall.uuid")
	MaintenanceTaskRequest toRequestFromEntity(MaintenanceTask entity);

	@Override
	@Mapping(target = "maintainer", source = "maintainerUuid")
	@Mapping(target = "maintenanceHall", source = "hallUuid")
	MaintenanceTask toEntityFromRequest(MaintenanceTaskRequest request);

	@Override
	@Mapping(target = "maintainer", source = "maintainerUuid")
	@Mapping(target = "maintenanceHall", source = "hallUuid")
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromRequest(MaintenanceTaskRequest request, @MappingTarget MaintenanceTask entity);
}
