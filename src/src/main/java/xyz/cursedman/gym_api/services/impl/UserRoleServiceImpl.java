package xyz.cursedman.gym_api.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.userRole.UserRoleDto;
import xyz.cursedman.gym_api.mappers.UserRoleMapper;
import xyz.cursedman.gym_api.repositories.UserRoleRepository;
import xyz.cursedman.gym_api.services.UserRoleService;

import java.util.List;

@Service
@AllArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

	private final UserRoleMapper userRoleMapper;

	private final UserRoleRepository userRoleRepository;

	@Override
	public List<UserRoleDto> listUserRoles() {
		return userRoleRepository.findAll().stream().map(userRoleMapper::toDtoFromEntity).toList();
	}
}
