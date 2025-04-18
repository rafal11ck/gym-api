package xyz.cursedman.gym_api.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.cursedman.gym_api.domain.dtos.card.CardRequest;
import xyz.cursedman.gym_api.domain.entities.Card;
import xyz.cursedman.gym_api.helpers.TestCardDataHelper;
import xyz.cursedman.gym_api.helpers.TestJsonHelper;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CardControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TestCardDataHelper testCardDataHelper;

	@Test
	void checkIfGetCardsReturnsHttp200AndSavedRecord() throws Exception {
		Card testCard = testCardDataHelper.saveAndGetTestCard();

		mockMvc.perform(MockMvcRequestBuilders.get("/cards"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(TestJsonHelper.contentEqualsJsonArrayOf(testCard));
	}

	@Test
	void checkIfCreateCardReturnsHttp201AndCreatedRecord() throws Exception {
		String[] ignoredFields = { "uuid" };
		Card testCard = testCardDataHelper.getTestCard();
		CardRequest cardRequest = testCardDataHelper.getCreateCardRequestFromTestCard(testCard);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/cards")
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestJsonHelper.stringify(cardRequest))
			).andExpect(MockMvcResultMatchers.status().isCreated())
			 .andExpect(TestJsonHelper.contentEqualsJsonOf(testCard, ignoredFields));
	}

	@Test
	void checkIfCardPatchUpdateReturnsHttp200AndUpdatedRecord() throws Exception {
		String newCardNumber = "newCardNumber";
		CardRequest request = testCardDataHelper.getUpdateCardRequest(newCardNumber);
		Card testCard = testCardDataHelper.saveAndGetTestCard();

		testCard.setCardNumber(newCardNumber);

		mockMvc.perform(
			MockMvcRequestBuilders.patch("/cards/" + testCard.getUuid())
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(request))
		).andExpect(MockMvcResultMatchers.status().isOk())
		 .andExpect(TestJsonHelper.contentEqualsJsonOf(testCard));
	}
}
