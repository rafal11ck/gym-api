package xyz.cursedman.gym_api.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.membership.MembershipDto;
import xyz.cursedman.gym_api.domain.dtos.membership.MembershipRequest;
import xyz.cursedman.gym_api.domain.entities.Membership;
import xyz.cursedman.gym_api.exceptions.NotFoundException;
import xyz.cursedman.gym_api.mappers.MembershipMapper;
import xyz.cursedman.gym_api.repositories.MembershipRepository;
import xyz.cursedman.gym_api.services.MembershipService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MembershipServiceImpl implements MembershipService {

	private final MembershipRepository membershipRepository;

	private final MembershipMapper membershipMapper;

	@Override
	public List<MembershipDto> listMemberships() {
		return membershipRepository
			.findAll()
			.stream()
			.map(membershipMapper::toDtoFromEntity)
			.toList();
	}

	@Override
	public MembershipDto getMembership(UUID id) {
		return membershipRepository
			.findById(id)
			.map(membershipMapper::toDtoFromEntity)
			.orElseThrow(() -> new NotFoundException("Membership with id " + id + " not found"));
	}

	@Override
	public MembershipDto createMembership(MembershipRequest request) {
		Membership membershipToSave = membershipMapper.toEntityFromRequest(request);
		Membership savedMembership = membershipRepository.save(membershipToSave);
		return membershipMapper.toDtoFromEntity(savedMembership);
	}

	@Override
	public MembershipDto patchMembership(UUID id, MembershipRequest request) {
		Membership membership = membershipRepository.findById(id).orElseThrow(
			() -> new NotFoundException("Membership with id " + id + " not found"));
		membershipMapper.updateFromRequest(request, membership);

		Membership savedMembership = membershipRepository.save(membership);
		return membershipMapper.toDtoFromEntity(savedMembership);
	}

	@Override
	public Membership getMembershipById(UUID id) {
		return membershipRepository.findById(id).orElseThrow(
			() -> new NotFoundException("Membership with ID " + id + " not found")
		);
	}


}
