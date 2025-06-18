package xyz.cursedman.gym_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeDto;
import xyz.cursedman.gym_api.domain.dtos.membershipType.MembershipTypeRequest;
import xyz.cursedman.gym_api.domain.entities.MembershipType;

import java.util.UUID;

public interface MembershipTypeService {
	Page<MembershipTypeDto> listMembershipTypes(Pageable pageable);

	MembershipTypeDto getMembershipType(UUID id);

	MembershipTypeDto createMembershipType(MembershipTypeRequest request);

	MembershipTypeDto patchMembershipType(
		UUID membershipTypeId,
		MembershipTypeRequest request
	);

	// used by mapper
	MembershipType getMembershipTypeEntity(UUID id);
}
