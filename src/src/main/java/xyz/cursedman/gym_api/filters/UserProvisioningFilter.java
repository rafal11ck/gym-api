package xyz.cursedman.gym_api.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.cursedman.gym_api.domain.dtos.user.UserDto;
import xyz.cursedman.gym_api.domain.dtos.user.UserRequest;
import xyz.cursedman.gym_api.services.UserRoleService;
import xyz.cursedman.gym_api.services.UserService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserProvisioningFilter extends OncePerRequestFilter {

	private final UserService userService;
	private final UserRoleService userRoleService;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null
			&& authentication.isAuthenticated()
			&& authentication.getPrincipal() instanceof Jwt jwt) {

			UUID keycloakId = UUID.fromString(jwt.getSubject());
			UserRequest userRequest = new UserRequest();
			userRequest.setFirstName(jwt.getClaim("name"));
			userRequest.setLastName(jwt.getClaim("family_name"));
			userRequest.setEmail(jwt.getClaim("email"));

			Optional<UserDto> user = userService.getUserByExternalAuthorizationId
				("OIDC", keycloakId.toString());

// TODO here
			if (!userService.existsUser(keycloakId)) {
				System.out.println("Provisioning external user: " + keycloakId);
				userService.createUser(userRequest);
			} else {
				// update existing user
				userService.patchUser(keycloakId, userRequest);
			}
		}

		filterChain.doFilter(request, response);
	}
}
