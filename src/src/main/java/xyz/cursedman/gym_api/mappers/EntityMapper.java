package xyz.cursedman.gym_api.mappers;

import xyz.cursedman.gym_api.domain.entities.User;

public interface EntityMapper<EntityT, DtoT> {
	DtoT toDto(User entity);

	EntityT toEntityFromDto(DtoT dto);
}
