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
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionRequest;
import xyz.cursedman.gym_api.helpers.TestJsonHelper;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WorkoutSessionControllerTest {
	@Autowired
	private MockMvc mockMvc;

	private final String endpointUri = "/workout-sessions";

	private final String validWorkoutSessionUuid = "8d64dca2-87dc-479f-bcb5-9f91b16d870c";

	private final String validHallUuid = "ce5f8d01-6fa8-4226-97fc-51d3e9cd91e5";

	private final String validCoachUuid = "4a3e70f9-bd65-45c4-a47a-eaae0a0d3d56";

	private final WorkoutSessionRequest validWorkoutSessionRequest = WorkoutSessionRequest.builder()
		.title("title")
		.description("description")
		.date(new Date())
		.hallUuid(UUID.fromString(validHallUuid))
		.coachUuid(UUID.fromString(validCoachUuid))
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
		mockMvc.perform(MockMvcRequestBuilders.get(endpointUri + "/" + validWorkoutSessionUuid))
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
				.content(TestJsonHelper.stringify(validWorkoutSessionRequest))
		).andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(TestJsonHelper.contentEqualsJsonOf(validWorkoutSessionRequest, "hallUuid", "coachUuid"))
		.andExpect(
			MockMvcResultMatchers.jsonPath("$.hall.uuid", Matchers.is(validHallUuid))
		)
		.andExpect(
			MockMvcResultMatchers.jsonPath("$.coach.uuid", Matchers.is(validCoachUuid))
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
		String hallUuidToUpdate = "9256b9cb-40a6-4d17-b987-180e4e6596e0";
		mockMvc.perform(
				MockMvcRequestBuilders.patch(endpointUri + "/" + validWorkoutSessionUuid)
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestJsonHelper.toJSONField("hallUuid", hallUuidToUpdate))
			).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(
				MockMvcResultMatchers.jsonPath("$.hall.uuid", Matchers.is(hallUuidToUpdate))
			);
	}

	@Test
	void checkIfPatchUpdateOfNonExistingRecordReturnsHttp404() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.patch(endpointUri + "/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validWorkoutSessionRequest))
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
