package xyz.cursedman.gym_api.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.domain.dtos.payment.PaymentDto;
import xyz.cursedman.gym_api.domain.dtos.payment.PaymentRequest;
import xyz.cursedman.gym_api.services.PaymentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/payments")
@RequiredArgsConstructor
public class PaymentController {
	private final PaymentService paymentService;

	@GetMapping
	public ResponseEntity<List<PaymentDto>> listPayments() {
		return ResponseEntity.ok(paymentService.listPayments());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<PaymentDto> getPayment(@Valid @PathVariable UUID id) {
		PaymentDto paymentDto = paymentService.getPayment(id);
		return ResponseEntity.ok(paymentDto);
	}

	@PostMapping
	public ResponseEntity<PaymentDto> createPayment(@Valid @RequestBody PaymentRequest request) {
		PaymentDto createdPayment = paymentService.createPayment(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<PaymentDto> updatePayment(
		@Valid @PathVariable UUID id,
		@RequestBody PaymentRequest request
	) {
		return ResponseEntity.ok(paymentService.patchPayment(id, request));
	}
}
