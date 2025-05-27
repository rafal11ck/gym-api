package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.userRole.UserRoleDto;
import xyz.cursedman.gym_api.domain.entities.UserRole;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UserRoleService {
	List<UserRoleDto> listUserRoles();

	UserRoleDto findByName(String name);

	/// used by mapper
	Set<UserRole> getUserRoles(Collection<String> userRoleNames);

}
