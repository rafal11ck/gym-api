package xyz.cursedman.gym_api.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.membership.MembershipDto;
import xyz.cursedman.gym_api.domain.dtos.payment.PaymentDto;
import xyz.cursedman.gym_api.domain.entities.Payment;
import xyz.cursedman.gym_api.domain.entities.PaymentExternalRefType;
import xyz.cursedman.gym_api.domain.entities.PaymentStatusEnum;
import xyz.cursedman.gym_api.mappers.PaymentMapper;
import xyz.cursedman.gym_api.repositories.PaymentRepository;
import xyz.cursedman.gym_api.services.MembershipService;
import xyz.cursedman.gym_api.services.PaymentProvider;
import xyz.cursedman.gym_api.services.PaymentService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MembershipPaymentFacade {

	private final PaymentRepository paymentRepository;

	private final MembershipService membershipService;

	private final PaymentProvider paymentProvider;

	private final PaymentMapper paymentMapper;

	private final PaymentService paymentService;

	public URI getPaymentURIFor(UUID id) {
		MembershipDto membership = membershipService.getMembership(id);
		Payment payment = Payment.builder()
			.externalRefId(id)
			.externalRefType(PaymentExternalRefType.MEMBERSHIP)
			.currency(membership.getMembershipType().getCurrency())
			.price(membership.getMembershipType().getPrice())
			.status(PaymentStatusEnum.PENDING)
			.build();

		Payment savedPayment = paymentRepository.save(payment);
		return paymentProvider.getPaymentUri(paymentMapper.toPaymentDto(savedPayment));
	}

	public List<PaymentDto> listPaymentsFor(UUID id) {
		return paymentService.listPaymentsForExternalRef(id, PaymentExternalRefType.MEMBERSHIP);
	}
}
