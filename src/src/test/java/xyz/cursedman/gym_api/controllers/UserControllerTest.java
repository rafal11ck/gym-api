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
import java.util.Arrays;
import java.util.Date;
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

	int correctThisWeekTotalSets = 3;

	int correctLastWeekTotalSets = 2;

	int correctThisWeekTotalVolume = 540;

	int correctLastWeekTotalVolume = 195;

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
		float correctWeeklyTotalSetsTrend = (correctLastWeekTotalSets != 0) ?
			((correctThisWeekTotalSets - correctLastWeekTotalSets) / (float) correctLastWeekTotalSets * 100) : 0f;

		float correctWeeklySessionVolumeTrend = (correctLastWeekTotalVolume != 0) ?
			((correctThisWeekTotalVolume - correctLastWeekTotalVolume) / (float) correctLastWeekTotalVolume * 100) : 0f;

		mockMvc.perform(MockMvcRequestBuilders.get(
				endpointUri + "/" + validUserUuid + "/progress/overview"
			)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(
				MockMvcResultMatchers
					.jsonPath("$.weeklyTotalSets.value")
					.value(correctThisWeekTotalSets)
			).andExpect(
				MockMvcResultMatchers
					.jsonPath("$.weeklyTotalSets.trend")
					.value(Math.round(correctWeeklyTotalSetsTrend))
			).andExpect(
				MockMvcResultMatchers
					.jsonPath("$.weeklySessionVolume.value")
					.value(correctThisWeekTotalVolume)
			).andExpect(
				MockMvcResultMatchers
					.jsonPath("$.weeklySessionVolume.trend")
					.value(Math.round(correctWeeklySessionVolumeTrend))
			);
	}

	@Test
	void checkIfGetUserTotalChartDataReturnsHttp200AndCorrectData() throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd");
		LocalDate today = LocalDate.now(clock);
		LocalDate startOfThisWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		LocalDate startOfLastWeek = startOfThisWeek.minusWeeks(1);
		String thisWeekLabel = startOfThisWeek.format(formatter);
		String lastWeekLabel = startOfLastWeek.format(formatter);
		int requestedNumberOfWeeks = 6;

		// with default request param
		mockMvc.perform(
			MockMvcRequestBuilders.get(endpointUri + "/" + validUserUuid + "/progress/charts/total-effort")
		).andExpect(
			MockMvcResultMatchers.status().isOk()
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.data[0].timeSeries.labels[10]").value(lastWeekLabel)
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.data[0].timeSeries.labels[11]").value(thisWeekLabel)
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.data[0].timeSeries.values[10]").value(correctLastWeekTotalVolume)
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.data[0].timeSeries.values[11]").value(correctThisWeekTotalVolume)
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.data[1].timeSeries.labels[10]").value(lastWeekLabel)
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.data[1].timeSeries.labels[11]").value(thisWeekLabel)
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.data[1].timeSeries.values[10]").value(correctLastWeekTotalSets)
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.data[1].timeSeries.values[11]").value(correctThisWeekTotalSets)
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
			MockMvcResultMatchers.jsonPath("$.data[0].timeSeries.labels.length()").value(requestedNumberOfWeeks)
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.data[0].timeSeries.values.length()").value(requestedNumberOfWeeks)
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.data[0].timeSeries.labels[5]").value(thisWeekLabel)
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.data[0].timeSeries.values[4]").value(correctLastWeekTotalVolume)
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.data[0].timeSeries.values[5]").value(correctThisWeekTotalVolume)
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.data[1].timeSeries.labels.length()").value(requestedNumberOfWeeks)
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.data[1].timeSeries.values.length()").value(requestedNumberOfWeeks)
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.data[1].timeSeries.labels[4]").value(lastWeekLabel)
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.data[1].timeSeries.labels[5]").value(thisWeekLabel)
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.data[1].timeSeries.values[4]").value(correctLastWeekTotalSets)
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.data[1].timeSeries.values[5]").value(correctThisWeekTotalSets)
		);
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
