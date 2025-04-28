package xyz.cursedman.gym_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import xyz.cursedman.gym_api.domain.dtos.paymentStatus.PaymentStatusDto;
import xyz.cursedman.gym_api.domain.entities.PaymentStatus;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentStatusMapper extends EntityMapper<PaymentStatus, PaymentStatusDto> {
}
