package xyz.cursedman.gym_api.mappers;

import org.mapstruct.MappingTarget;

public interface EntityCreatePatchMapper<EntityT, DtoT, CreateT, PatchT> extends EntityMapper<EntityT, DtoT> {
	EntityT toEntityFromCreateRequest(CreateT request);

	void updateFromPatchRequest(PatchT request, @MappingTarget EntityT entity);
}
