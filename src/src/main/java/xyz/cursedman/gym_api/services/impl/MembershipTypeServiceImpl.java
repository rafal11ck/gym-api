package xyz.cursedman.gym_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeDto;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeRequest;
import xyz.cursedman.gym_api.domain.entities.MembershipType;
import xyz.cursedman.gym_api.exceptions.NotFoundException;
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
	public MembershipType getMembershipTypeEntity(UUID id) {
		return membershipTypeRepository.findById(id)
			.orElse(null);
	}

	@Override
	public MembershipTypeDto getMembershipType(UUID id) {
		return membershipTypeRepository
			.findById(id)
			.map(membershipTypeMapper::toDtoFromEntity)
			.orElseThrow(() -> new NotFoundException("Membership type with id " + id + " not found"));
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
	)  {

		MembershipType membershipType = membershipTypeRepository
			.findById(membershipTypeId)
			.orElseThrow(() -> new NotFoundException("Membership type with id " + membershipTypeId + " not found"));

		membershipTypeMapper.updateFromRequest(request, membershipType);
		MembershipType result = membershipTypeRepository.save(membershipType);

		return membershipTypeMapper.toDtoFromEntity(result);
	}
}
