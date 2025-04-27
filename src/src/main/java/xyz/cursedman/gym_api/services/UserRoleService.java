package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.userRole.UserRoleDto;
import xyz.cursedman.gym_api.domain.entities.UserRole;

import java.util.List;
import java.util.UUID;

public interface UserRoleService {
	List<UserRoleDto> listUserRoles();

	// used by mapper
	UserRole getUserRoleByUuid(UUID id);
}
