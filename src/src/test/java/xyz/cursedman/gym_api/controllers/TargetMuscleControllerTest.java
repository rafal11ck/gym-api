package xyz.cursedman.gym_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.cursedman.gym_api.domain.dtos.targetMuscle.CreateTargetMuscleRequest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@AutoConfigureMockMvc
class TargetMuscleControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper = new ObjectMapper();


	@Test
	void testExercisesGet() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/target-muscles"))
			.andDo(print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	@Order(1)
	void createTargetMuscle() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/target-muscles")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					objectMapper.writeValueAsString(CreateTargetMuscleRequest.builder()
						.name("ass")
						.build())
				))
			.andDo(print())
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

		mockMvc.perform(MockMvcRequestBuilders.get("/target-muscles")).andDo(print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("ass"));
	}

	@Test
	@Order(2)
	void deleteTargetMuscle() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/target-muscles")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					objectMapper.writeValueAsString(CreateTargetMuscleRequest.builder()
						.name("ass")
						.build())
				))
			.andDo(print())
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		
		String getResponse = mockMvc.perform(MockMvcRequestBuilders.get(
			"/target-muscles")).andDo(print()).andReturn().getResponse().getContentAsString();

		System.out.println(getResponse);
//
//		mockMvc.perform(
//			MockMvcRequestBuilders.delete("/target-muscles/" + JsonPath.read(list, "$[0].name")));
	}

}
