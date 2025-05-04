package xyz.cursedman.gym_api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cursedman.gym_api.domain.dtos.hallType.HallTypeDto;
import xyz.cursedman.gym_api.services.HallTypeService;

import java.util.List;

@RestController
@RequestMapping(path = "/hall-types")
@RequiredArgsConstructor
public class HallTypeController {
	private final HallTypeService hallTypeService;

	@GetMapping
	public ResponseEntity<List<HallTypeDto>> listHallTypes() {
		return ResponseEntity.ok(hallTypeService.listHallTypes());
	}
}
