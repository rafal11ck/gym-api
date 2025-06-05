package xyz.cursedman.gym_api.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.payment.PaymentDto;
import xyz.cursedman.gym_api.domain.entities.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper  {
	PaymentDto toPaymentDto(Payment payment);
}
