package xyz.cursedman.gym_api.services;

import jakarta.persistence.EntityNotFoundException;
import xyz.cursedman.gym_api.domain.dtos.membership.MembershipDto;
import xyz.cursedman.gym_api.domain.dtos.membership.MembershipRequest;

import java.util.List;
import java.util.UUID;

public interface MembershipService {
	List<MembershipDto> listMemberships();

	MembershipDto getMembership(UUID id) throws EntityNotFoundException;

	MembershipDto createMembership(MembershipRequest request);

	MembershipDto patchMembership(UUID id, MembershipRequest request) throws EntityNotFoundException;
}
