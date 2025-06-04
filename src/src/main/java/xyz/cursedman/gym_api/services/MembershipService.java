package xyz.cursedman.gym_api.services;

import jakarta.validation.Valid;
import xyz.cursedman.gym_api.domain.dtos.membership.MembershipDto;
import xyz.cursedman.gym_api.domain.dtos.membership.MembershipRequest;
import xyz.cursedman.gym_api.domain.dtos.payment.PaymentDto;
import xyz.cursedman.gym_api.domain.entities.Membership;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface MembershipService {

	MembershipDto activateMembership(UUID membershipId);

	List<MembershipDto> listMemberships();

	MembershipDto getMembership(UUID id);

	MembershipDto createMembership(MembershipRequest request);

	MembershipDto patchMembership(UUID id, MembershipRequest request);

	// used by mapper
	Membership getMembershipById(UUID id);

	Collection<PaymentDto> getMembershipPayments(UUID id);

	URI getPaymentURIFor(@Valid UUID id);
}
