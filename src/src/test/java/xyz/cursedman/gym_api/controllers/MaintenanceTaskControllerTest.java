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
import xyz.cursedman.gym_api.domain.dtos.maintenanceTask.MaintenanceTaskRequest;
import xyz.cursedman.gym_api.helpers.TestJsonHelper;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MaintenanceTaskControllerTest {
	@Autowired
	private MockMvc mockMvc;

	private final String endpointUri = "/maintenance-tasks";

	private final String validMaintenanceTaskUuid = "596706d3-6095-44b8-9f0d-8fd30ab9b847";

	private final String validMaintainerUuid = "1953df3d-9015-4c2e-b314-e4a5ef771e62";

	private final String validMaintenanceHallUuid = "ce5f8d01-6fa8-4226-97fc-51d3e9cd91e5";

	private final MaintenanceTaskRequest validMaintenanceTaskRequest = MaintenanceTaskRequest.builder()
		.maintainerUuid(UUID.fromString(validMaintainerUuid))
		.hallUuid(UUID.fromString(validMaintenanceHallUuid))
		.plannedStartDate(new Date())
		.plannedEndDate(new Date())
		.description("description")
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
		mockMvc.perform(MockMvcRequestBuilders.get(endpointUri + "/" + validMaintenanceTaskUuid))
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
				.content(TestJsonHelper.stringify(validMaintenanceTaskRequest))
		).andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(
			TestJsonHelper.contentEqualsJsonOf(
				validMaintenanceTaskRequest,
				"maintainerUuid", "hallUuid"
			)
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.maintainer.uuid", Matchers.is(validMaintainerUuid))
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.maintenanceHall.uuid", Matchers.is(validMaintenanceHallUuid))
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
		String maintainerUuidToUpdate = "f18d2783-77f6-4f3d-a58e-f72bb31600c6";
		mockMvc.perform(
			MockMvcRequestBuilders.patch(endpointUri + "/" + validMaintenanceTaskUuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.toJSONField("maintainerUuid", maintainerUuidToUpdate))
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(
			MockMvcResultMatchers.jsonPath("$.maintainer.uuid", Matchers.is(maintainerUuidToUpdate))
		);
	}

	@Test
	void checkIfPatchUpdateOfNonExistingRecordReturnsHttp404() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.patch(endpointUri + "/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestJsonHelper.stringify(validMaintenanceTaskRequest))
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	// DELETE

	@Test
	void checkIfDeleteReturnsHttp204AndEmptyBody() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.delete(endpointUri + "/" + validMaintenanceTaskUuid)
		).andExpect(MockMvcResultMatchers.status().isNoContent())
		.andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
	}

	@Test
	void checkIfDeleteNonExistingRecordReturnsHttp404() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.delete(endpointUri + "/" + UUID.randomUUID())
		).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
