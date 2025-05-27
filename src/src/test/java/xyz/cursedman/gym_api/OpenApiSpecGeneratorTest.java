package xyz.cursedman.gym_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OpenApiSpecGeneratorTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void generateOpenApiSpecJson() throws Exception {
		// Fetch the OpenAPI JSON
		String json = mockMvc.perform(MockMvcRequestBuilders.get(
				"/api-docs"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn()
			.getResponse()
			.getContentAsString();

		Files.write(Paths.get("target/openapi.json"), json.getBytes());
	}

	@Test
	void generateOpenApiSpecYaml() throws Exception {
		// Fetch the OpenAPI Yaml
		String json = mockMvc.perform(MockMvcRequestBuilders.get("/api-docs.yaml"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn()
			.getResponse()
			.getContentAsString();

		Files.write(Paths.get("target/openapi.yaml"), json.getBytes());
	}
}
