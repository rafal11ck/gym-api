package xyz.cursedman.gym_api.mappers;

import org.mapstruct.*;
import xyz.cursedman.gym_api.domain.dtos.card.CardDto;
import xyz.cursedman.gym_api.domain.dtos.card.CardRequest;
import xyz.cursedman.gym_api.domain.entities.Card;
import xyz.cursedman.gym_api.services.impl.CountryServiceImpl;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = CountryServiceImpl.class)
public interface CardMapper extends EntityRequestMapper<Card, CardDto, CardRequest> {
	@Override
	@Mapping(target = "countryUuid", source = "country.uuid")
	CardRequest toRequestFromEntity(Card entity);

	@Override
	@Mapping(target = "country", source = "countryUuid")
	Card toEntityFromRequest(CardRequest request);

	@Override
	@Mapping(target = "country", source = "countryUuid")
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromRequest(CardRequest request, @MappingTarget Card entity);
}
