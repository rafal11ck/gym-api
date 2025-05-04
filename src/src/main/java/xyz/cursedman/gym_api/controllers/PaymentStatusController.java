package xyz.cursedman.gym_api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cursedman.gym_api.domain.dtos.paymentStatus.PaymentStatusDto;
import xyz.cursedman.gym_api.services.PaymentStatusService;

import java.util.List;

@RestController
@RequestMapping(path = "/payment-statuses")
@RequiredArgsConstructor
public class PaymentStatusController {
	private final PaymentStatusService paymentStatusService;

	@GetMapping
	public ResponseEntity<List<PaymentStatusDto>> listPaymentStatuses() {
		return ResponseEntity.ok(paymentStatusService.listPaymentStatuses());
	}
}
