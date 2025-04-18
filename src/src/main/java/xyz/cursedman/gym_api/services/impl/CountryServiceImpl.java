package xyz.cursedman.gym_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cursedman.gym_api.domain.entities.Country;
import xyz.cursedman.gym_api.mappers.CountryMapper;
import xyz.cursedman.gym_api.repositories.CountryRepository;
import xyz.cursedman.gym_api.services.CountryService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {
	private final CountryRepository countryRepository;
	private final CountryMapper countryMapper;

	@Override
	public List<Country> listCountries() {
		return countryRepository.findAll();
	}

	@Override
	public Country getCountryByUuid(UUID id) {
		return countryRepository.findById(id).orElseThrow(
			() -> new EntityNotFoundException("Country with ID " + id + " not found"));
		}
}
