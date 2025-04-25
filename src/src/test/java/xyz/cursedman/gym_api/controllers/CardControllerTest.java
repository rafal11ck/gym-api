package xyz.cursedman.gym_api.controllers;

import org.hamcrest.Matchers;
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
import xyz.cursedman.gym_api.helpers.TestJsonHelper;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CardControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private final String validCardUuid = "5bd05494-155e-4bd1-b14c-61421d0caaae";

	private final String validCountryUuid = "352ed7f1-8bb1-4baa-9ca7-88995ec58d8a";

	private final CardRequest validCardRequest = CardRequest.builder()
		.cardNumber("123")
		.nameOnCard("John Doe")
		.cvv("123")
		.dateOfBirth(new Date())
		.postalCode("21-370")
		.countryUuid(UUID.fromString(validCountryUuid))
		.build();

	// GET

	@Test
	void checkIfGetReturnsHttp200AndAllRecords() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/cards"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.greaterThan(0)));
	}

	@Test
	void checkIfGetByIdReturnsHttp200AndRequestedRecord() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/cards/" + validCardUuid))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$").exists());
	}

	@Test
	void checkIfGetNonExistingRecordReturns404AndEmptyBody() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/cards/" + UUID.randomUUID()))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
	}

	// POST

	@Test
	void checkIfCreateReturnsHttp201AndCreatedRecord() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.post("/cards")
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestJsonHelper.stringify(validCardRequest))
		).andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(TestJsonHelper.contentEqualsJsonOf(validCardRequest, "countryUuid"))
		.andExpect(
			MockMvcResultMatchers.jsonPath("$.country.uuid", Matchers.is(validCountryUuid))
		);
	}

	@Test
	void checkIfInvalidCreateBodyReturnsHttp400() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.post("/cards")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}")
		).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	// PATCH

	@Test
	void checkIfPatchUpdateReturnsHttp200AndUpdatedRecord() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.patch("/cards/" + validCardUuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validCardRequest))
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(TestJsonHelper.contentEqualsJsonOf(validCardRequest, "countryUuid"))
		.andExpect(
			MockMvcResultMatchers.jsonPath("$.country.uuid", Matchers.is(validCountryUuid))
		);
	}

	@Test
	void checkIfPatchUpdateOfNonExistingRecordReturnsHttp404() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.patch("/cards/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validCardRequest))
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void checkIfInvalidPatchUpdateBodyReturnsHttp400() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.patch("/cards/" + validCardUuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}")
		).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}
