package xyz.cursedman.gym_api.services;

import jakarta.persistence.EntityNotFoundException;
import xyz.cursedman.gym_api.domain.dtos.maintenanceTask.MaintenanceTaskDto;
import xyz.cursedman.gym_api.domain.dtos.maintenanceTask.MaintenanceTaskRequest;

import java.util.List;
import java.util.UUID;

public interface MaintenanceTaskService {
	List<MaintenanceTaskDto> listMaintenanceTasks();

	MaintenanceTaskDto getMaintenanceTask(UUID id) throws EntityNotFoundException;

	MaintenanceTaskDto createMaintenanceTask(MaintenanceTaskRequest request);

	MaintenanceTaskDto patchMaintenanceTask(UUID id, MaintenanceTaskRequest request) throws EntityNotFoundException;

	void deleteMaintenanceTask(UUID id) throws EntityNotFoundException;
}
