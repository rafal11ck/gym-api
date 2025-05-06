package xyz.cursedman.gym_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import xyz.cursedman.gym_api.domain.dtos.chatParticipant.ChatParticipantCreateRequest;
import xyz.cursedman.gym_api.domain.dtos.chatParticipant.ChatParticipantDto;
import xyz.cursedman.gym_api.domain.dtos.chatParticipant.ChatParticipantPatchRequest;
import xyz.cursedman.gym_api.domain.entities.ChatParticipant;
import xyz.cursedman.gym_api.services.UserService;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = UserService.class)
public interface ChatParticipantMapper extends EntityCreatePatchMapper<
	ChatParticipant, ChatParticipantDto, ChatParticipantCreateRequest, ChatParticipantPatchRequest
> {
	@Override
	@Mapping(target = "user", source = "userUuid")
	ChatParticipant toEntityFromCreateRequest(ChatParticipantCreateRequest request);
}
