package xyz.cursedman.gym_api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.domain.dtos.maintenanceTask.MaintenanceTaskDto;
import xyz.cursedman.gym_api.domain.dtos.maintenanceTask.MaintenanceTaskRequest;
import xyz.cursedman.gym_api.services.MaintenanceTaskService;

import java.util.UUID;

@RestController
@RequestMapping("/maintenance-tasks")
@RequiredArgsConstructor
public class MaintenanceTaskController {
	private final MaintenanceTaskService maintenanceTaskService;

	@GetMapping
	public ResponseEntity<Page<MaintenanceTaskDto>> listMaintenanceTasks(@ParameterObject Pageable pageable) {
		return ResponseEntity.ok(maintenanceTaskService.listMaintenanceTasks(pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<MaintenanceTaskDto> getMaintenanceTask(@Valid @PathVariable UUID id) {
		MaintenanceTaskDto taskDto = maintenanceTaskService.getMaintenanceTask(id);
		return ResponseEntity.ok(taskDto);
	}

	@PostMapping
	public ResponseEntity<MaintenanceTaskDto> createMaintenanceTask(
		@Valid @RequestBody MaintenanceTaskRequest request
	) {
		MaintenanceTaskDto createdTask = maintenanceTaskService.createMaintenanceTask(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<MaintenanceTaskDto> updateMaintenanceTask(
		@Valid @PathVariable UUID id,
		@RequestBody MaintenanceTaskRequest request
	) {
		return ResponseEntity.ok(maintenanceTaskService.patchMaintenanceTask(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMaintenanceTask(@Valid @PathVariable UUID id) {
		maintenanceTaskService.deleteMaintenanceTask(id);
		return ResponseEntity.noContent().build();
	}
}
