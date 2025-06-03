package xyz.cursedman.gym_api.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.entities.Membership;
import xyz.cursedman.gym_api.domain.entities.Payment;
import xyz.cursedman.gym_api.domain.entities.PaymentExternalRefType;
import xyz.cursedman.gym_api.domain.entities.PaymentStatusEnum;
import xyz.cursedman.gym_api.repositories.MembershipRepository;
import xyz.cursedman.gym_api.services.PaymentStatusHandler;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MembershipPaymentStatusHandler implements PaymentStatusHandler {

	private final MembershipRepository membershipRepository;


	public Optional<Membership> resolveMembershipFromPayment(Payment payment) {
		if (payment.getExternalRefType() != PaymentExternalRefType.MEMBERSHIP) {
			return Optional.empty();
		}
		return membershipRepository.findById(payment.getExternalRefId());
	}


	@Override
	public boolean supports(Payment payment) {
		return payment.getExternalRefType().equals(PaymentExternalRefType.MEMBERSHIP);
	}


	@Override
	public void handle(Payment payment, PaymentStatusEnum newStatus) {
		if (newStatus == PaymentStatusEnum.COMPLETED) {
			resolveMembershipFromPayment(payment).ifPresent(membership -> {
				membership.setValidFrom(LocalDateTime.from(LocalDateTime.now()));
				membership.setValidUntil(LocalDateTime.from(LocalDateTime.now().plusMonths(1)));

				membershipRepository.save(membership);
			});
		}

	}
}
