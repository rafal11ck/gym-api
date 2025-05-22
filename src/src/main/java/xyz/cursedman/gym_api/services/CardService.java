package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.card.CardDto;
import xyz.cursedman.gym_api.domain.dtos.card.CardRequest;
import xyz.cursedman.gym_api.domain.entities.Card;

import java.util.List;
import java.util.UUID;

public interface CardService {
	List<CardDto> listCards();

	CardDto createCard(CardRequest cardToCreate);

	CardDto getCard(UUID id);

	CardDto patchCard(UUID id, CardRequest request);

	Card getCardByUUID(UUID id);
}
