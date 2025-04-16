package xyz.cursedman.gym_api.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.cursedman.gym_api.domain.entities.Country;
import xyz.cursedman.gym_api.repositories.CountryRepository;

@Component
public class TestCountryDataHelper {

	@Autowired
	private CountryRepository countryRepository;

	public Country saveAndGetTestCountry() {
		Country country = new Country(null, "Poland");
		return countryRepository.saveAndFlush(country);
	}
}
