package xyz.cursedman.gym_api.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.chatParticipant.ChatParticipantCreateRequest;
import xyz.cursedman.gym_api.domain.dtos.chatParticipant.ChatParticipantDto;
import xyz.cursedman.gym_api.domain.dtos.chatParticipant.ChatParticipantPatchRequest;
import xyz.cursedman.gym_api.domain.entities.Chat;
import xyz.cursedman.gym_api.domain.entities.ChatParticipant;
import xyz.cursedman.gym_api.exceptions.NotFoundException;
import xyz.cursedman.gym_api.mappers.ChatParticipantMapper;
import xyz.cursedman.gym_api.repositories.ChatParticipantRepository;
import xyz.cursedman.gym_api.repositories.ChatRepository;
import xyz.cursedman.gym_api.repositories.UserRepository;
import xyz.cursedman.gym_api.services.ChatParticipantService;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatParticipantServiceImpl implements ChatParticipantService {

	private final ChatParticipantRepository chatParticipantRepository;

	private final ChatParticipantMapper chatParticipantMapper;

	private final UserRepository userRepository;

	private final ChatRepository chatRepository;


	@Override
	public Set<ChatParticipantDto> getChatParticipantDtosByChatUuid(UUID chatUuid) {

		Optional<Chat> chatOptional = chatRepository.findById(chatUuid);
		if (chatOptional.isEmpty()) {
			throw new NotFoundException("Chat with uuid " + chatUuid + " not found");
		}

		return chatParticipantRepository.findByChat_Uuid(chatUuid)
			.stream()
			.map(chatParticipantMapper::toDtoFromEntity)
			.collect(Collectors.toSet());
	}

	@Override
	public Set<ChatParticipant> getChatParticipantsByUserUuid(UUID userId) {
		if (!userRepository.existsById(userId)) {
			throw new NotFoundException("User with uuid " + userId + " not found");
		}

		return chatParticipantRepository.findByUser_Uuid(userId);
	}

	@Override
	public ChatParticipantDto addChatParticipant(UUID id, ChatParticipantCreateRequest request) {
		Chat chat = chatRepository.findById(id).orElseThrow(() ->
			new NotFoundException("Chat with uuid " + id + " not found"));

		Set<ChatParticipant> chatParticipants = chatParticipantRepository.findByChat_Uuid(id);

		Optional<ChatParticipant> foundParticipant = chatParticipants.stream()
			.filter(cp -> cp.getUser().getUuid().equals(request.getUserUuid()))
			.findFirst();

		// Do not add again if already exists
		if (foundParticipant.isPresent()) {
			return
				chatParticipantMapper.toDtoFromEntity(foundParticipant.get());
		}

		ChatParticipant chatParticipant = chatParticipantMapper.toEntityFromCreateRequest(request);
		chatParticipant.setChat(chat);
		ChatParticipant createdParticipant = chatParticipantRepository.save(chatParticipant);

		return chatParticipantMapper.toDtoFromEntity(createdParticipant);
	}

	@Override
	public ChatParticipantDto updateChatParticipant(UUID id, UUID userId, ChatParticipantPatchRequest request) {
		if (!chatRepository.existsById(id)) {
			throw new NotFoundException("Chat with uuid " + id + " not found");
		}

		Set<ChatParticipant> chatParticipants = chatParticipantRepository.findByChat_Uuid(id);
		Optional<ChatParticipant> foundParticipant = chatParticipants.stream()
			.filter(cp -> cp.getUser().getUuid().equals(userId))
			.findFirst();

		if (foundParticipant.isEmpty()) {
			throw new NotFoundException("User with uuid " + userId + " not found");
		}

		ChatParticipant chatParticipant = foundParticipant.get();
		chatParticipantMapper.updateFromPatchRequest(request, chatParticipant);

		// FIXME brakuje zapisania do repo ?

		ChatParticipant updatedParticipant = chatParticipantRepository.save(chatParticipant);
		return chatParticipantMapper.toDtoFromEntity(updatedParticipant);
	}
}
