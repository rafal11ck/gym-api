package xyz.cursedman.gym_api.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.domain.dtos.user.UserDto;
import xyz.cursedman.gym_api.domain.dtos.user.UserRequest;
import xyz.cursedman.gym_api.services.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping
	public ResponseEntity<List<UserDto>> listCards() {
		return ResponseEntity.ok(userService.listUsers());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<UserDto> getCard(@Valid @PathVariable UUID id) {
		try {
			UserDto userDto = userService.getUser(id);
			return ResponseEntity.ok(userDto);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<UserDto> createCard(@Valid @RequestBody UserRequest request) {
		UserDto createdUser = userService.createUser(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<UserDto> updateCard(
		@Valid @PathVariable UUID id,
		@RequestBody UserRequest request
	) {
		try {
			return ResponseEntity.ok(userService.patchUser(id, request));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
