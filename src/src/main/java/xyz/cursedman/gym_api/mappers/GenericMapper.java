package xyz.cursedman.gym_api.mappers;

import org.mapstruct.*;

public interface GenericMapper<EntityT, DtoT, RequestT> {
	DtoT toDtoFromEntity(EntityT entity);

	EntityT toEntityFromDto(DtoT dto);

	EntityT toEntityFromRequest(RequestT request);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromRequestDto(RequestT request, @MappingTarget EntityT entity);
}
