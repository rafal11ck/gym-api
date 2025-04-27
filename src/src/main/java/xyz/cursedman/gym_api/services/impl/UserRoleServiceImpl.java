package xyz.cursedman.gym_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.userRole.UserRoleDto;
import xyz.cursedman.gym_api.domain.entities.UserRole;
import xyz.cursedman.gym_api.mappers.UserRoleMapper;
import xyz.cursedman.gym_api.repositories.UserRoleRepository;
import xyz.cursedman.gym_api.services.UserRoleService;

import java.util.List;
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
	public UserRole getUserRoleByUuid(UUID id) {
		return userRoleRepository.findById(id).orElseThrow(
			() -> new EntityNotFoundException("User role with ID " + id + " not found")
		);
	}
}
