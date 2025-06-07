package xyz.cursedman.gym_api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.domain.dtos.membership.MembershipDto;
import xyz.cursedman.gym_api.domain.dtos.membership.MembershipRequest;
import xyz.cursedman.gym_api.domain.dtos.payment.PaymentDto;
import xyz.cursedman.gym_api.services.MembershipService;
import xyz.cursedman.gym_api.services.impl.MembershipPaymentFacade;

import java.net.URI;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping(path = "/memberships")
@AllArgsConstructor
public class MembershipController {

	private final MembershipService membershipService;

	private final MembershipPaymentFacade membershipPaymentFacade;

	@GetMapping
	public ResponseEntity<Page<MembershipDto>> listMemberships(Pageable pageable) {
		return ResponseEntity.ok(membershipService.listMemberships(pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<MembershipDto> getMembership(@Valid @PathVariable UUID id) {
		return ResponseEntity.ok(membershipService.getMembership(id));
	}

	@GetMapping("/{id}/payments")
	public ResponseEntity<Collection<PaymentDto>> getMembershipPayments(@Valid @PathVariable UUID id) {
		return ResponseEntity.ok(membershipPaymentFacade.listPaymentsFor(id));
	}

	@PostMapping("/{id}/payments")
	public ResponseEntity<URI> getPaymentURI(@Valid @PathVariable UUID id) {
		return ResponseEntity.status(HttpStatus.CREATED).body(membershipPaymentFacade.getPaymentURIFor(id));
		//return ResponseEntity.status(HttpStatus.SEE_OTHER).location(membershipPaymentFacade.getPaymentURIFor(id)).build();
	}

	@PostMapping
	public ResponseEntity<MembershipDto> createMembership(@Valid @RequestBody MembershipRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(membershipService.createMembership(request));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<MembershipDto> patchMembership(
		@Valid @PathVariable UUID id,
		@RequestBody MembershipRequest request
	) {
		return ResponseEntity.ok(membershipService.patchMembership(id, request));
	}
}
