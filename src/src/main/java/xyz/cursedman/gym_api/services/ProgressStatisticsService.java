package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.progressStatistics.ProgressOverviewDto;

import java.util.UUID;

public interface ProgressStatisticsService {
	ProgressOverviewDto getUserProgressOverview(UUID userUuid);
}
