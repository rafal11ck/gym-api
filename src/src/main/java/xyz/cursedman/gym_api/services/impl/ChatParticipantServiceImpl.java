package xyz.cursedman.gym_api.services.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.chatParticipant.ChatParticipantCreateRequest;
import xyz.cursedman.gym_api.domain.dtos.chatParticipant.ChatParticipantDto;
import xyz.cursedman.gym_api.domain.dtos.chatParticipant.ChatParticipantPatchRequest;
import xyz.cursedman.gym_api.domain.entities.Chat;
import xyz.cursedman.gym_api.domain.entities.ChatParticipant;
import xyz.cursedman.gym_api.exceptions.ChatParticipantNotFoundException;
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
	public Set<ChatParticipantDto> getChatParticipantDtosByChatUuid(UUID chatUuid) throws ChatParticipantNotFoundException {
		if (!chatRepository.existsById(chatUuid)) {
			throw new ChatParticipantNotFoundException();
		}

		return chatParticipantRepository.findByChat_Uuid(chatUuid)
			.stream()
			.map(chatParticipantMapper::toDtoFromEntity)
			.collect(Collectors.toSet());
	}

	@Override
	public Set<ChatParticipant> getChatParticipantsByUserUuid(UUID userId) throws EntityNotFoundException {
		if (!userRepository.existsById(userId)) {
			throw new EntityNotFoundException();
		}

		return chatParticipantRepository.findByUser_Uuid(userId);
	}

	@Override
	public ChatParticipantDto addChatParticipant(UUID id, ChatParticipantCreateRequest request)
		throws EntityNotFoundException
	{
		Chat chat = chatRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		Set<ChatParticipant> chatParticipants = chatParticipantRepository.findByChat_Uuid(id);
		Optional<ChatParticipant> foundParticipant = chatParticipants.stream()
			.filter(cp -> cp.getUser().getUuid().equals(request.getUserUuid()))
			.findFirst();

		if (foundParticipant.isPresent()) {
			throw new EntityExistsException();
		}

		ChatParticipant chatParticipant = chatParticipantMapper.toEntityFromCreateRequest(request);
		chatParticipant.setChat(chat);

		ChatParticipant createdParticipant = chatParticipantRepository.save(chatParticipant);
		return chatParticipantMapper.toDtoFromEntity(createdParticipant);
	}

	@Override
	public ChatParticipantDto updateChatParticipant(UUID id, UUID userId, ChatParticipantPatchRequest request)
		throws EntityNotFoundException
	{
		if (!chatRepository.existsById(id)) {
			throw new EntityNotFoundException();
		}

		Set<ChatParticipant> chatParticipants = chatParticipantRepository.findByChat_Uuid(id);
		Optional<ChatParticipant> foundParticipant = chatParticipants.stream()
			.filter(cp -> cp.getUser().getUuid().equals(userId))
			.findFirst();

		if (foundParticipant.isEmpty()) {
			throw new EntityNotFoundException();
		}

		ChatParticipant chatParticipant = foundParticipant.get();
		chatParticipantMapper.updateFromPatchRequest(request, chatParticipant);

		ChatParticipant updatedParticipant = chatParticipantRepository.save(chatParticipant);
		return chatParticipantMapper.toDtoFromEntity(updatedParticipant);
	}
}
