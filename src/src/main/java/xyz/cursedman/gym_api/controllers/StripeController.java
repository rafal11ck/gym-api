package xyz.cursedman.gym_api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.services.impl.StripeProvider;

@RestController
@RequestMapping(path = "/stripe")
@AllArgsConstructor
public class StripeController {

	private final StripeProvider stripeProvider;

	@PostMapping("/webhook")
	public void handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
		stripeProvider.handleWebhook(payload, sigHeader);
	}
}
