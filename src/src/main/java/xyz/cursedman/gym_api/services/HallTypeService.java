package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.hallType.HallTypeDto;
import xyz.cursedman.gym_api.domain.entities.HallType;

import java.util.List;
import java.util.UUID;

public interface HallTypeService {
	List<HallTypeDto> listHallTypes();

	HallType getHallTypeByUuid(UUID id);
}
