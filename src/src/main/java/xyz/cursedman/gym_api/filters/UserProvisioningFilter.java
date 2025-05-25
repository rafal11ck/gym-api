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
import xyz.cursedman.gym_api.domain.dtos.user.UserRequest;
import xyz.cursedman.gym_api.services.UserRoleService;
import xyz.cursedman.gym_api.services.UserService;

import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserProvisioningFilter extends OncePerRequestFilter {


	private final UserService userService;
	private final UserRoleService userRoleService;

	private final String userRolesClaimName = "roles";

	private UserRequest jwtToUserRequest(Jwt jwt) {
		System.out.println(jwt);
		UserRequest userRequest = new UserRequest();
		userRequest.setFirstName(jwt.getClaim("name"));
		userRequest.setLastName(jwt.getClaim("family_name"));
		userRequest.setEmail(jwt.getClaim("email"));
		userRequest.setRoles(new HashSet<String>(jwt.getClaim(userRolesClaimName)));

		System.out.println(jwt.getClaims().toString());
		return userRequest;
	}

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
			String externalAuthorizationProvider = "OIDC";

			userService.createOrUpdateLinkedUser(
				this.jwtToUserRequest(jwt), externalAuthorizationProvider, keycloakId.toString()
			);
		}

		filterChain.doFilter(request, response);
	}
}
