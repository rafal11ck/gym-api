package xyz.cursedman.gym_api.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.cursedman.gym_api.domain.entities.Country;
import xyz.cursedman.gym_api.helpers.TestJsonHelper;
import xyz.cursedman.gym_api.helpers.TestCountryDataHelper;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CountryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TestCountryDataHelper testCountryDataHelper;

	@Test
	void checkIfGetRequestReturnsHttp200AndSavedRecord() throws Exception {
		Country testCountry = testCountryDataHelper.saveAndGetTestCountry();

		mockMvc.perform(
			MockMvcRequestBuilders.get("/countries")
		).andExpect(MockMvcResultMatchers.status().isOk())
		 .andExpect(TestJsonHelper.contentEqualsJsonArrayOf(testCountry));
	}
}
