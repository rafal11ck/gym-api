package xyz.cursedman.gym_api.mappers;

import org.mapstruct.*;

public interface GenericMapper<EntityT, DtoT, RequestT> {
	DtoT toDtoFromEntity(EntityT entity);

	EntityT toEntityFromDto(DtoT dto);

	EntityT toEntityFromRequest(RequestT request);

	RequestT toRequestFromEntity(EntityT dto);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromRequest(RequestT request, @MappingTarget EntityT entity);
}
