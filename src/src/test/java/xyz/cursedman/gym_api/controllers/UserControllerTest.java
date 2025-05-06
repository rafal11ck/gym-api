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
import xyz.cursedman.gym_api.domain.dtos.user.UserRequest;
import xyz.cursedman.gym_api.helpers.TestJsonHelper;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private final String endpointUri = "/users";

	private final String validUserUuid = "65f40335-135a-47ec-ad7d-72278c4be65c";

	private final String validCardUuid = "5bd05494-155e-4bd1-b14c-61421d0caaae";

	private final String validMembershipUuid = "ddd5f2a7-157e-4bf1-b11c-fa46e0d6bad1";

	private final String validRoleUuid = "0608911b-f8fa-4a83-8f63-f314030a36ed";

	private final UserRequest validUserRequest = UserRequest.builder()
		.firstName("John")
		.lastName("Pork")
		.dateOfBirth(new Date())
		.email("email")
		.imageUrl("https://picsum.photos/200")
		.phoneNumber("123123123")
		.cardUuid(UUID.fromString(validCardUuid))
		.membershipUuid(UUID.fromString(validMembershipUuid))
		.roleUuid(UUID.fromString(validRoleUuid))
		.build();

	// GET

	@Test
	void checkIfGetUsersReturnsHttp200AndAllRecords() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(endpointUri))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.greaterThan(0)));
	}

	@Test
	void checkIfGetUserByIdReturnsHttp200AndRequestedRecord() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(endpointUri + "/" + validUserUuid))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$").exists());
	}

	@Test
	void checkIfGetNonExistingUserRecordReturns404AndEmptyBody() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(endpointUri + "/" + UUID.randomUUID()))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
	}

	// POST

	@Test
	void checkIfCreateUserReturnsHttp201AndCreatedRecord() throws Exception {
		String[] fieldsToIgnore = { "membershipUuid", "cardUuid", "roleUuid" };
		mockMvc.perform(
			MockMvcRequestBuilders.post(endpointUri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validUserRequest))
		).andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(TestJsonHelper.contentEqualsJsonOf(validUserRequest, fieldsToIgnore))
		.andExpect(
			MockMvcResultMatchers.jsonPath("$.membership.uuid", Matchers.is(validMembershipUuid))
		)
		.andExpect(
			MockMvcResultMatchers.jsonPath("$.card.uuid", Matchers.is(validCardUuid))
		)
		.andExpect(
			MockMvcResultMatchers.jsonPath("$.role.uuid", Matchers.is(validRoleUuid))
		);
	}

	@Test
	void checkIfInvalidUserCreateBodyReturnsHttp400() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.post(endpointUri)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}")
		).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	// PATCH

	@Test
	void checkIfUserPatchUpdateReturnsHttp200AndUpdatedRecord() throws Exception {
		String cardUuidToUpdate = "ed36b15c-89d7-43cd-aa1b-354b2ec9067d";
		mockMvc.perform(
			MockMvcRequestBuilders.patch(endpointUri + "/" + validUserUuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.toJSONField("cardUuid", cardUuidToUpdate))
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(
			MockMvcResultMatchers.jsonPath("$.card.uuid", Matchers.is(cardUuidToUpdate))
		);
	}

	@Test
	void checkIfPatchUpdateOfNonExistingUserReturnsHttp404() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.patch(endpointUri + "/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validUserRequest))
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	// chat

	// GET

	@Test
	void checkIfGetUserChatsReturnsHttp200AndAllRecords() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.get(endpointUri + "/" + validUserUuid + "/chats")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validUserRequest))
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.greaterThan(0)));
	}

	@Test
	void checkIfGetChatsOfNonExistingUserReturnsHttp404() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.get(endpointUri + "/" + UUID.randomUUID() + "/chats")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validUserRequest))
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
