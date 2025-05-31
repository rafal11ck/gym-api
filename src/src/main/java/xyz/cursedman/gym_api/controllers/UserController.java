package xyz.cursedman.gym_api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cursedman.gym_api.domain.dtos.chat.ChatDto;
import xyz.cursedman.gym_api.domain.dtos.user.UserDto;
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

	// chats

	@GetMapping("{id}/chats")
	public ResponseEntity<List<ChatDto>> listChats(@PathVariable UUID id) {
		return ResponseEntity.ok(chatService.listUserChats(id));
	}
}
