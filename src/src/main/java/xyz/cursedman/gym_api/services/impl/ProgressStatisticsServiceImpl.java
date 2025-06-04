package xyz.cursedman.gym_api.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.progressStatistics.*;
import xyz.cursedman.gym_api.domain.entities.WorkoutSession;
import xyz.cursedman.gym_api.domain.entities.WorkoutSessionExercise;
import xyz.cursedman.gym_api.repositories.ExerciseRepository;
import xyz.cursedman.gym_api.services.ProgressStatisticsService;
import xyz.cursedman.gym_api.services.WorkoutSessionExerciseService;
import xyz.cursedman.gym_api.services.WorkoutSessionService;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class ProgressStatisticsServiceImpl implements ProgressStatisticsService {

	private final Clock clock;

	private final WorkoutSessionExerciseService workoutSessionExerciseService;

	private final WorkoutSessionService workoutSessionService;

	private final ExerciseRepository exerciseRepository;

	private int getPercentageChange(Number previousValue, Number currentValue) {
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
		float totalVolume = exercises.stream()
			.map(exercise -> exercise.getWeight() * exercise.getReps())
			.reduce(0f, Float::sum);

		return (float) Math.round(totalVolume);
	}

	private void forEachWeekFromDate(LocalDate from, int weeksBack, BiConsumer<LocalDate, LocalDate> actionPerWeek) {
		LocalDate startOfCurrentWeek = from.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		for (int i = weeksBack - 1; i >= 0; i--) {
			LocalDate startOfWeek = startOfCurrentWeek.minusWeeks(i);
			LocalDate endOfWeek = startOfWeek.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
			actionPerWeek.accept(startOfWeek, endOfWeek);
		}
	}

	private String getTimeSeriesLabel(LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern("MM.dd"));
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
			.getExercisesFromSessionsInDateRange(sessions, startOfThisWeek, endOfThisWeek);

		List<WorkoutSessionExercise> lastWeekExercises = workoutSessionExerciseService
			.getExercisesFromSessionsInDateRange(sessions, startOfLastWeek, endOfLastWeek);

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

		return new ProgressOverviewDto(weeklyTotalSetsStatistic, weeklySessionVolumeStatistic);
	}

	@Override
	public ChartDto getUserTotalChartData(UUID userUuid, Integer numberOfWeeks) {
		List<WorkoutSession> sessions = workoutSessionService.findWorkoutSessionsByAttendantUuid(userUuid);
		List<String> labels = new ArrayList<>();
		List<Float> volumeValues = new ArrayList<>();
		List<Float> workoutSetsValues = new ArrayList<>();
		LocalDate today = LocalDate.now(clock);

		forEachWeekFromDate(today, numberOfWeeks, (startOfWeek, endOfWeek) -> {
			List<WorkoutSessionExercise> exercises = workoutSessionExerciseService
				.getExercisesFromSessionsInDateRange(sessions, startOfWeek, endOfWeek);

			labels.add(getTimeSeriesLabel(startOfWeek));
			volumeValues.add(getTotalVolumeFromExercises(exercises));
			workoutSetsValues.add((float) exercises.size());
		});

		return new ChartDto(
			"Total workout volume and sets",
			List.of(
				new ChartDataDto("Volume", new TimeSeriesDto(labels, volumeValues)),
				new ChartDataDto("Sets", new TimeSeriesDto(labels, workoutSetsValues))
			)
		);
	}

	@Override
	public ChartDto getUserExerciseChartData(UUID userUuid, Integer numberOfWeeks) {
		List<WorkoutSession> sessions = workoutSessionService.findWorkoutSessionsByAttendantUuid(userUuid);
		LocalDate today = LocalDate.now(clock);
		List<ChartDataDto> exerciseData = new ArrayList<>();

		exerciseRepository.findAll().forEach(exercise -> {
			List<String> labels = new ArrayList<>();
			List<Float> values = new ArrayList<>();

			forEachWeekFromDate(today, numberOfWeeks, (startOfWeek, endOfWeek) -> {
				float maxWeightThisWeek = workoutSessionExerciseService
					.getExerciseMaxWeightFromSessionsInDateRange(exercise.getUuid(), sessions, startOfWeek, endOfWeek);

				labels.add(getTimeSeriesLabel(startOfWeek));
				values.add(maxWeightThisWeek);
			});

			exerciseData.add(new ChartDataDto(exercise.getName(), new TimeSeriesDto(labels, values)));
		});

		return new ChartDto("Exercise heaviest weight", exerciseData);
	}
}
