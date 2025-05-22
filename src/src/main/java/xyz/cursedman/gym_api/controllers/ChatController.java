package xyz.cursedman.gym_api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.domain.dtos.chat.ChatDto;
import xyz.cursedman.gym_api.domain.dtos.chatParticipant.ChatParticipantCreateRequest;
import xyz.cursedman.gym_api.domain.dtos.chatParticipant.ChatParticipantDto;
import xyz.cursedman.gym_api.domain.dtos.chatParticipant.ChatParticipantPatchRequest;
import xyz.cursedman.gym_api.services.ChatParticipantService;
import xyz.cursedman.gym_api.services.ChatService;

import java.util.UUID;

@RestController
@RequestMapping(path = "/chats")
@RequiredArgsConstructor
public class ChatController {
	private final ChatService chatService;

	private final ChatParticipantService chatParticipantService;

	@GetMapping("/{id}")
	public ResponseEntity<ChatDto> getChat(@Valid @PathVariable UUID id) {
		return ResponseEntity.ok(chatService.getChat(id));
	}

	@PostMapping
	public ResponseEntity<ChatDto> createChat() {
		return ResponseEntity.status(HttpStatus.CREATED).body(chatService.createChat());
	}

	// chat participants
	@PostMapping("/{id}/participants")
	public ResponseEntity<ChatParticipantDto> addChatParticipant(
		@Valid @PathVariable UUID id,
		@Valid @RequestBody ChatParticipantCreateRequest request
	) {
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(chatParticipantService.addChatParticipant(id, request));
	}

	@PatchMapping("/{id}/participants/{userId}")
	public ResponseEntity<ChatParticipantDto> updateChatParticipant(
		@Valid @PathVariable UUID id,
		@Valid @PathVariable UUID userId,
		@RequestBody ChatParticipantPatchRequest request
	) {
		return ResponseEntity.ok(chatParticipantService.updateChatParticipant(id, userId, request));

	}
}
