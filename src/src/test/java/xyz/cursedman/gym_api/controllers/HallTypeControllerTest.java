package xyz.cursedman.gym_api.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class HallTypeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private final String endpointUri = "/hall-types";

	@Test
	void checkIfGetReturnsHttp200AndAllRecords() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.get(endpointUri)
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.greaterThan(0)));
	}
}
