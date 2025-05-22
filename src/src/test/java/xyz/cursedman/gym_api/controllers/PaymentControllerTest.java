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
import xyz.cursedman.gym_api.domain.dtos.payment.PaymentRequest;
import xyz.cursedman.gym_api.helpers.TestJsonHelper;

import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PaymentControllerTest {
	@Autowired
	private MockMvc mockMvc;

	private final String endpointUri = "/payments";

	private final String validPaymentUuid = "a9e17e91-8792-42dd-badd-f2639e09ebc2";

	private final String validCardUuid = "5bd05494-155e-4bd1-b14c-61421d0caaae";

	private final String validMembershipUuid = "06d25bae-8d09-4170-b566-fd17f2ee6a23";

	private final String validStatusUuid = "dbc10aab-cce9-4813-b8b1-cb0ff763b0b1";

	private final PaymentRequest validPaymentRequest = PaymentRequest.builder()
		.cardUuid(UUID.fromString(validCardUuid))
		.membershipUuid(UUID.fromString(validMembershipUuid))
		.statusUuid(UUID.fromString(validStatusUuid))
		.build();

	// GET

	@Test
	void checkIfGetReturnsHttp200AndAllRecords() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(endpointUri))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.greaterThan(0)));
	}

	@Test
	void checkIfGetByIdReturnsHttp200AndRequestedRecord() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(endpointUri + "/" + validPaymentUuid))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$").exists());
	}

	@Test
	void checkIfGetNonExistingRecordReturns404() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(endpointUri + "/" + UUID.randomUUID()))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	// POST

	@Test
	void checkIfCreateReturnsHttp201AndCreatedRecord() throws Exception {
		String[] fieldsToIgnore = { "membershipUuid", "statusUuid", "cardUuid" };
		mockMvc.perform(
			MockMvcRequestBuilders.post(endpointUri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validPaymentRequest))
		).andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(TestJsonHelper.contentEqualsJsonOf(validPaymentRequest, fieldsToIgnore))
		.andExpect(
			MockMvcResultMatchers.jsonPath("$.membership.uuid", Matchers.is(validMembershipUuid))
		)
		.andExpect(
			MockMvcResultMatchers.jsonPath("$.card.uuid", Matchers.is(validCardUuid))
		)
		.andExpect(
			MockMvcResultMatchers.jsonPath("$.status.uuid", Matchers.is(validStatusUuid))
		);
	}

	@Test
	void checkIfInvalidCreateBodyReturnsHttp400() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.post(endpointUri)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}")
		).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	// PATCH

	@Test
	void checkIfPatchUpdateReturnsHttp200AndUpdatedRecord() throws Exception {
		String cardUuidToUpdate = "ed36b15c-89d7-43cd-aa1b-354b2ec9067d";
		mockMvc.perform(
				MockMvcRequestBuilders.patch(endpointUri + "/" + validPaymentUuid)
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestJsonHelper.toJSONField("cardUuid", cardUuidToUpdate))
			).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(
				MockMvcResultMatchers.jsonPath("$.card.uuid", Matchers.is(cardUuidToUpdate))
			);
	}

	@Test
	void checkIfPatchUpdateOfNonExistingRecordReturnsHttp404() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.patch(endpointUri + "/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validPaymentRequest))
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
