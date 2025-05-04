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
import xyz.cursedman.gym_api.domain.dtos.hall.HallRequest;
import xyz.cursedman.gym_api.helpers.TestJsonHelper;

import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class HallControllerTest {
	@Autowired
	private MockMvc mockMvc;

	private final String endpointUri = "/halls";

	private final String validHallUuid = "ce5f8d01-6fa8-4226-97fc-51d3e9cd91e5";

	private final String validHallTypeUuid = "2a2fa2ba-2381-4cb6-86f4-282bdbf18e81";

	private final HallRequest validHallRequest = HallRequest.builder()
		.hallName("hall name")
		.hallDescription("hall desc")
		.hallTypeUuid(UUID.fromString(validHallTypeUuid))
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
		mockMvc.perform(MockMvcRequestBuilders.get(endpointUri + "/" + validHallUuid))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$").exists());
	}

	@Test
	void checkIfGetNonExistingRecordReturns404AndEmptyBody() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(endpointUri + "/" + UUID.randomUUID()))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
	}

	// POST

	@Test
	void checkIfCreateReturnsHttp201AndCreatedRecord() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.post(endpointUri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validHallRequest))
		).andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(TestJsonHelper.contentEqualsJsonOf(validHallRequest, "hallTypeUuid"))
		.andExpect(
			MockMvcResultMatchers.jsonPath("$.hallType.uuid", Matchers.is(validHallTypeUuid))
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
		String hallTypeUuidToUpdate = "cc5f2a2a-1248-4e4f-aed9-5aab7c3f577a";
		mockMvc.perform(
			MockMvcRequestBuilders.patch(endpointUri + "/" + validHallUuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.toJSONField("hallTypeUuid", hallTypeUuidToUpdate))
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(
			MockMvcResultMatchers.jsonPath("$.hallType.uuid", Matchers.is(hallTypeUuidToUpdate))
		);
	}

	@Test
	void checkIfPatchUpdateOfNonExistingRecordReturnsHttp404() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.patch(endpointUri + "/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validHallRequest))
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
