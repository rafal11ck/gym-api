package xyz.cursedman.gym_api.mappers;

import org.mapstruct.*;
import xyz.cursedman.gym_api.domain.dtos.user.UserDto;
import xyz.cursedman.gym_api.domain.dtos.user.UserRequest;
import xyz.cursedman.gym_api.domain.entities.User;
import xyz.cursedman.gym_api.domain.entities.UserRole;
import xyz.cursedman.gym_api.services.CardService;
import xyz.cursedman.gym_api.services.MembershipService;
import xyz.cursedman.gym_api.services.UserRoleService;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	uses = {MembershipService.class, CardService.class, UserRoleService.class}
)
public interface UserMapper extends EntityRequestMapper<User, UserDto, UserRequest> {
	@Override
	@Mapping(target = "roles", source = "roles")
	@Mapping(target = "cardUuid", source = "card.uuid")
	@Mapping(target = "membershipUuid", source = "membership.uuid")
	UserRequest toRequestFromEntity(User entity);

	@Override
//	@Mapping(target = "role", source = "roleUuid")
//	@Mapping(target = "card", source = "cardUuid")
//	@Mapping(target = "membership", source = "membershipUuid")
	User toEntityFromRequest(UserRequest request);

	@Override
//	@Mapping(target = "roles", source = "roleUuid")
	@Mapping(target = "card", source = "cardUuid")
	@Mapping(target = "membership", source = "membershipUuid")
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromRequest(UserRequest request, @MappingTarget User entity);


	default Set<String> mapRolesToStrings(Set<UserRole> roles) {
		if (roles == null) return null;
		return roles.stream().map(UserRole::getRoleName).collect(Collectors.toSet());
	}
}
