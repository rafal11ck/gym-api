package xyz.cursedman.gym_api.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.cursedman.gym_api.domain.dtos.user.UserRequest;
import xyz.cursedman.gym_api.helpers.TestJsonHelper;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(roles = {"CLIENT"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest {

	private final String endpointUri = "/users";

	private final String validUserUuid = "65f40335-135a-47ec-ad7d-72278c4be65c";

	private final String validCardUuid = "5bd05494-155e-4bd1-b14c-61421d0caaae";

	private final String validMembershipUuid = "ddd5f2a7-157e-4bf1-b11c-fa46e0d6bad1";

	private final UserRequest validUserRequest = UserRequest.builder()
		.firstName("John")
		.lastName("Pork")
		.dateOfBirth(new Date())
		.email("email")
		.imageUrl("https://picsum.photos/200")
		.phoneNumber("123123123")
		.cardUuid(UUID.fromString(validCardUuid))
		.membershipUuid(UUID.fromString(validMembershipUuid))
		.build();

	@Autowired
	private MockMvc mockMvc;

	@Qualifier("testClock")
	@Autowired
	private Clock clock;

	@TestConfiguration
	static class TestClockConfig {
		@Bean
		@Primary
		public Clock testClock() {
			LocalDate fixedDate = LocalDate.of(2025, 6, 3);
			return Clock.fixed(fixedDate.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
		}
	}

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
	void checkIfGetNonExistingUserRecordReturns404() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(endpointUri + "/" + UUID.randomUUID()))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	// TODO: make new stats tests

	// GET

	@Test
	void checkIfGetUserLastWorkoutSessionReturnsHttp200AndCorrectData() throws Exception {
		String expectedLastSessionDate = LocalDate
			.of(2025, 6, 3)
			.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		mockMvc.perform(MockMvcRequestBuilders.get(endpointUri + "/" + validUserUuid + "/last-workout-session"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.date").value(expectedLastSessionDate));
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
