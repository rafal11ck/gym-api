package xyz.cursedman.gym_api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class StripeController {
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
