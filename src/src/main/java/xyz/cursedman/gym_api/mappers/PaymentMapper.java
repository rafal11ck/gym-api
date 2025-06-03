package xyz.cursedman.gym_api.mappers;

import org.mapstruct.Mapper;
import xyz.cursedman.gym_api.domain.dtos.payment.PaymentDto;
import xyz.cursedman.gym_api.domain.entities.Payment;

@Mapper
public interface PaymentMapper {
	PaymentDto toPaymentDto(Payment payment);
}
