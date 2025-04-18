package xyz.cursedman.gym_api.mappers;

import org.mapstruct.*;
import xyz.cursedman.gym_api.domain.dtos.card.CardDto;
import xyz.cursedman.gym_api.domain.dtos.card.CardRequest;
import xyz.cursedman.gym_api.domain.entities.Card;
import xyz.cursedman.gym_api.services.CountryService;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = CountryService.class)
public interface CardMapper extends GenericMapper<Card, CardDto, CardRequest> {
	@Override
	@Mapping(target = "countryUuid", source = "country.uuid")
	CardRequest toRequestFromEntity(Card dto);

	@Override
	@Mapping(target = "country", source = "countryUuid")
	Card toEntityFromRequest(CardRequest request);
}
