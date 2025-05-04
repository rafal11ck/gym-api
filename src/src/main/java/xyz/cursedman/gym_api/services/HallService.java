package xyz.cursedman.gym_api.services;

import jakarta.persistence.EntityNotFoundException;
import xyz.cursedman.gym_api.domain.dtos.hall.HallDto;
import xyz.cursedman.gym_api.domain.dtos.hall.HallRequest;

import java.util.List;
import java.util.UUID;

public interface HallService {
	List<HallDto> listHalls();

	HallDto getHall(UUID id) throws EntityNotFoundException;

	HallDto createHall(HallRequest cardToCreate);

	HallDto patchHall(UUID id, HallRequest request) throws EntityNotFoundException;
}
