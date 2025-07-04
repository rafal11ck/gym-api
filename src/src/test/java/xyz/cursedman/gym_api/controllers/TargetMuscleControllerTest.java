package xyz.cursedman.gym_api.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = {"CLIENT", "COACH"})
@ActiveProfiles("test")
class TargetMuscleControllerTest {

	private final String endpointUri = "/target-muscles";
	@Autowired
	private MockMvc mockMvc;

	@Test
	void checkIfGetReturnsHttp200AndAllRecords() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(endpointUri))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.greaterThan(0)));
	}

	// TODO: Remove CRUD

//	@Test
//	void testCrud() throws Exception {
//		// add
//		MvcResult addResult = mockMvc.perform(MockMvcRequestBuilders.post("/target-muscles")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(
//					objectMapper.writeValueAsString(CreateTargetMuscleRequest.builder()
//						.name("ass")
//						.build())
//				))
//			.andExpect(MockMvcResultMatchers.status().isCreated())
//			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//			.andReturn();
//
//		String uuid = JsonPath.read(addResult.getResponse().getContentAsString(), "$.uuid");
//
//		// get
//		mockMvc.perform(MockMvcRequestBuilders.get("/target-muscles"))
//			.andExpect(MockMvcResultMatchers.status().isOk())
//			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//			.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("ass"));
//
//
//		// delete
//		mockMvc.perform(MockMvcRequestBuilders.delete("/target-muscles/{uuid}", uuid))
//			.andExpect(MockMvcResultMatchers.status().isNoContent());
//	}

}
