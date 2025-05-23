package xyz.cursedman.gym_api.mappers;

import org.mapstruct.*;
import xyz.cursedman.gym_api.domain.dtos.user.UserDto;
import xyz.cursedman.gym_api.domain.dtos.user.UserRequest;
import xyz.cursedman.gym_api.domain.entities.User;
import xyz.cursedman.gym_api.services.CardService;
import xyz.cursedman.gym_api.services.MembershipService;
import xyz.cursedman.gym_api.services.UserRoleService;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	uses = {MembershipService.class, CardService.class, UserRoleService.class}
)
public interface UserMapper extends EntityRequestMapper<User, UserDto, UserRequest> {
	@Override
	@Mapping(target = "roleUuid", source = "role.uuid")
	@Mapping(target = "cardUuid", source = "card.uuid")
	@Mapping(target = "membershipUuid", source = "membership.uuid")
	UserRequest toRequestFromEntity(User entity);

	@Override
//	@Mapping(target = "role", source = "roleUuid")
//	@Mapping(target = "card", source = "cardUuid")
//	@Mapping(target = "membership", source = "membershipUuid")
	User toEntityFromRequest(UserRequest request);

	@Override
	@Mapping(target = "role", source = "roleUuid")
	@Mapping(target = "card", source = "cardUuid")
	@Mapping(target = "membership", source = "membershipUuid")
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromRequest(UserRequest request, @MappingTarget User entity);
}
