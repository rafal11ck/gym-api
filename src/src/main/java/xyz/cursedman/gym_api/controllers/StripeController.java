package xyz.cursedman.gym_api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import xyz.cursedman.gym_api.config.StripeProperties;
import xyz.cursedman.gym_api.services.StripeService;

@Controller
@RequiredArgsConstructor
public class StripeController {
	private final StripeService stripeService;
	private final StripeProperties stripeProperties;

	// debug
	@GetMapping("/success")
	public ResponseEntity<String> success() {
		return ResponseEntity.ok("success");
	}

	// debug
	@GetMapping("/cancel")
	public ResponseEntity<String> cancel() {
		return ResponseEntity.ok("cancel");
	}

	@PostMapping("/stripe-webhook")
	public ResponseEntity<Void> getCheckoutDataWebhook(
		@RequestBody String payload,
		@RequestHeader("Stripe-Signature") String sigHeader)
	{
		if (!stripeProperties.isStripeConfigurationValid()) {
			return ResponseEntity.notFound().build();
		}

		try {
			stripeService.handleWebhook(payload, sigHeader);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
