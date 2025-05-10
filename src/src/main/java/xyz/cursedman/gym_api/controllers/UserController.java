package xyz.cursedman.gym_api.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.domain.dtos.chat.ChatDto;
import xyz.cursedman.gym_api.domain.dtos.user.UserDto;
import xyz.cursedman.gym_api.domain.dtos.user.UserRequest;
import xyz.cursedman.gym_api.services.ChatService;
import xyz.cursedman.gym_api.services.StripeService;
import xyz.cursedman.gym_api.services.UserService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	private final ChatService chatService;
	private final StripeService stripeService;

	@GetMapping
	public ResponseEntity<List<UserDto>> listUsers() {
		return ResponseEntity.ok(userService.listUsers());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<UserDto> getUser(@Valid @PathVariable UUID id) {
		try {
			UserDto userDto = userService.getUser(id);
			return ResponseEntity.ok(userDto);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserRequest request) {
		try {
			UserDto createdUser = userService.createUser(request);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<UserDto> updateUser(
		@Valid @PathVariable UUID id,
		@RequestBody UserRequest request
	) {
		try {
			return ResponseEntity.ok(userService.patchUser(id, request));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	// chats

	@GetMapping("{id}/chats")
	public ResponseEntity<List<ChatDto>> listChats(@PathVariable UUID id) {
		try {
			return ResponseEntity.ok(chatService.listUserChats(id));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	// payments

	@GetMapping("/users/{userId}/subscriptions/{productName}/setup-subscription")
	public ResponseEntity<Void> checkoutMembership(
		@Valid @PathVariable String productName,
		@Valid @PathVariable UUID userId
	) {
		Optional<URI> checkoutUri = stripeService.createCheckoutSession(productName, userId);
		if (checkoutUri.isPresent()) {
			return ResponseEntity
				.status(HttpStatus.SEE_OTHER)
				.location(checkoutUri.get())
				.build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
