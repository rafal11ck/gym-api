package xyz.cursedman.gym_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeDto;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeRequest;
import xyz.cursedman.gym_api.domain.entities.MembershipType;
import xyz.cursedman.gym_api.mappers.MembershipTypeMapper;
import xyz.cursedman.gym_api.repositories.MembershipTypeRepository;
import xyz.cursedman.gym_api.services.MembershipTypeService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MembershipTypeServiceImpl implements MembershipTypeService {

	private final MembershipTypeRepository membershipTypeRepository;

	private final MembershipTypeMapper membershipTypeMapper;

	@Override
	public List<MembershipTypeDto> listMembershipTypes() {
		return membershipTypeRepository.findAll().stream().map(membershipTypeMapper::toDtoFromEntity).toList();
	}

	@Override
	public MembershipTypeDto getMembershipType(UUID id) throws EntityNotFoundException {
		return membershipTypeRepository
			.findById(id)
			.map(membershipTypeMapper::toDtoFromEntity)
			.orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public MembershipTypeDto createMembershipType(MembershipTypeRequest request) {
		MembershipType membershipType = membershipTypeMapper.toEntityFromRequest(request);
		MembershipType result = membershipTypeRepository.save(membershipType);
		return membershipTypeMapper.toDtoFromEntity(result);
	}

	@Override
	public MembershipTypeDto patchMembershipType(
		UUID membershipTypeId,
		MembershipTypeRequest request
	) throws EntityNotFoundException {

		MembershipType membershipType = membershipTypeRepository
			.findById(membershipTypeId)
			.orElseThrow(EntityNotFoundException::new);

		membershipTypeMapper.updateFromRequest(request, membershipType);
		MembershipType result = membershipTypeRepository.save(membershipType);

		return membershipTypeMapper.toDtoFromEntity(result);
	}
}
