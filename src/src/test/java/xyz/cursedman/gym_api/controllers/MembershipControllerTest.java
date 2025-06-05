package xyz.cursedman.gym_api.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.cursedman.gym_api.domain.dtos.membership.MembershipRequest;
import xyz.cursedman.gym_api.helpers.TestJsonHelper;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(roles = {"CLIENT"})
class MembershipControllerTest {
	private final String endpointUri = "/memberships";
	private final String validMembershipUuid = "bbac838e-4a2f-4ef8-b562-075d64d8493a";
	private final String validMembershipTypeUuid = "9d4e894f-30e4-488e-9689-ad0fa32a69d1";
	private final MembershipRequest validMembershipRequest = MembershipRequest.builder()
		.membershipTypeUuid(UUID.fromString(validMembershipTypeUuid))
		.build();
	@Autowired
	private MockMvc mockMvc;

	// GET

	@Test
	void checkIfGetReturnsHttp200AndAllRecords() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(endpointUri))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.greaterThan(0)));
	}

	@Test
	void checkIfGetByIdReturnsHttp200AndRequestedRecord() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(endpointUri + "/" + validMembershipUuid))
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
		mockMvc.perform(
			MockMvcRequestBuilders.post(endpointUri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validMembershipRequest))
		).andExpect(MockMvcResultMatchers.status().isCreated());
	}


	@Test
	void checkIfGetPaymentReturnsURI() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.post(endpointUri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validMembershipRequest))
		).andReturn();
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
		String membershipTypeUuidToUpdate = "38dc2c0f-566f-48c7-a147-95fd0f0632ee";
		mockMvc.perform(
				MockMvcRequestBuilders.patch(endpointUri + "/" + validMembershipUuid)
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestJsonHelper.toJSONField("membershipTypeUuid", membershipTypeUuidToUpdate))
			).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(
				MockMvcResultMatchers.jsonPath("$.membershipType.uuid", Matchers.is(membershipTypeUuidToUpdate))
			);
	}

	@Test
	void checkIfPatchUpdateOfNonExistingRecordReturnsHttp404() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.patch(endpointUri + "/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validMembershipRequest))
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
