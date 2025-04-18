package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.entities.Country;

import java.util.List;
import java.util.UUID;

public interface CountryService {
	List<Country> listCountries();

	// used by mapper under the hood
	Country getCountryByUuid(UUID id);
}
