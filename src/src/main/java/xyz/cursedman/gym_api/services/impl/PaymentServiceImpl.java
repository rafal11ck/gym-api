package xyz.cursedman.gym_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.payment.PaymentDto;
import xyz.cursedman.gym_api.domain.dtos.payment.PaymentRequest;
import xyz.cursedman.gym_api.domain.entities.Payment;
import xyz.cursedman.gym_api.exceptions.NotFoundException;
import xyz.cursedman.gym_api.mappers.PaymentMapper;
import xyz.cursedman.gym_api.repositories.PaymentRepository;
import xyz.cursedman.gym_api.services.PaymentService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;

	private final PaymentMapper paymentMapper;

	@Override
	public List<PaymentDto> listPayments() {
		return paymentRepository.findAll().stream().map(paymentMapper::toDtoFromEntity).toList();
	}

	@Override
	public PaymentDto getPayment(UUID id)  {
		return paymentRepository
			.findById(id)
			.map(paymentMapper::toDtoFromEntity)
			.orElseThrow(() -> new NotFoundException("Payment with id " + id + " not found"));
	}

	@Override
	public PaymentDto createPayment(PaymentRequest request) {
		Payment payment = paymentMapper.toEntityFromRequest(request);
		Payment result = paymentRepository.save(payment);
		return paymentMapper.toDtoFromEntity(result);
	}

	@Override
	public PaymentDto patchPayment(UUID id, PaymentRequest request)  {
		Payment payment = paymentRepository.findById(id).orElseThrow(NotFoundException::new);
		paymentMapper.updateFromRequest(request, payment);

		Payment result = paymentRepository.save(payment);
		return paymentMapper.toDtoFromEntity(result);
	}
}
