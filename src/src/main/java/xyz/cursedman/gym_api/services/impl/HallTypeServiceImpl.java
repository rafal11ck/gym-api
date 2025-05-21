package xyz.cursedman.gym_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.hallType.HallTypeDto;
import xyz.cursedman.gym_api.domain.entities.HallType;
import xyz.cursedman.gym_api.exceptions.NotFoundException;
import xyz.cursedman.gym_api.mappers.HallTypeMapper;
import xyz.cursedman.gym_api.repositories.HallTypeRepository;
import xyz.cursedman.gym_api.services.HallTypeService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HallTypeServiceImpl implements HallTypeService {

	private final HallTypeRepository hallTypeRepository;

	private final HallTypeMapper hallTypeMapper;

	@Override
	public List<HallTypeDto> listHallTypes() {
		return hallTypeRepository.findAll().stream().map(hallTypeMapper::toDtoFromEntity).toList();
	}

	@Override
	public HallType getHallTypeByUuid(UUID id) {
		return hallTypeRepository.findById(id).orElseThrow(
			() -> new NotFoundException("Hall type with ID " + id + " not found")
		);
	}
}
