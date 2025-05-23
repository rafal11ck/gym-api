package xyz.cursedman.gym_api.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.userRole.UserRoleDto;
import xyz.cursedman.gym_api.domain.entities.UserRole;
import xyz.cursedman.gym_api.exceptions.NotFoundException;
import xyz.cursedman.gym_api.mappers.UserRoleMapper;
import xyz.cursedman.gym_api.repositories.UserRoleRepository;
import xyz.cursedman.gym_api.services.UserRoleService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

	private final UserRoleMapper userRoleMapper;

	private final UserRoleRepository userRoleRepository;

	@Override
	public List<UserRoleDto> listUserRoles() {
		return userRoleRepository.findAll().stream().map(userRoleMapper::toDtoFromEntity).toList();
	}

	@Override
	public UserRoleDto findByName(String name) {
		Optional<UserRole> userRole = userRoleRepository.findByRoleNameEqualsIgnoreCase(name);

		if (userRole.isEmpty()) {
			throw new NotFoundException("role " + name + " not found");
		}

		return userRoleMapper.toDtoFromEntity(userRole.get());
	}

	@Override
	public UserRole getUserRoleByUuid(UUID id) {
		return userRoleRepository.findById(id).orElseThrow(
			() -> new NotFoundException("User role with ID " + id + " not found")
		);
	}
}
