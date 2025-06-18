package xyz.cursedman.gym_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.maintenanceTask.MaintenanceTaskDto;
import xyz.cursedman.gym_api.domain.dtos.maintenanceTask.MaintenanceTaskRequest;
import xyz.cursedman.gym_api.domain.entities.MaintenanceTask;
import xyz.cursedman.gym_api.exceptions.NotFoundException;
import xyz.cursedman.gym_api.mappers.MaintenanceTaskMapper;
import xyz.cursedman.gym_api.repositories.MaintenanceTaskRepository;
import xyz.cursedman.gym_api.services.MaintenanceTaskService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaintenanceTaskServiceImpl implements MaintenanceTaskService {

	private final MaintenanceTaskRepository maintenanceTaskRepository;

	private final MaintenanceTaskMapper maintenanceTaskMapper;

	@Override
	public Page<MaintenanceTaskDto> listMaintenanceTasks(Pageable pageable) {
		return maintenanceTaskRepository.findAll(pageable).map(maintenanceTaskMapper::toDtoFromEntity);
	}

	@Override
	public MaintenanceTaskDto getMaintenanceTask(UUID id) throws EntityNotFoundException {
		return maintenanceTaskRepository
			.findById(id)
			.map(maintenanceTaskMapper::toDtoFromEntity)
			.orElseThrow(() -> new NotFoundException("Maintenance task with id " + id + " not found"));
	}

	@Override
	public MaintenanceTaskDto createMaintenanceTask(MaintenanceTaskRequest request) {
		MaintenanceTask task = maintenanceTaskMapper.toEntityFromRequest(request);

		MaintenanceTask result = maintenanceTaskRepository.save(task);
		return maintenanceTaskMapper.toDtoFromEntity(result);
	}

	@Override
	public MaintenanceTaskDto patchMaintenanceTask(UUID id, MaintenanceTaskRequest request) {
		MaintenanceTask task = maintenanceTaskRepository.findById(id).orElseThrow(NotFoundException::new);
		maintenanceTaskMapper.updateFromRequest(request, task);

		MaintenanceTask result = maintenanceTaskRepository.save(task);
		return maintenanceTaskMapper.toDtoFromEntity(result);
	}

	@Override
	public void deleteMaintenanceTask(UUID id) throws EntityNotFoundException {
		if (!maintenanceTaskRepository.existsById(id)) {
			throw new NotFoundException("Maintenance task with id " + id + " not found");
		}

		maintenanceTaskRepository.deleteById(id);
	}
}
