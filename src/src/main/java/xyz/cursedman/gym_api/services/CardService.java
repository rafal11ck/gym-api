package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.card.CardDto;
import xyz.cursedman.gym_api.domain.dtos.card.CardRequest;

import java.util.List;
import java.util.UUID;

public interface CardService {
	List<CardDto> listCards();

	CardDto createCard(CardRequest cardToCreate);

	CardDto patchCard(UUID id, CardRequest request);

	void deleteCard(UUID id);
}
