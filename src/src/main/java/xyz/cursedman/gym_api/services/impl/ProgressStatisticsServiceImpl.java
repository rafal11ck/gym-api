package xyz.cursedman.gym_api.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.progressStatistics.*;
import xyz.cursedman.gym_api.domain.entities.WorkoutSession;
import xyz.cursedman.gym_api.domain.entities.WorkoutSessionExercise;
import xyz.cursedman.gym_api.services.ProgressStatisticsService;
import xyz.cursedman.gym_api.services.WorkoutSessionExerciseService;
import xyz.cursedman.gym_api.services.WorkoutSessionService;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProgressStatisticsServiceImpl implements ProgressStatisticsService {

	private final Clock clock;

	private final WorkoutSessionExerciseService workoutSessionExerciseService;

	private final WorkoutSessionService workoutSessionService;

	private Integer getPercentageChange(Number previousValue, Number currentValue) {
		if (previousValue == null || currentValue == null) {
			return 0;
		}

		double previous = previousValue.doubleValue();
		double current = currentValue.doubleValue();

		if (previous == 0f) {
			return current == 0f ? 0 : 100;
		}

		float result = (float) ((current - previous) / previous * 100);
		return Math.round(result);
	}

	private float getTotalVolumeFromExercises(List<WorkoutSessionExercise> exercises) {
		return exercises.stream()
			.map(exercise -> exercise.getWeight() * exercise.getReps())
			.reduce(0f, Float::sum);
	}

	@Override
	public ProgressOverviewDto getUserProgressOverview(UUID userUuid) {
		List<WorkoutSession> sessions = workoutSessionService.findWorkoutSessionsByAttendantUuid(userUuid);

		LocalDate today = LocalDate.now(clock);
		LocalDate startOfThisWeek = today.with(DayOfWeek.MONDAY);
		LocalDate endOfThisWeek = startOfThisWeek.plusDays(6);
		LocalDate startOfLastWeek = startOfThisWeek.minusWeeks(1);
		LocalDate endOfLastWeek = startOfLastWeek.plusDays(6);

		List<WorkoutSessionExercise> thisWeekExercises = workoutSessionExerciseService
			.getExercisesFromSessionsInDateRange(
				sessions,
				startOfThisWeek,
				endOfThisWeek
			);

		List<WorkoutSessionExercise> lastWeekExercises = workoutSessionExerciseService
			.getExercisesFromSessionsInDateRange(
				sessions,
				startOfLastWeek,
				endOfLastWeek
			);

		float thisWeekTotalVolume = getTotalVolumeFromExercises(thisWeekExercises);
		float lastWeekTotalVolume = getTotalVolumeFromExercises(lastWeekExercises);

		ProgressOverviewStatisticDto weeklySessionVolumeStatistic = ProgressOverviewStatisticDto.builder()
			.value(Math.round(thisWeekTotalVolume))
			.trend(getPercentageChange(lastWeekTotalVolume, thisWeekTotalVolume))
			.build();

		ProgressOverviewStatisticDto weeklyTotalSetsStatistic = ProgressOverviewStatisticDto.builder()
			.value(thisWeekExercises.size())
			.trend(getPercentageChange(lastWeekExercises.size(), thisWeekExercises.size()))
			.build();

		return ProgressOverviewDto.builder()
			.weeklySessionVolume(weeklySessionVolumeStatistic)
			.weeklyTotalSets(weeklyTotalSetsStatistic)
			.build();
	}
}
