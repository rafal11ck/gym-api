package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.hallType.HallTypeDto;

import java.util.List;

public interface HallTypeService {
	List<HallTypeDto> listHallTypes();
}
