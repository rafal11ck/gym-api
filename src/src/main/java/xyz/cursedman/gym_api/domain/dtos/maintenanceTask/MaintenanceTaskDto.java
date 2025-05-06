package xyz.cursedman.gym_api.domain.dtos.maintenanceTask;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.cursedman.gym_api.domain.entities.Hall;
import xyz.cursedman.gym_api.domain.entities.User;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceTaskDto {
	private UUID uuid;

	private User maintainer;

	private Hall maintenanceHall;

	private Date plannedStartDate;

	private Date plannedEndDate;

	private String description;
}
