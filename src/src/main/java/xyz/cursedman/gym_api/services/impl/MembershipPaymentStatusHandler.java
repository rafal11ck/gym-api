package xyz.cursedman.gym_api.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.entities.Payment;
import xyz.cursedman.gym_api.domain.entities.PaymentExternalRefType;
import xyz.cursedman.gym_api.domain.entities.PaymentStatusEnum;
import xyz.cursedman.gym_api.services.MembershipService;
import xyz.cursedman.gym_api.services.PaymentStatusHandler;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MembershipPaymentStatusHandler implements PaymentStatusHandler {
	private MembershipService membershipService;

	@Override
	public boolean supports(Payment payment) {
		return payment.getExternalRefType().equals(PaymentExternalRefType.MEMBERSHIP);

	}

	@Override
	public void handle(Payment payment, PaymentStatusEnum newStatus) {
		if (newStatus == PaymentStatusEnum.COMPLETED) {
			UUID memberShipId = payment.getExternalRefId();
			membershipService.activateMembership(memberShipId);
		}
	}
}
