package xyz.cursedman.gym_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import xyz.cursedman.gym_api.domain.dtos.userRole.UserRoleDto;
import xyz.cursedman.gym_api.domain.entities.UserRole;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserRoleMapper implements EntityMapper<UserRole, UserRoleDto> {

}
