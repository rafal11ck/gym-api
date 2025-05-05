package xyz.cursedman.gym_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.hall.HallDto;
import xyz.cursedman.gym_api.domain.dtos.hall.HallRequest;
import xyz.cursedman.gym_api.domain.entities.Hall;
import xyz.cursedman.gym_api.mappers.HallMapper;
import xyz.cursedman.gym_api.repositories.HallRepository;
import xyz.cursedman.gym_api.services.HallService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class HallServiceImpl implements HallService {

	private final HallRepository hallRepository;

	private final HallMapper hallMapper;

	@Override
	public List<HallDto> listHalls() {
		return hallRepository.findAll().stream().map(hallMapper::toDtoFromEntity).toList();
	}

	@Override
	public HallDto getHall(UUID id) throws EntityNotFoundException {
		return hallRepository
			.findById(id)
			.map(hallMapper::toDtoFromEntity)
			.orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public HallDto createHall(HallRequest request) {
		Hall hall = hallMapper.toEntityFromRequest(request);
		Hall result = hallRepository.save(hall);
		return hallMapper.toDtoFromEntity(result);
	}

	@Override
	public HallDto patchHall(UUID id, HallRequest request) throws EntityNotFoundException {
		Hall hall = hallRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		hallMapper.updateFromRequest(request, hall);

		Hall result = hallRepository.save(hall);
		return hallMapper.toDtoFromEntity(result);
	}

	@Override
	public Hall findHallByUuid(UUID id) {
		return hallRepository.findById(id).orElseThrow(
			() -> new EntityNotFoundException("Hall with ID " + id + " not found")
		);
	}
}
