package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.chatParticipant.ChatParticipantCreateRequest;
import xyz.cursedman.gym_api.domain.dtos.chatParticipant.ChatParticipantDto;
import xyz.cursedman.gym_api.domain.dtos.chatParticipant.ChatParticipantPatchRequest;
import xyz.cursedman.gym_api.domain.entities.ChatParticipant;

import java.util.Set;
import java.util.UUID;

public interface ChatParticipantService {
	Set<ChatParticipantDto> getChatParticipantDtosByChatUuid(UUID chatUuid);

	Set<ChatParticipant> getChatParticipantsByUserUuid(UUID userId);

	ChatParticipantDto addChatParticipant(UUID id, ChatParticipantCreateRequest request);

	ChatParticipantDto updateChatParticipant(UUID id, UUID userId, ChatParticipantPatchRequest request);
}
