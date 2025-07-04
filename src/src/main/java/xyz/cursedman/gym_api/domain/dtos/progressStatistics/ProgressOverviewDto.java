package xyz.cursedman.gym_api.domain.dtos.progressStatistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProgressOverviewDto {
	private ProgressOverviewStatisticDto weeklyTotalSets;
	private ProgressOverviewStatisticDto weeklySessionVolume;
}
