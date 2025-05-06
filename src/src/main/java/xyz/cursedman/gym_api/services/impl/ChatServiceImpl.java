package xyz.cursedman.gym_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.chat.ChatDto;
import xyz.cursedman.gym_api.domain.entities.Chat;
import xyz.cursedman.gym_api.domain.entities.ChatParticipant;
import xyz.cursedman.gym_api.mappers.ChatMapper;
import xyz.cursedman.gym_api.repositories.ChatRepository;
import xyz.cursedman.gym_api.services.ChatParticipantService;
import xyz.cursedman.gym_api.services.ChatService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

	private final ChatParticipantService chatParticipantService;

	private final ChatRepository chatRepository;

	private final ChatMapper chatMapper;

	@Override
	public ChatDto getChat(UUID id) throws EntityNotFoundException {
		Chat chat = chatRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		UUID chatUuid = chat.getUuid();

		return ChatDto.builder()
			.uuid(chatUuid)
			.participants(chatParticipantService.getChatParticipantDtosByChatUuid(chatUuid))
			.build();
	}

	@Override
	public List<ChatDto> listUserChats(UUID userId) {
		return chatParticipantService.getChatParticipantsByUserUuid(userId).stream()
			.map(ChatParticipant::getChat)
			.distinct()
			.map(chat -> ChatDto.builder()
				.uuid(chat.getUuid())
				.participants(chatParticipantService.getChatParticipantDtosByChatUuid(chat.getUuid()))
				.build())
			.toList();
	}

	@Override
	public ChatDto createChat() throws EntityNotFoundException {
		Chat createdChat = chatRepository.save(new Chat());
		return chatMapper.toDtoFromEntity(createdChat);
	}
}
