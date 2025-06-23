package xyz.cursedman.gym_api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import xyz.cursedman.gym_api.domain.dtos.user.UserDto;
import xyz.cursedman.gym_api.domain.dtos.user.UserRequest;
import xyz.cursedman.gym_api.domain.entities.UserRoleEnum;
import xyz.cursedman.gym_api.services.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GymJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
	private final UserService userService;
	private final String externalAuthProvider = "OIDC";
	private final String userRolesClaimName = "roles";


	private UserRequest jwtToUserRequest(Jwt jwt) {
		UserRequest userRequest = new UserRequest();
		userRequest.setFirstName(jwt.getClaim("name"));
		userRequest.setLastName(jwt.getClaim("family_name"));
		userRequest.setEmail(jwt.getClaim("email"));
		userRequest.setUsername(
			jwt.getClaim("preferred_username")
		);

		Set<String> userRoleNames = new HashSet<>(
			// read roles claim
			Optional.ofNullable(
					jwt.getClaimAsStringList(userRolesClaimName)
				)
				// if roles claim doesn't set no roles
				.orElse(List.of(UserRoleEnum.CLIENT.name()))
		);
		userRequest.setRoles(userRoleNames);


		return userRequest;
	}

	@Override
	public AbstractAuthenticationToken convert(Jwt jwt) {
		UUID keycloakId = UUID.fromString(jwt.getSubject());

		UserDto user = userService.createOrUpdateLinkedUser(
			jwtToUserRequest(jwt), externalAuthProvider, keycloakId.toString()
		);

		Collection<String> roles = userService.getUserRoles(user.getUuid());
		Collection<GrantedAuthority> authorities
			= roles.stream()
			.map(String::toUpperCase)
			.map(role -> new SimpleGrantedAuthority(("ROLE_" + role)))
			.collect(Collectors.toList());

		System.out.println(roles);

		return new JwtAuthenticationToken(jwt, authorities, user.getUuid().toString());
	}
}
