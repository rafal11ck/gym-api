package xyz.cursedman.gym_api.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.cursedman.gym_api.domain.dtos.card.CardRequest;
import xyz.cursedman.gym_api.domain.entities.Card;
import xyz.cursedman.gym_api.domain.entities.Country;
import xyz.cursedman.gym_api.mappers.CardMapper;
import xyz.cursedman.gym_api.repositories.CardRepository;

import java.util.Date;

@Component
public class TestCardDataHelper {

	@Autowired
	private TestCountryDataHelper testCountryDataHelper;

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private CardMapper cardMapper;

	public Card getTestCard() {
		Country testCountry = testCountryDataHelper.saveAndGetTestCountry();
		return Card.builder()
			.country(testCountry)
			.cardNumber("0000 0000 0000 0000")
			.cvv("123")
			.dateOfBirth(new Date())
			.nameOnCard("John Pork")
			.postalCode("21-370")
			.build();
	}

	public Card saveAndGetTestCard() {
		Card testCard = getTestCard();
		return cardRepository.saveAndFlush(testCard);
	}

	public CardRequest getCreateCardRequestFromTestCard(Card testCard) {
		return cardMapper.toRequestFromEntity(testCard);
	}

	public CardRequest getUpdateCardRequest(String newCardNumber) {
		return CardRequest.builder()
			.cardNumber(newCardNumber)
			.build();
	}
}
