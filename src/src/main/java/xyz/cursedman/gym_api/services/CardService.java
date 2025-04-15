package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.card.CardDto;
import xyz.cursedman.gym_api.domain.dtos.card.CreateCardRequest;
import xyz.cursedman.gym_api.domain.dtos.card.UpdateCardRequest;

import java.util.List;
import java.util.UUID;

public interface CardService {
	List<CardDto> listCards();

	CardDto createCard(CreateCardRequest cardToCreate);

	CardDto updateCard(UUID id, UpdateCardRequest cardToUpdate);

	void deleteCard(UUID id);
}
