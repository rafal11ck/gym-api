package xyz.cursedman.gym_api.mappers;

import org.mapstruct.*;
import xyz.cursedman.gym_api.domain.dtos.card.CardDto;
import xyz.cursedman.gym_api.domain.dtos.card.CreateCardRequest;
import xyz.cursedman.gym_api.domain.dtos.card.UpdateCardRequest;
import xyz.cursedman.gym_api.domain.entities.Card;
import xyz.cursedman.gym_api.services.CountryService;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = CountryService.class)
public interface CardMapper {
	CardDto toDto(Card card);

	@Mapping(source = "country.uuid", target = "countryUuid")
	CreateCardRequest toCreateCardRequest(Card card);

	@Mapping(source = "countryUuid", target = "country")
	Card toEntity(CreateCardRequest request);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromDto(UpdateCardRequest dto, @MappingTarget Card entity);
}
