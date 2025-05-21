package xyz.cursedman.gym_api.services;

import jakarta.persistence.EntityNotFoundException;
import xyz.cursedman.gym_api.domain.dtos.card.CardDto;
import xyz.cursedman.gym_api.domain.dtos.card.CardRequest;
import xyz.cursedman.gym_api.domain.entities.Card;

import java.util.List;
import java.util.UUID;

public interface CardService {
	List<CardDto> listCards();

	CardDto getCard(UUID id);

	CardDto createCard(CardRequest cardToCreate);

	CardDto patchCard(UUID id, CardRequest request);

	// used by mapper
	Card getCardByUUID(UUID id);
}
