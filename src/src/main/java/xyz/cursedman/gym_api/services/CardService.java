package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.card.CardDto;
import xyz.cursedman.gym_api.domain.dtos.card.CreateCardRequest;
import xyz.cursedman.gym_api.domain.dtos.card.PatchCardRequest;

import java.util.List;
import java.util.UUID;

public interface CardService {
	List<CardDto> listCards();

	CardDto createCard(CreateCardRequest cardToCreate);

	CardDto patchCard(UUID id, PatchCardRequest cardToUpdate);

	void deleteCard(UUID id);
}
