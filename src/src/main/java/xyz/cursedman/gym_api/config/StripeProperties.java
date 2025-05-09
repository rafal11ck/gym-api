package xyz.cursedman.gym_api.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "stripe")
@Data
@Validated
public class StripeProperties {
	@NotBlank(
		message = "Missing STRIPE_API_KEY (stripe.api-key). Set in application.yml or as environment variable (preferably)."
	)
	private String apiKey;

	@NotBlank(
		message = "Missing STRIPE_WEBHOOK_SECRET (stripe.webhook-secret). Set in application.yml or as environment variable (preferably)."
	)
	private String webhookSecret;

	private String productsFilePath = "stripe-products.json";

	private String successRedirectUrl = "http://localhost:8080/success";

	private String failureRedirectUrl = "http://localhost:8080/failure";
}
