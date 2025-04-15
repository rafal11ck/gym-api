package xyz.cursedman.gym_api.mappers;

import org.mapstruct.*;
import xyz.cursedman.gym_api.domain.dtos.card.CardDto;
import xyz.cursedman.gym_api.domain.dtos.card.CreateCardRequest;
import xyz.cursedman.gym_api.domain.dtos.card.UpdateCardRequest;
import xyz.cursedman.gym_api.domain.entities.Card;
import xyz.cursedman.gym_api.services.CountryService;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = CountryService.class)
public abstract class CardMapper {
	public abstract CardDto toDto(Card card);

	@Mapping(source = "countryUuid", target = "country")
	public abstract Card toEntity(CreateCardRequest request);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	public abstract void updateFromDto(UpdateCardRequest dto, @MappingTarget Card entity);
}
