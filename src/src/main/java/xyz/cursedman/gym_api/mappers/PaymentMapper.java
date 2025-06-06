package xyz.cursedman.gym_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.dtos.payment.PaymentDto;
import xyz.cursedman.gym_api.domain.entities.Payment;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface PaymentMapper {
	@Mapping(target = "status", source = "status")
	PaymentDto toPaymentDto(Payment payment);
}
