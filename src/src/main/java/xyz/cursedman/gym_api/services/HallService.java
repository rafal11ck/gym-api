package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.hall.HallDto;
import xyz.cursedman.gym_api.domain.dtos.hall.HallRequest;
import xyz.cursedman.gym_api.domain.entities.Hall;

import java.util.List;
import java.util.UUID;

public interface HallService {
	List<HallDto> listHalls();

	HallDto getHall(UUID id);

	HallDto createHall(HallRequest cardToCreate);

	HallDto patchHall(UUID id, HallRequest request);

	// used by mapper
	Hall findHallByUuid(UUID id);
}
