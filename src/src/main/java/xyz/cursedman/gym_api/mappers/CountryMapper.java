package xyz.cursedman.gym_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import xyz.cursedman.gym_api.domain.dtos.country.CountryDto;
import xyz.cursedman.gym_api.domain.entities.Country;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CountryMapper {
	CountryDto toDto(Country country);
}
