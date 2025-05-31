package xyz.cursedman.gym_api.controllers;

import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.cursedman.gym_api.domain.dtos.chatParticipant.ChatParticipantCreateRequest;
import xyz.cursedman.gym_api.domain.dtos.chatParticipant.ChatParticipantPatchRequest;
import xyz.cursedman.gym_api.helpers.TestJsonHelper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(roles = {"CLIENT"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ChatControllerTest {

	private final String endpointUri = "/chats";
	private final ChatParticipantCreateRequest validParticipantCreateRequest = ChatParticipantCreateRequest.builder()
		.userUuid(UUID.fromString("f18d2783-77f6-4f3d-a58e-f72bb31600c6"))
		.build();
	private final ChatParticipantPatchRequest validParticipantPatchRequest
		= new ChatParticipantPatchRequest(new Date());
	private final Map<String, String> validChatUuids = Map.of(
		"chat_uuid", "a1ed2efc-59ac-4467-883b-89ee2ee6846d",
		"participant_uuid", "65f40335-135a-47ec-ad7d-72278c4be65c"
	);
	@Autowired
	private MockMvc mockMvc;

	// chat

	// GET

	@Test
	void checkIfGetChatByIdReturnsHttp200AndRequestedRecord() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(endpointUri + "/" + validChatUuids.get("chat_uuid")))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
			.andExpect(
				MockMvcResultMatchers.jsonPath("$.participants.length()", Matchers.greaterThan(0))
			);
	}

	@Test
	void checkIfGetNonExistingChatReturns404() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(endpointUri + "/" + UUID.randomUUID()))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	// POST

	@Test
	void checkIfCreateChatReturnsHttp201AndCreatedRecord() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post(endpointUri)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(
				MockMvcResultMatchers.jsonPath("$.uuid").exists()
			);
	}

	// chat participants

	// POST

	@Test
	void checkIfCreateChatParticipantReturnsHttp201AndCreatedRecord() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post(
						endpointUri + "/" + validChatUuids.get("chat_uuid") + "/participants"
					).contentType(MediaType.APPLICATION_JSON)
					.content(TestJsonHelper.stringify(validParticipantCreateRequest))
			).andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(TestJsonHelper.contentEqualsJsonOf(validParticipantCreateRequest, "userUuid"))
			.andExpect(
				MockMvcResultMatchers.jsonPath(
					"$.user.uuid", Matchers.is(validParticipantCreateRequest.getUserUuid().toString())
				)
			);
	}

	@Test
	void checkIfCreateExistingChatParticipantReturnsHttp201() throws Exception {
		ChatParticipantCreateRequest existingParticipantCreateRequest = ChatParticipantCreateRequest.builder()
			.userUuid(UUID.fromString(validChatUuids.get("participant_uuid")))
			.build();

		mockMvc.perform(
			MockMvcRequestBuilders.post(
					endpointUri + "/" + validChatUuids.get("chat_uuid") + "/participants"
				).contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(existingParticipantCreateRequest))
		).andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	void checkIfInvalidCreateChatParticipantBodyReturnsHttp400() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.post(
				endpointUri + "/" + validChatUuids.get("chat_uuid") + "/participants"
			).contentType(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void checkIfCreateChatParticipantInNonExistingChatReturnsHttp404() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.post(
					endpointUri + "/" + UUID.randomUUID() + "/participants"
				).contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validParticipantCreateRequest))
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	// PATCH

	@Test
	void checkIfChatParticipantPatchUpdateReturnsHttp200AndUpdatedRecord() throws Exception {
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.patch(
						endpointUri
							+ "/"
							+ validChatUuids.get("chat_uuid")
							+ "/participants/"
							+ validChatUuids.get("participant_uuid")
					)
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestJsonHelper.stringify(validParticipantPatchRequest))
			).andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		String dateFromJson = JsonPath.read(responseBody, "$.lastReadDateTime");

		Instant expected = validParticipantPatchRequest.getLastReadDateTime().toInstant();
		Instant actual = OffsetDateTime.parse(dateFromJson).toInstant();

		assertEquals(expected.truncatedTo(ChronoUnit.SECONDS), actual.truncatedTo(ChronoUnit.SECONDS));
	}

	@Test
	void checkIfChatParticipantPatchUpdateOfNonExistingChatReturnsHttp404() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.patch(
					endpointUri
						+ "/"
						+ UUID.randomUUID()
						+ "/participants/"
						+ validChatUuids.get("participant_uuid")
				).contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validParticipantPatchRequest))
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
