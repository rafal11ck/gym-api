package xyz.cursedman.gym_api.mappers;

import org.mapstruct.*;

public interface GenericMapper<EntityT, DtoT, RequestT> {
	DtoT toDtoFromEntity(EntityT entity);

	EntityT toEntityFromDto(DtoT dto);

	EntityT toEntityFromRequest(RequestT request);

	RequestT toRequestFromEntity(EntityT entity);

	void updateFromRequest(RequestT request, @MappingTarget EntityT entity);
}
