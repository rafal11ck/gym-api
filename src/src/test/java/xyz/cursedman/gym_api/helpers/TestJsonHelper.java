package xyz.cursedman.gym_api.helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

public class TestJsonHelper {
	static ObjectMapper objectMapper = new ObjectMapper()
		// prevent making timestamps from dates
		.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
		.setDateFormat(new StdDateFormat().withColonInTimeZone(true));

	public static String stringify(Object object) throws Exception {
		return objectMapper.writeValueAsString(object);
	}

	public static ResultMatcher contentEqualsJsonArrayOf(Object... objects) throws Exception {
		String json = objectMapper.writeValueAsString(objects);
		return MockMvcResultMatchers.content().json(json);
	}

	public static ResultMatcher contentEqualsJsonOf(Object object, String... fieldsToIgnore) throws Exception {
		Map<String, Object> jsonMap = objectMapper.convertValue(object, new TypeReference<>() {});

		for (String field : fieldsToIgnore) {
			jsonMap.remove(field);
		}

		String json = objectMapper.writeValueAsString(jsonMap);

		return MockMvcResultMatchers.content().json(json);
	}
}
