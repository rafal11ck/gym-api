package xyz.cursedman.gym_api.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.domain.dtos.card.CardDto;
import xyz.cursedman.gym_api.domain.dtos.card.CardRequest;
import xyz.cursedman.gym_api.services.CardService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/cards")
@RequiredArgsConstructor
public class CardController {
	private final CardService cardService;

	@GetMapping
	public ResponseEntity<List<CardDto>> listCards() {
		return ResponseEntity.ok(cardService.listCards());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<CardDto> getCard(@Valid @PathVariable UUID id) {
		try {
			CardDto cardDto = cardService.getCard(id);
			return ResponseEntity.ok(cardDto);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<CardDto> createCard(@Valid @RequestBody CardRequest cardRequestDto) {
		CardDto createdCard = cardService.createCard(cardRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCard);
	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<CardDto> updateCard(
		@Valid @PathVariable UUID id,
		@Valid @RequestBody CardRequest request
	) {
		try {
			return ResponseEntity.ok(cardService.patchCard(id, request));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
