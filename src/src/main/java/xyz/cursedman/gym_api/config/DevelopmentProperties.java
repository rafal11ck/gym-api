package xyz.cursedman.gym_api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "development")
@Data
public class DevelopmentProperties {

	private Stripe stripe = new Stripe();

	@Data
	public static class Stripe {
		private boolean testingEnabled = false;
	}
}
