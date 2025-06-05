package xyz.cursedman.gym_api.mappers;

import org.mapstruct.*;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeDto;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeRequest;
import xyz.cursedman.gym_api.domain.entities.MembershipType;

import java.util.Currency;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MembershipTypeMapper {

	// Map Request to Entity
	@Mapping(target = "currency", source = "currencyCode")
	MembershipType toEntityFromRequest(MembershipTypeRequest request);

	// Map Entity to Request
	@Mapping(target = "currencyCode", source = "currency")
	MembershipTypeRequest toRequestFromEntity(MembershipType entity);

	// Map Entity to DTO
	@Mapping(target = "currency", source = "currency")
	MembershipTypeDto toDto(MembershipType entity);

	// Map DTO to Entity (if needed)
	MembershipType toEntity(MembershipTypeDto dto);

	// Update entity from request
	void updateFromRequest(MembershipTypeRequest request, @MappingTarget MembershipType entity);

	// Helper: String → Currency
	default Currency map(String currencyCode) {
		return Currency.getInstance(currencyCode);
	}

	// Helper: Currency → String
	default String map(Currency currency) {
		return currency.getCurrencyCode();
	}
}
