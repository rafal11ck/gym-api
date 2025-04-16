package xyz.cursedman.gym_api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cursedman.gym_api.domain.dtos.country.CountryDto;
import xyz.cursedman.gym_api.mappers.CountryMapper;
import xyz.cursedman.gym_api.services.CountryService;

import java.util.List;

@RestController
@RequestMapping(path = "/countries")
@RequiredArgsConstructor
public class CountryController {
	private final CountryService countryService;
	private final CountryMapper countryMapper;

	@GetMapping
	public ResponseEntity<List<CountryDto>> getCountry() {
		List<CountryDto> countries = countryService.listCountries()
			.stream().map(countryMapper::toDto).toList();

		return ResponseEntity.ok(countries);
	}
}
