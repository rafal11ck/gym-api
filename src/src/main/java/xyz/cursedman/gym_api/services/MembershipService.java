package xyz.cursedman.gym_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.cursedman.gym_api.domain.dtos.membership.MembershipDto;
import xyz.cursedman.gym_api.domain.dtos.membership.MembershipRequest;
import xyz.cursedman.gym_api.domain.dtos.payment.PaymentDto;
import xyz.cursedman.gym_api.domain.entities.Membership;

import java.util.List;
import java.util.UUID;

public interface MembershipService {

	MembershipDto activateMembership(UUID membershipId);

	Page<MembershipDto> listMemberships(Pageable pageable);

	MembershipDto getMembership(UUID id);

	MembershipDto createMembership(MembershipRequest request);

	MembershipDto patchMembership(UUID id, MembershipRequest request);

	// used by mapper
	Membership getMembershipById(UUID id);

	List<PaymentDto> getMembershipPayments(UUID id);
}
