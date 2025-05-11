package xyz.cursedman.gym_api.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "stripe")
@Data
public class StripeProperties {
	private String apiKey;

	private String webhookSecret;

	private String successRedirectUrl = "http://localhost:8080/success";

	private String cancelRedirectUrl = "http://localhost:8080/cancel";

	private final Logger logger = LoggerFactory.getLogger(StripeProperties.class);

	@PostConstruct
	public void validate() {
		if (apiKey == null) {
			logger.warn(
				"Missing STRIPE_API_KEY (stripe.api-key). "
				+ "Set in application.yml or as environment variable (preferably). "
				+ "Payments will not work unless key is set!"
			);
		}

		if (webhookSecret == null) {
			logger.warn(
				"Missing STRIPE_WEBHOOK_SECRET (stripe.webhook-secret). "
				+ "Set in application.yml or as environment variable (preferably). "
				+ "Payments will not work unless secret is set!"
			);
		}
	}

	public boolean isStripeConfigurationValid() {
		return apiKey != null && webhookSecret != null;
	}
}
