package xyz.cursedman.gym_api.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.domain.dtos.membership.MembershipDto;
import xyz.cursedman.gym_api.domain.dtos.membership.MembershipRequest;
import xyz.cursedman.gym_api.services.MembershipService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/memberships")
@AllArgsConstructor
public class MembershipController {
	private final MembershipService membershipService;

	@GetMapping
	public ResponseEntity<List<MembershipDto>> listMemberships() {
		return ResponseEntity.ok(membershipService.listMemberships());
	}

	@GetMapping("/{id}")
	public ResponseEntity<MembershipDto> getMembership(@Valid @PathVariable UUID id) {
		return ResponseEntity.ok(membershipService.getMembership(id));
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
