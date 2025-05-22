package xyz.cursedman.gym_api.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.config.StripeProperties;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeDto;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeRequest;
import xyz.cursedman.gym_api.services.MembershipTypeService;
import xyz.cursedman.gym_api.services.StripeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/membership-types")
@RequiredArgsConstructor
public class MembershipTypeController {

	private final MembershipTypeService membershipTypeService;
	private final StripeProperties stripeProperties;
	private final StripeService stripeService;

	@GetMapping
	public ResponseEntity<List<MembershipTypeDto>> listMembershipTypes() {
		return ResponseEntity.ok(membershipTypeService.listMembershipTypes());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<MembershipTypeDto> getMembershipType(@Valid @PathVariable UUID id) {
		MembershipTypeDto membershipTypeDto = membershipTypeService.getMembershipType(id);
		return ResponseEntity.ok(membershipTypeDto);
	}

	@PostMapping
	public ResponseEntity<MembershipTypeDto> createMembershipType(@Valid @RequestBody MembershipTypeRequest request) {
		try {
			MembershipTypeDto createdMembershipType = membershipTypeService.createMembershipType(request);
			if (stripeProperties.isStripeConfigurationValid()) {
				stripeService.createProductFromMembershipTypeRequest(request);
			}

			return ResponseEntity.status(HttpStatus.CREATED).body(createdMembershipType);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<MembershipTypeDto> updateCard(
		@Valid @PathVariable UUID id,
		@RequestBody MembershipTypeRequest request
	) {
		return ResponseEntity.ok(membershipTypeService.patchMembershipType(id, request));
	}
}
