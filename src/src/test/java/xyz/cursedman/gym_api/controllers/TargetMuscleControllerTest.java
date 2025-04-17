package xyz.cursedman.gym_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.cursedman.gym_api.domain.dtos.targetMuscle.CreateTargetMuscleRequest;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TargetMuscleControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper = new ObjectMapper();


	@Test
	void testCrud() throws Exception {

	// add
	MvcResult addResult = mockMvc.perform(MockMvcRequestBuilders.post("/target-muscles")
			.contentType(MediaType.APPLICATION_JSON)
			.content(
				objectMapper.writeValueAsString(CreateTargetMuscleRequest.builder()
					.name("ass")
					.build())
			))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andReturn();

	String uuid = JsonPath.read(addResult.getResponse().getContentAsString(), "$.uuid");

	// get
	mockMvc.perform(MockMvcRequestBuilders.get("/target-muscles"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("ass"));


	// delete
	mockMvc.perform(MockMvcRequestBuilders.delete("/target-muscles/{uuid}", uuid))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

}
