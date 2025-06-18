package xyz.cursedman.gym_api.mappers;

import org.mapstruct.*;

//@Deprecated(forRemoval = true,
//	since = "initial commit of this as violates Interface separation principle and Single-Responsibility Principle")

// zajebiscie ze napisales cos czego nie wiemy zeby krzyczalo teraz erroremi w IDE na kazdym kroku

public interface EntityRequestMapper<EntityT, DtoT, RequestT> extends EntityMapper<EntityT, DtoT> {
	EntityT toEntityFromRequest(RequestT request);

	RequestT toRequestFromEntity(EntityT entity);

	void updateFromRequest(RequestT request, @MappingTarget EntityT entity);
}
