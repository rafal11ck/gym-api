package xyz.cursedman.gym_api.domain.dtos.maintenanceTask;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceTaskRequest {
	@NotNull
	private UUID maintainerUuid;

	@NotNull
	private UUID hallUuid;

	@NotNull
	private Date plannedStartDate;

	@NotNull
	private Date plannedEndDate;

	@NotNull
	private String description;
}
