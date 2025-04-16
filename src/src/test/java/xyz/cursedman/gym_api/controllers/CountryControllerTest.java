package xyz.cursedman.gym_api.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.cursedman.gym_api.domain.entities.Country;
import xyz.cursedman.gym_api.repositories.CountryRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CountryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CountryRepository countryRepository;

	private void createAndSaveCountry(String countryName) {
		Country country = new Country(null, countryName);
		countryRepository.saveAndFlush(country);
	}

	@Test
	void checkIfGetRequestReturnsHttp200AndSavedRecord() throws Exception {
		String countryName = "Poland";
		createAndSaveCountry(countryName);

		mockMvc.perform(
			MockMvcRequestBuilders.get("/countries")
		).andExpect(MockMvcResultMatchers.status().isOk())
		 .andExpect(MockMvcResultMatchers.jsonPath("$[0].countryName").value(countryName));
	}
}
