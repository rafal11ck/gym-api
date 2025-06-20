package xyz.cursedman.gym_api.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeDto;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeRequest;
import xyz.cursedman.gym_api.domain.entities.MembershipType;
import xyz.cursedman.gym_api.exceptions.NotFoundException;
import xyz.cursedman.gym_api.mappers.MembershipTypeMapper;
import xyz.cursedman.gym_api.repositories.MembershipTypeRepository;
import xyz.cursedman.gym_api.services.MembershipTypeService;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MembershipTypeServiceImpl implements MembershipTypeService {

	private final MembershipTypeRepository membershipTypeRepository;

	private final MembershipTypeMapper membershipTypeMapper;

	@Override
	public Page<MembershipTypeDto> listMembershipTypes(Pageable pageable) {
		return membershipTypeRepository.findAll(pageable).map(membershipTypeMapper::toDto);
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
			.map(membershipTypeMapper::toDto)
			.orElseThrow(() -> new NotFoundException("Membership type with id " + id + " not found"));
	}

	@Override
	public MembershipTypeDto createMembershipType(MembershipTypeRequest request) {
		MembershipType membershipType = membershipTypeMapper.toEntityFromRequest(request);
		MembershipType result = membershipTypeRepository.save(membershipType);
		return membershipTypeMapper.toDto(result);
	}

	@Override
	public MembershipTypeDto patchMembershipType(
		UUID membershipTypeId,
		MembershipTypeRequest request
	) {

		MembershipType membershipType = membershipTypeRepository
			.findById(membershipTypeId)
			.orElseThrow(() -> new NotFoundException("Membership type with id " + membershipTypeId + " not found"));

		membershipTypeMapper.updateFromRequest(request, membershipType);
		MembershipType result = membershipTypeRepository.save(membershipType);

		return membershipTypeMapper.toDto(result);
	}
}
