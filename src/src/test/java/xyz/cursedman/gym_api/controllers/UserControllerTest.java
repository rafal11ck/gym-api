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
@WithMockUser(roles = {"EMPLOYEE", "COACH", "MANAGER"})
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

	private final String expectedThisWeekLabel = "06.02";

	private final String expectedLastWeekLabel = "05.26";

	private final int expectedThisWeekTotalSets = 3;

	private final int expectedLastWeekTotalSets = 2;

	private final int expectedThisWeekTotalVolume = 540;

	private final int expectedLastWeekTotalVolume = 195;

	private final int expectedDefaultNumberOfWeeks = 12;

	private final int requestedNumberOfWeeks = 6;

	private final int defaultWeeksThisWeekDataIndex = expectedDefaultNumberOfWeeks - 1;

	private final int defaultWeeksLastWeekDataIndex = expectedDefaultNumberOfWeeks - 2;

	private final int requestedWeeksThisWeekDataIndex = requestedNumberOfWeeks - 1;

	private final int requestedWeeksLastWeekDataIndex = requestedNumberOfWeeks - 2;

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

	// stats

	// GET

	@Test
	void checkIfGetUserOverviewStatisticsReturnsHttp200AndCorrectStatistics() throws Exception {
		float expectedWeeklyTotalSetsTrend = (
			(expectedThisWeekTotalSets - expectedLastWeekTotalSets) / (float) expectedLastWeekTotalSets * 100
		);

		float expectedWeeklySessionVolumeTrend = (
			(expectedThisWeekTotalVolume - expectedLastWeekTotalVolume) / (float) expectedLastWeekTotalVolume * 100
		);

		mockMvc.perform(MockMvcRequestBuilders.get(
				endpointUri + "/" + validUserUuid + "/progress/overview"
			)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(
				MockMvcResultMatchers
					.jsonPath("$.weeklyTotalSets.value").value(expectedThisWeekTotalSets)
			).andExpect(
				MockMvcResultMatchers
					.jsonPath("$.weeklyTotalSets.trend").value(Math.round(expectedWeeklyTotalSetsTrend))
			).andExpect(
				MockMvcResultMatchers
					.jsonPath("$.weeklySessionVolume.value").value(expectedThisWeekTotalVolume)
			).andExpect(
				MockMvcResultMatchers
					.jsonPath("$.weeklySessionVolume.trend").value(Math.round(expectedWeeklySessionVolumeTrend))
			);
	}

	@Test
	void checkIfGetUserTotalChartDataReturnsHttp200AndCorrectData() throws Exception {
		// with default request param
		mockMvc.perform(
			MockMvcRequestBuilders.get(endpointUri + "/" + validUserUuid + "/progress/charts/total-effort")
		).andExpect(
			MockMvcResultMatchers.status().isOk()
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data[0].timeSeries.labels[" + defaultWeeksLastWeekDataIndex + "]")
				.value(expectedLastWeekLabel)
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data[0].timeSeries.labels[" + defaultWeeksThisWeekDataIndex + "]")
				.value(expectedThisWeekLabel)
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data[0].timeSeries.values[" + defaultWeeksLastWeekDataIndex + "]")
				.value(expectedLastWeekTotalVolume)
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data[0].timeSeries.values[" + defaultWeeksThisWeekDataIndex + "]")
				.value(expectedThisWeekTotalVolume)
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data[1].timeSeries.labels[" + defaultWeeksLastWeekDataIndex + "]")
				.value(expectedLastWeekLabel)
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data[1].timeSeries.labels[" + defaultWeeksThisWeekDataIndex + "]")
				.value(expectedThisWeekLabel)
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data[1].timeSeries.values[" + defaultWeeksLastWeekDataIndex + "]")
				.value(expectedLastWeekTotalSets)
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data[1].timeSeries.values[" + defaultWeeksThisWeekDataIndex + "]")
				.value(expectedThisWeekTotalSets)
		);

		// with request param provided
		mockMvc.perform(
			MockMvcRequestBuilders.get(
				endpointUri
					+ "/"
					+ validUserUuid
					+ "/progress/charts/total-effort?numberOfWeeks="
					+ requestedNumberOfWeeks
			)
		).andExpect(
			MockMvcResultMatchers.status().isOk()
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data[0].timeSeries.labels.length()").value(requestedNumberOfWeeks)
		).andExpect(
			MockMvcResultMatchers.
				jsonPath("$.data[0].timeSeries.values.length()").value(requestedNumberOfWeeks)
		).andExpect(
			MockMvcResultMatchers.
				jsonPath("$.data[0].timeSeries.labels[" + requestedWeeksLastWeekDataIndex + "]")
				.value(expectedLastWeekLabel)
		).andExpect(
			MockMvcResultMatchers.
				jsonPath("$.data[0].timeSeries.labels[" + requestedWeeksThisWeekDataIndex + "]")
				.value(expectedThisWeekLabel)
		).andExpect(
			MockMvcResultMatchers.
				jsonPath("$.data[0].timeSeries.values[" + requestedWeeksLastWeekDataIndex + "]")
				.value(expectedLastWeekTotalVolume)
		).andExpect(
			MockMvcResultMatchers.
				jsonPath("$.data[0].timeSeries.values[" + requestedWeeksThisWeekDataIndex + "]")
				.value(expectedThisWeekTotalVolume)
		).andExpect(
			MockMvcResultMatchers.
				jsonPath("$.data[1].timeSeries.labels.length()")
				.value(requestedNumberOfWeeks)
		).andExpect(
			MockMvcResultMatchers.
				jsonPath("$.data[1].timeSeries.values.length()")
				.value(requestedNumberOfWeeks)
		).andExpect(
			MockMvcResultMatchers.
				jsonPath("$.data[1].timeSeries.labels[" + requestedWeeksLastWeekDataIndex + "]")
				.value(expectedLastWeekLabel)
		).andExpect(
			MockMvcResultMatchers.
				jsonPath("$.data[1].timeSeries.labels[" + requestedWeeksThisWeekDataIndex + "]")
				.value(expectedThisWeekLabel)
		).andExpect(
			MockMvcResultMatchers.
				jsonPath("$.data[1].timeSeries.values[" + requestedWeeksLastWeekDataIndex + "]")
				.value(expectedLastWeekTotalSets)
		).andExpect(
			MockMvcResultMatchers.
				jsonPath("$.data[1].timeSeries.values[" + requestedWeeksThisWeekDataIndex + "]")
				.value(expectedThisWeekTotalSets)
		);
	}

	@Test
	void checkIfGetUserExerciseChartDataReturnsHttp200AndCorrectData() throws Exception {
		int expectedNumberOfExercises = 3;
		float expectedLastWeekWeight = 30;
		float expectedThisWeekWeight = 50;

		// with default request param
		mockMvc.perform(
			MockMvcRequestBuilders.get(
				endpointUri
					+ "/"
					+ validUserUuid
					+ "/progress/charts/exercise-heaviest-weight"
			)
		).andExpect(
			MockMvcResultMatchers.status().isOk()
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data.length()").value(expectedNumberOfExercises)
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data[0].timeSeries.labels[" + defaultWeeksLastWeekDataIndex + "]")
				.value(expectedLastWeekLabel)
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data[0].timeSeries.labels[" + defaultWeeksThisWeekDataIndex + "]")
				.value(expectedThisWeekLabel)
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data[0].timeSeries.values[" + defaultWeeksLastWeekDataIndex + "]")
				.value(expectedLastWeekWeight)
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data[0].timeSeries.values[" + defaultWeeksThisWeekDataIndex + "]")
				.value(expectedThisWeekWeight)
		);

		// with request param provided
		mockMvc.perform(
			MockMvcRequestBuilders.get(
				endpointUri
					+ "/"
					+ validUserUuid
					+ "/progress/charts/exercise-heaviest-weight?numberOfWeeks="
					+ requestedNumberOfWeeks
			)
		).andExpect(
			MockMvcResultMatchers.status().isOk()
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data.length()").value(expectedNumberOfExercises)
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data[0].timeSeries.labels.length()")
				.value(requestedNumberOfWeeks)
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data[0].timeSeries.values.length()")
				.value(requestedNumberOfWeeks)
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data[0].timeSeries.labels[" + requestedWeeksLastWeekDataIndex + "]")
				.value(expectedLastWeekLabel)
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data[0].timeSeries.labels[" + requestedWeeksThisWeekDataIndex + "]")
				.value(expectedThisWeekLabel)
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data[0].timeSeries.values[" + requestedWeeksLastWeekDataIndex + "]")
				.value(expectedLastWeekWeight)
		).andExpect(
			MockMvcResultMatchers
				.jsonPath("$.data[0].timeSeries.values[" + requestedWeeksThisWeekDataIndex + "]")
				.value(expectedThisWeekWeight)
		);
	}

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
