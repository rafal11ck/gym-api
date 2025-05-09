package xyz.cursedman.gym_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.maintenanceTask.MaintenanceTaskDto;
import xyz.cursedman.gym_api.domain.dtos.maintenanceTask.MaintenanceTaskRequest;
import xyz.cursedman.gym_api.domain.entities.MaintenanceTask;
import xyz.cursedman.gym_api.mappers.MaintenanceTaskMapper;
import xyz.cursedman.gym_api.repositories.MaintenanceTaskRepository;
import xyz.cursedman.gym_api.services.MaintenanceTaskService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaintenanceTaskServiceImpl implements MaintenanceTaskService {

	private final MaintenanceTaskRepository maintenanceTaskRepository;

	private final MaintenanceTaskMapper maintenanceTaskMapper;

	@Override
	public List<MaintenanceTaskDto> listMaintenanceTasks() {
		return maintenanceTaskRepository.findAll().stream().map(maintenanceTaskMapper::toDtoFromEntity).toList();
	}

	@Override
	public MaintenanceTaskDto getMaintenanceTask(UUID id) throws EntityNotFoundException {
		return maintenanceTaskRepository
			.findById(id)
			.map(maintenanceTaskMapper::toDtoFromEntity)
			.orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public MaintenanceTaskDto createMaintenanceTask(MaintenanceTaskRequest request) {
		MaintenanceTask task = maintenanceTaskMapper.toEntityFromRequest(request);
		MaintenanceTask result = maintenanceTaskRepository.save(task);
		return maintenanceTaskMapper.toDtoFromEntity(result);
	}

	@Override
	public MaintenanceTaskDto patchMaintenanceTask(UUID id, MaintenanceTaskRequest request)
		throws EntityNotFoundException
	{
		MaintenanceTask task = maintenanceTaskRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		maintenanceTaskMapper.updateFromRequest(request, task);

		MaintenanceTask result = maintenanceTaskRepository.save(task);
		return maintenanceTaskMapper.toDtoFromEntity(result);
	}

	@Override
	public void deleteMaintenanceTask(UUID id) throws EntityNotFoundException {
		if (!maintenanceTaskRepository.existsById(id)) {
			throw new EntityNotFoundException();
		}

		maintenanceTaskRepository.deleteById(id);
	}
}
