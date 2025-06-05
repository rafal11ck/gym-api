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
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeRequest;
import xyz.cursedman.gym_api.helpers.TestJsonHelper;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(roles = {"CLIENT"})
class MembershipTypeControllerTest {

	private final String endpointUri = "/membership-types";
	private final String validMembershipTypeUuid = "9d4e894f-30e4-488e-9689-ad0fa32a69d1";
	private final MembershipTypeRequest validMembershipTypeRequest = MembershipTypeRequest.builder()
		.type("Example ty	pe")
		.currencyCode("PLN")
		.price(BigDecimal.valueOf(12.34))
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

		mockMvc.perform(MockMvcRequestBuilders.get(endpointUri + "/" + validMembershipTypeUuid))
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
					.content(TestJsonHelper.stringify(validMembershipTypeRequest))
			).andExpect(MockMvcResultMatchers.status().isCreated());
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
//
//	@Test
//	void checkIfPatchUpdateReturnsHttp200AndUpdatedRecord() throws Exception {
//		String typeNameToUpdate = "Example type updated";
//		mockMvc.perform(
//				MockMvcRequestBuilders.patch(endpointUri + "/" + validMembershipTypeUuid)
//					.contentType(MediaType.APPLICATION_JSON)
//					.content(TestJsonHelper.toJSONField("type", typeNameToUpdate))
//			).andExpect(MockMvcResultMatchers.status().isOk())
//			.andExpect(MockMvcResultMatchers.jsonPath("$.type").value(typeNameToUpdate));
//	}

	@Test
	void checkIfPatchUpdateOfNonExistingRecordReturnsHttp404() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.patch(endpointUri + "/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validMembershipTypeRequest))
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
