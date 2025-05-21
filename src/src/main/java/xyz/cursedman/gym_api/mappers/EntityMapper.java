package xyz.cursedman.gym_api.mappers;

public interface EntityMapper<EntityT, DtoT> {
	DtoT toDtoFromEntity(EntityT entity);

	EntityT toEntityFromDto(DtoT dto);
}
