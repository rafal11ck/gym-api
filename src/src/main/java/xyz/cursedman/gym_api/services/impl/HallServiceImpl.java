package xyz.cursedman.gym_api.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.hall.HallDto;
import xyz.cursedman.gym_api.domain.dtos.hall.HallRequest;
import xyz.cursedman.gym_api.domain.entities.Hall;
import xyz.cursedman.gym_api.exceptions.NotFoundException;
import xyz.cursedman.gym_api.mappers.HallMapper;
import xyz.cursedman.gym_api.repositories.HallRepository;
import xyz.cursedman.gym_api.services.HallService;

import java.util.UUID;

@Service
@AllArgsConstructor
public class HallServiceImpl implements HallService {

	private final HallRepository hallRepository;

	private final HallMapper hallMapper;

	@Override
	public Page<HallDto> listHalls(Pageable pageable) {
		return hallRepository.findAll(pageable)
			.map(hallMapper::toDtoFromEntity);
	}

	@Override
	public HallDto getHall(UUID id) {
		return hallRepository.findById(id).map(hallMapper::toDtoFromEntity).orElseThrow(NotFoundException::new);
	}

	@Override
	public HallDto createHall(HallRequest request) {
		Hall hall = hallMapper.toEntityFromRequest(request);
		Hall result = hallRepository.save(hall);
		return hallMapper.toDtoFromEntity(result);
	}

	@Override
	public HallDto patchHall(UUID id, HallRequest request) {
		Hall hall = hallRepository.findById(id).orElseThrow(NotFoundException::new);
		hallMapper.updateFromRequest(request, hall);

		Hall result = hallRepository.save(hall);
		return hallMapper.toDtoFromEntity(result);
	}

	@Override
	public Hall findHallByUuid(UUID id) {
		if (id == null) return null;
		return hallRepository.findById(id).orElse(null);
	}
}
