package xyz.cursedman.gym_api.mappers;

import org.mapstruct.*;
import xyz.cursedman.gym_api.domain.dtos.membership.MembershipDto;
import xyz.cursedman.gym_api.domain.dtos.membership.MembershipRequest;
import xyz.cursedman.gym_api.domain.entities.Membership;
import xyz.cursedman.gym_api.services.MembershipTypeService;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MembershipTypeService.class)
public interface MembershipMapper extends GenericMapper<Membership, MembershipDto, MembershipRequest> {
	@Override
	@Mapping(target = "membershipTypeUuid", source = "membershipType.uuid")
	MembershipRequest toRequestFromEntity(Membership entity);

	@Override
	@Mapping(target = "membershipType", source = "membershipTypeUuid")
	Membership toEntityFromRequest(MembershipRequest request);

	@Override
	@Mapping(target = "membershipType", source = "membershipTypeUuid")
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromRequest(MembershipRequest request, @MappingTarget Membership entity);
}
