package xyz.cursedman.gym_api.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.cursedman.gym_api.domain.dtos.card.CardRequest;
import xyz.cursedman.gym_api.domain.entities.Card;
import xyz.cursedman.gym_api.helpers.TestCardDataHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CardMapperTest {
	@Autowired
	private TestCardDataHelper testCardDataHelper;

	@Autowired
	private CardMapper cardMapper;

	@Test
	void checkIfCardMapperCorrectlyMapsCountry() {
		// Card -> CardRequest
		Card card = testCardDataHelper.getTestCard();
		CardRequest request = cardMapper.toRequestFromEntity(card);
		assertEquals(card.getCountry().getUuid(), request.getCountryUuid());

		// CardRequest -> Card
		Card cardFromRequest = cardMapper.toEntityFromRequest(request);
		assertEquals(cardFromRequest.getCountry().getUuid(), request.getCountryUuid());
	}
}
