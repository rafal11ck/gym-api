package xyz.cursedman.gym_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.card.CardDto;
import xyz.cursedman.gym_api.domain.dtos.card.CardRequest;
import xyz.cursedman.gym_api.domain.entities.Card;
import xyz.cursedman.gym_api.mappers.CardMapper;
import xyz.cursedman.gym_api.repositories.CardRepository;
import xyz.cursedman.gym_api.services.CardService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {

	private final CardRepository cardRepository;

	private final CardMapper cardMapper;

	@Override
	public List<CardDto> listCards() {
		return cardRepository.findAll().stream().map(cardMapper::toDtoFromEntity).toList();
	}

	@Override
	public CardDto getCard(UUID id) throws EntityNotFoundException {
		return cardRepository
			.findById(id)
			.map(cardMapper::toDtoFromEntity)
			.orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public CardDto createCard(CardRequest cardRequest) {
		Card card = cardMapper.toEntityFromRequest(cardRequest);
		Card result = cardRepository.save(card);
		return cardMapper.toDtoFromEntity(result);
	}

	@Override
	public CardDto patchCard(UUID id, CardRequest request) throws EntityNotFoundException {
		Card card = cardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		cardMapper.updateFromRequest(request, card);

		Card result = cardRepository.save(card);
		return cardMapper.toDtoFromEntity(result);
	}

	@Override
	public Card getCardByUUID(UUID id) {
		return cardRepository.findById(id).orElseThrow(
			() -> new EntityNotFoundException("Card with ID " + id + " not found")
		);
	}
}
