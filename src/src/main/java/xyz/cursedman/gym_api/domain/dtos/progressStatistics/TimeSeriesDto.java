package xyz.cursedman.gym_api.domain.dtos.progressStatistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSeriesDto {
	private List<String> labels;
	private List<Integer> values;
}
