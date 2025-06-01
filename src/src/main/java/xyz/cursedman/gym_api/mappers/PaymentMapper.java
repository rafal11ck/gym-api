package xyz.cursedman.gym_api.mappers;

import org.mapstruct.*;
import xyz.cursedman.gym_api.domain.dtos.payment.PaymentDto;
import xyz.cursedman.gym_api.domain.dtos.payment.PaymentRequest;
import xyz.cursedman.gym_api.domain.entities.Payment;
import xyz.cursedman.gym_api.services.CardService;
import xyz.cursedman.gym_api.services.MembershipService;
import xyz.cursedman.gym_api.services.PaymentStatusService;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	uses = {CardService.class, PaymentStatusService.class, MembershipService.class}
)
public interface PaymentMapper extends EntityRequestMapper<Payment, PaymentDto, PaymentRequest> {
	@Override
	@Mapping(target = "statusUuid", source = "status.uuid")
	@Mapping(target = "membershipUuid", source = "membership.uuid")
	PaymentRequest toRequestFromEntity(Payment entity);

	@Override
	@Mapping(target = "card", source = "cardUuid")
	@Mapping(target = "status", source = "statusUuid")
	@Mapping(target = "membership", source = "membershipUuid")
	Payment toEntityFromRequest(PaymentRequest request);

	@Override
	@Mapping(target = "card", source = "cardUuid")
	@Mapping(target = "status", source = "statusUuid")
	@Mapping(target = "membership", source = "membershipUuid")
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromRequest(PaymentRequest request, @MappingTarget Payment entity);
}
