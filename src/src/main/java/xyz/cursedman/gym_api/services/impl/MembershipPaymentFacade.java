package xyz.cursedman.gym_api.services.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.membership.MembershipDto;
import xyz.cursedman.gym_api.domain.entities.Membership;
import xyz.cursedman.gym_api.domain.entities.Payment;
import xyz.cursedman.gym_api.domain.entities.PaymentExternalRefType;
import xyz.cursedman.gym_api.domain.entities.PaymentStatusEnum;
import xyz.cursedman.gym_api.exceptions.NotFoundException;
import xyz.cursedman.gym_api.services.MembershipService;
import xyz.cursedman.gym_api.services.PaymentCoordinator;
import xyz.cursedman.gym_api.services.PaymentStatusHandler;

import java.net.URI;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MembershipPaymentFacade  {
	MembershipService membershipService;
	PaymentCoordinator paymentCoordinator;

	public URI getPaymentURIFor(UUID id) {
		MembershipDto membership =  membershipService.getMembership(id);
		Payment payment = new Payment();
		payment.setExternalRefId(id);
		payment.setExternalRefType(PaymentExternalRefType.MEMBERSHIP);
		payment.setCurrency(membership.getMembershipType().getCurrency());
		payment.setPrice(membership.getMembershipType().getPrice());

		return paymentCoordinator.getPaymentUri(payment);
	}


}
