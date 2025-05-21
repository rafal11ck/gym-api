package xyz.cursedman.gym_api.mappers;

import org.mapstruct.MappingTarget;

public interface EntityCreatePatchMapper<EntityT, DtoT, CreateT, PatchT> extends EntityMapper<EntityT, DtoT> {
	EntityT toEntityFromCreateRequest(CreateT request);

	CreateT toCreateRequestFromEntity(EntityT entity);

	void updateFromPatchRequest(PatchT request, @MappingTarget EntityT entity);

	PatchT toPatchRequestFromEntity(EntityT entity);
}
