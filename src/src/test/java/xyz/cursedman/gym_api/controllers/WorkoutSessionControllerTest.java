package xyz.cursedman.gym_api.controllers;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionAttendantRequest;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionExerciseRequest;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionRequest;
import xyz.cursedman.gym_api.helpers.TestJsonHelper;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(roles = {"CLIENT", "COACH"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WorkoutSessionControllerTest {
	private final String endpointUri = "/workout-sessions";
	private final String validHallUuid = "ce5f8d01-6fa8-4226-97fc-51d3e9cd91e5";
	private final String validCoachUuid = "4a3e70f9-bd65-45c4-a47a-eaae0a0d3d56";
	private final Map<String, String> validWorkoutSessionUuids = Map.of(
		"workout_session_uuid", "8d64dca2-87dc-479f-bcb5-9f91b16d870c",
		"attendant_uuid", "4a3e70f9-bd65-45c4-a47a-eaae0a0d3d56",
		"exercise_uuid", "ee371adf-3ac7-4a0a-a6c2-254990c1c80f",
		"workout_session_exercise_uuid", "0713a057-a183-4f13-be9d-9fe3985db31e"
	);
	private final WorkoutSessionRequest validWorkoutSessionRequest = WorkoutSessionRequest.builder()
		.title("title")
		.description("description")
		.date(LocalDate.now())
		.hallUuid(UUID.fromString(validHallUuid))
		.coachUuid(UUID.fromString(validCoachUuid))
		.build();
	private final WorkoutSessionAttendantRequest validWorkoutSessionAttendantRequest =
		new WorkoutSessionAttendantRequest(UUID.fromString("f18d2783-77f6-4f3d-a58e-f72bb31600c6"));
	private final WorkoutSessionExerciseRequest validWorkoutSessionExerciseRequest
		= WorkoutSessionExerciseRequest.builder()
		.exerciseUuid(UUID.fromString("ee371adf-3ac7-4a0a-a6c2-254990c1c80f"))
		.exerciseOrder(1)
		.reps(1)
		.weight(1f)
		.build();
	@Autowired
	private MockMvc mockMvc;

	// workout sessions

	// GET

	@Test
	void checkIfGetReturnsHttp200AndAllRecords() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(endpointUri))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.greaterThan(0)));
	}

	@Test
	void checkIfGetByIdReturnsHttp200AndRequestedRecord() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get(
					endpointUri + "/" + validWorkoutSessionUuids.get("workout_session_uuid")
				)
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.attendants.length()", Matchers.greaterThan(0)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.exercises.length()", Matchers.greaterThan(0)));
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
				MockMvcRequestBuilders.patch(
						endpointUri + "/" + validWorkoutSessionUuids.get("workout_session_uuid")
					).contentType(MediaType.APPLICATION_JSON)
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

	// session attendants

	// POST

	@Test
	void checkIfAddingAttendantReturnsHttp200AndUpdatedRecord() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post(
						endpointUri + "/" + validWorkoutSessionUuids.get("workout_session_uuid") + "/attendants"
					).contentType(MediaType.APPLICATION_JSON)
					.content(TestJsonHelper.stringify(validWorkoutSessionAttendantRequest))
			).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(
				MockMvcResultMatchers.jsonPath(
					"$.attendants[?(@.uuid == '%s')]",
					validWorkoutSessionAttendantRequest.getUserUuid()
				).exists()
			);
	}

	@Test
	void checkIfAddingAttendantToNonExistingWorkoutSessionReturnsHttp404() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.post(endpointUri + "/" + UUID.randomUUID() + "/attendants")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validWorkoutSessionAttendantRequest))
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void checkIfAddingNonExistingAttendantReturnsHttp404() throws Exception {
		WorkoutSessionAttendantRequest invalidSessionUserRequest = new WorkoutSessionAttendantRequest(UUID.randomUUID());
		mockMvc.perform(
			MockMvcRequestBuilders.post(
					endpointUri + "/" + validWorkoutSessionUuids.get("workout_session_uuid") + "/attendants"
				).contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(invalidSessionUserRequest))
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void checkIfInvalidAttendantBodyReturnsHttp400() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.post(
					endpointUri + "/" + validWorkoutSessionUuids.get("workout_session_uuid") + "/attendants"
				).contentType(MediaType.APPLICATION_JSON)
				.content("{}")
		).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	// DELETE

	@Test
	void checkIfDeletingAttendantUpdatesRecordAndReturnsHttp204() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.delete(
				endpointUri
					+ "/"
					+ validWorkoutSessionUuids.get("workout_session_uuid")
					+ "/attendants/"
					+ validWorkoutSessionUuids.get("attendant_uuid")
			)
		).andExpect(MockMvcResultMatchers.status().isNoContent());

		mockMvc.perform(
			MockMvcRequestBuilders.get(
				endpointUri + "/" + validWorkoutSessionUuids.get("workout_session_uuid")
			)
		).andExpect(
			MockMvcResultMatchers.jsonPath(
				"$.attendants[?(@.uuid == '%s')]",
				validWorkoutSessionUuids.get("attendant_uuid")
			).doesNotExist()
		);
	}

	@Test
	void checkIfDeletingAttendantOfNonExistingWorkoutSessionReturnsHttp404() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.delete(
				endpointUri
					+ "/"
					+ UUID.randomUUID()
					+ "/attendants/"
					+ validWorkoutSessionUuids.get("attendant_uuid")
			)
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void checkIfDeletingNonExistingAttendantReturnsHttp404() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.delete(
				endpointUri
					+ "/"
					+ validWorkoutSessionUuids.get("workout_session_uuid")
					+ "/attendants/"
					+ UUID.randomUUID()
			)
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	// session exercises

	// POST

	@Test
	void checkIfAddingExerciseReturnsHttp200AndUpdatedRecord() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post(
						endpointUri + "/" + validWorkoutSessionUuids.get("workout_session_uuid") + "/exercises"
					).contentType(MediaType.APPLICATION_JSON)
					.content(TestJsonHelper.stringify(validWorkoutSessionExerciseRequest))
			).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(
				MockMvcResultMatchers.jsonPath(
					"$.exercises[?(@.exercise.uuid == '%s')]",
					validWorkoutSessionExerciseRequest.getExerciseUuid()
				).exists()
			);
	}

	@Test
	void checkIfAddingExerciseToNonExistingWorkoutSessionReturnsHttp404() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.post(endpointUri + "/" + UUID.randomUUID() + "/exercises")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validWorkoutSessionExerciseRequest))
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void checkIfAddingNonExistingExerciseReturnsHttp404() throws Exception {
		WorkoutSessionExerciseRequest invalidSessionExerciseRequest = WorkoutSessionExerciseRequest.builder()
			.exerciseUuid(UUID.randomUUID())
			.exerciseOrder(1)
			.reps(1)
			.weight(1f)
			.build();

		mockMvc.perform(
			MockMvcRequestBuilders.post(
					endpointUri + "/" + validWorkoutSessionUuids.get("workout_session_uuid") + "/exercises"
				).contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(invalidSessionExerciseRequest))
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void checkIfInvalidExerciseBodyReturnsHttp400() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.post(
					endpointUri + "/" + validWorkoutSessionUuids.get("workout_session_uuid") + "/exercises"
				).contentType(MediaType.APPLICATION_JSON)
				.content("{}")
		).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	// DELETE

	@Test
	void checkIfDeletingExerciseUpdatesRecordAndReturnsHttp204() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.delete(
				endpointUri
					+ "/"
					+ validWorkoutSessionUuids.get("workout_session_uuid")
					+ "/exercises/"
					+ validWorkoutSessionUuids.get("workout_session_exercise_uuid")
			)
		).andExpect(MockMvcResultMatchers.status().isNoContent());

		mockMvc.perform(
			MockMvcRequestBuilders.get(
				endpointUri + "/" + validWorkoutSessionUuids.get("workout_session_uuid")
			)
		).andExpect(
			MockMvcResultMatchers.jsonPath(
				"$.exercises[?(@.exercise.uuid == '%s')]",
				validWorkoutSessionUuids.get("exercise_uuid")
			).doesNotExist()
		);
	}

	@Test
	void checkIfDeletingExerciseOfNonExistingWorkoutSessionReturnsHttpNotFound() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.delete(
				endpointUri
					+ "/"
					+ UUID.randomUUID()
					+ "/exercises/"
					+ validWorkoutSessionUuids.get("exercise_uuid")
			)
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void checkIfDeletingNonExistingExerciseReturnsHttp404() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.delete(
				endpointUri
					+ "/"
					+ validWorkoutSessionUuids.get("workout_session_uuid")
					+ "/exercises/"
					+ UUID.randomUUID()
			)
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
