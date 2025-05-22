package xyz.cursedman.gym_api.services;

import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeDto;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeRequest;
import xyz.cursedman.gym_api.domain.entities.MembershipType;

import java.util.List;
import java.util.UUID;

public interface MembershipTypeService {
	List<MembershipTypeDto> listMembershipTypes();

	MembershipTypeDto getMembershipType(UUID id);

	MembershipTypeDto createMembershipType(MembershipTypeRequest request) throws Exception;

	MembershipTypeDto patchMembershipType(
		UUID membershipTypeId,
		MembershipTypeRequest request
	);

	// used by mapper
	MembershipType getMembershipTypeByUuid(UUID id) throws EntityNotFoundException;
}
