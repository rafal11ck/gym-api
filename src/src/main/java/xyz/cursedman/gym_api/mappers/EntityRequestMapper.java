package xyz.cursedman.gym_api.mappers;

import org.mapstruct.*;

public interface EntityRequestMapper<EntityT, DtoT, RequestT> extends EntityMapper<EntityT, DtoT> {
	EntityT toEntityFromRequest(RequestT request);

	RequestT toRequestFromEntity(EntityT entity);

	void updateFromRequest(RequestT request, @MappingTarget EntityT entity);
}
