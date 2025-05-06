package xyz.cursedman.gym_api.services;

import jakarta.persistence.EntityNotFoundException;
import xyz.cursedman.gym_api.domain.dtos.chat.ChatDto;

import java.util.List;
import java.util.UUID;

public interface ChatService {
	ChatDto getChat(UUID id) throws EntityNotFoundException;

	List<ChatDto> listUserChats(UUID userId) throws EntityNotFoundException;

	ChatDto createChat() throws EntityNotFoundException;
}
