package xyz.cursedman.gym_api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.services.StripeService;

import java.net.URI;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class StripeController {
	private final StripeService stripeService;

	@GetMapping("/checkout/{productName}")
	public ResponseEntity<Void> checkoutMembership(@Valid @PathVariable String productName) {
		Optional<URI> checkoutUri = stripeService.getProductCheckoutUri(productName);
		return checkoutUri.<ResponseEntity<Void>>map(
			uri -> ResponseEntity.status(303).location(uri).build()
		).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/checkout-webhook")
	public ResponseEntity<Void> getCheckoutDataWebhook(
		@RequestBody String payload,
		@RequestHeader("Stripe-Signature") String sigHeader)
	{
		try {
			stripeService.handleWebhook(payload, sigHeader);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	// debug
	@GetMapping("/success")
	public ResponseEntity<String> success() {
		return ResponseEntity.ok("success");
	}

	// debug
	@GetMapping("/failure")
	public ResponseEntity<String> failure() {
		return ResponseEntity.ok("failure");
	}
}
