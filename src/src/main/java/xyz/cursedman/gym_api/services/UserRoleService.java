package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.userRole.UserRoleDto;

import java.util.List;

public interface UserRoleService {
	List<UserRoleDto> listUserRoles();
}
