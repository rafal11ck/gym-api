package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.membership.MembershipDto;
import xyz.cursedman.gym_api.domain.dtos.membership.MembershipRequest;
import xyz.cursedman.gym_api.domain.entities.Membership;

import java.util.List;
import java.util.UUID;

public interface MembershipService {
	List<MembershipDto> listMemberships();

	MembershipDto createMembership(MembershipRequest request);

	MembershipDto patchMembership(UUID id, MembershipRequest request);

	// used by mapper
	Membership getMembershipById(UUID id);
}
