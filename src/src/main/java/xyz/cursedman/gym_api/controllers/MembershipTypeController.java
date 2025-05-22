package xyz.cursedman.gym_api.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeDto;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeRequest;
import xyz.cursedman.gym_api.services.MembershipTypeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/membership-types")
@RequiredArgsConstructor
public class MembershipTypeController {

	private final MembershipTypeService membershipTypeService;

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
		return new ResponseEntity<>(membershipTypeService.createMembershipType(request), HttpStatus.CREATED);
	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<MembershipTypeDto> updateCard(
		@Valid @PathVariable UUID id,
		@RequestBody MembershipTypeRequest request
	) {
		return ResponseEntity.ok(membershipTypeService.patchMembershipType(id, request));
	}
}
