package xyz.cursedman.gym_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.cursedman.gym_api.domain.dtos.maintenanceTask.MaintenanceTaskDto;
import xyz.cursedman.gym_api.domain.dtos.maintenanceTask.MaintenanceTaskRequest;

import java.util.UUID;

public interface MaintenanceTaskService {
	Page<MaintenanceTaskDto> listMaintenanceTasks(Pageable pageable);

	MaintenanceTaskDto getMaintenanceTask(UUID id);

	MaintenanceTaskDto createMaintenanceTask(MaintenanceTaskRequest request);

	MaintenanceTaskDto patchMaintenanceTask(UUID id, MaintenanceTaskRequest request);

	void deleteMaintenanceTask(UUID id);
}
