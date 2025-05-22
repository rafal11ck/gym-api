package xyz.cursedman.gym_api.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.domain.dtos.hall.HallDto;
import xyz.cursedman.gym_api.domain.dtos.hall.HallRequest;
import xyz.cursedman.gym_api.services.HallService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/halls")
@RequiredArgsConstructor
public class HallController {
	private final HallService hallService;

	@GetMapping
	public ResponseEntity<List<HallDto>> listHalls() {
		return ResponseEntity.ok(hallService.listHalls());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<HallDto> getHall(@Valid @PathVariable UUID id) {
		HallDto hallDto = hallService.getHall(id);
		return ResponseEntity.ok(hallDto);
	}

	@PostMapping
	public ResponseEntity<HallDto> createHall(@Valid @RequestBody HallRequest request) {
		HallDto createdHall = hallService.createHall(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdHall);
	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<HallDto> updateHall(
		@Valid @PathVariable UUID id,
		@RequestBody HallRequest request
	) {
		return ResponseEntity.ok(hallService.patchHall(id, request));
	}
}
