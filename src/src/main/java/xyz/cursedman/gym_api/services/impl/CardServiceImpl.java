package xyz.cursedman.gym_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.card.CardDto;
import xyz.cursedman.gym_api.domain.dtos.card.CreateCardRequest;
import xyz.cursedman.gym_api.domain.dtos.card.UpdateCardRequest;
import xyz.cursedman.gym_api.domain.entities.Card;
import xyz.cursedman.gym_api.mappers.CardMapper;
import xyz.cursedman.gym_api.mappers.CountryMapper;
import xyz.cursedman.gym_api.repositories.CardRepository;
import xyz.cursedman.gym_api.services.CardService;
import xyz.cursedman.gym_api.services.CountryService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {
	private final CardRepository cardRepository;
	private final CardMapper cardMapper;
	private final CountryMapper countryMapper;
	private final CountryService countryService;

	@Override
	public List<CardDto> listCards() {
		return cardRepository.findAll().stream().map(cardMapper::toDto).toList();
	}

	@Override
	public CardDto createCard(CreateCardRequest createCardRequest) {
		Card card = cardMapper.toEntity(createCardRequest);
		Card result = cardRepository.save(card);
		return cardMapper.toDto(result);
	}

	@Override
	public void deleteCard(UUID id) {
		cardRepository.deleteById(id);
	}

	@Override
	public CardDto updateCard(UUID id, UpdateCardRequest updateCardRequest) {
		Card card = cardRepository.findById(id).orElseThrow(
			() -> new EntityNotFoundException("Card with ID " + id + " not found"));

		cardMapper.updateFromDto(updateCardRequest, card);
		Card result = cardRepository.save(card);

		return cardMapper.toDto(result);
	}
}
