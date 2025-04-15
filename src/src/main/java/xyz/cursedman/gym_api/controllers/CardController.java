package xyz.cursedman.gym_api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.domain.dtos.card.CardDto;
import xyz.cursedman.gym_api.domain.dtos.card.CreateCardRequest;
import xyz.cursedman.gym_api.domain.dtos.card.UpdateCardRequest;
import xyz.cursedman.gym_api.domain.entities.Card;
import xyz.cursedman.gym_api.mappers.CardMapper;
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

	@PostMapping
	public ResponseEntity<CardDto> createCard(@RequestBody CreateCardRequest createCardRequestDto) {
		return ResponseEntity.ok(cardService.createCard(createCardRequestDto));
	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<CardDto> updateCard(
		@Valid @PathVariable UUID id,
		@RequestBody UpdateCardRequest updateCardRequestDto
	) {
		return ResponseEntity.ok(cardService.updateCard(id, updateCardRequestDto));
	}
}
