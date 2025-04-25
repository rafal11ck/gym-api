package xyz.cursedman.gym_api.services;

import jakarta.persistence.EntityNotFoundException;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeDto;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeRequest;

import java.util.List;
import java.util.UUID;

public interface MembershipTypeService {
	List<MembershipTypeDto> listMembershipTypes();

	MembershipTypeDto getMembershipType(UUID id) throws EntityNotFoundException;

	MembershipTypeDto createMembershipType(MembershipTypeRequest request);

	MembershipTypeDto patchMembershipType(
		UUID membershipTypeId,
		MembershipTypeRequest request
	) throws EntityNotFoundException;
}
