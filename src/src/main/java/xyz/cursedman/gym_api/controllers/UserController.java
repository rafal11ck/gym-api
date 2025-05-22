package xyz.cursedman.gym_api.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.domain.dtos.chat.ChatDto;
import xyz.cursedman.gym_api.domain.dtos.user.UserDto;
import xyz.cursedman.gym_api.domain.dtos.user.UserRequest;
import xyz.cursedman.gym_api.services.ChatService;
import xyz.cursedman.gym_api.services.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	private final ChatService chatService;

	@GetMapping
	public ResponseEntity<List<UserDto>> listUsers() {
		return ResponseEntity.ok(userService.listUsers());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<UserDto> getUser(@Valid @PathVariable UUID id) {
		UserDto userDto = userService.getUser(id);
		return ResponseEntity.ok(userDto);
	}

	@PostMapping
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserRequest request) {
		UserDto createdUser = userService.createUser(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<UserDto> updateUser(
		@Valid @PathVariable UUID id,
		@RequestBody UserRequest request
	) {
		return ResponseEntity.ok(userService.patchUser(id, request));
	}

	// chats

	@GetMapping("{id}/chats")
	public ResponseEntity<List<ChatDto>> listChats(@PathVariable UUID id) {
		return ResponseEntity.ok(chatService.listUserChats(id));
	}
}
