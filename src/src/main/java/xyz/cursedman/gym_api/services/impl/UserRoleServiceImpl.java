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
	public UserRoleDto createUserRole(String roleName) {
		// check if role doesn't exist already
		Optional<UserRole> userRole = userRoleRepository.findByRoleNameEqualsIgnoreCase(roleName);
		if (userRole.isEmpty()) {
			UserRole userRoleEntity = new UserRole();
			// if it does not exist provision it
			userRoleEntity.setRoleName(roleName);
			UserRole saved = userRoleRepository.save(userRoleEntity);
			userRole = Optional.of(saved);
		}
		return userRoleMapper.toDtoFromEntity(userRole.get());
	}

}
