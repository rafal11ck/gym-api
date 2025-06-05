package xyz.cursedman.gym_api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import xyz.cursedman.gym_api.security.GymJwtAuthenticationConverter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final GymJwtAuthenticationConverter jwtAuthenticationConverter;


	@Bean
	public SecurityFilterChain securityFilterChain(
		HttpSecurity http)
		throws Exception {

		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests((authorize) -> authorize
				// allow all to docs

				.requestMatchers("/swagger", "/swagger-ui/*", "/api-docs.yaml", "/api-docs/**")
				.permitAll()
				.requestMatchers("/workout-sessions").hasAnyRole("CLIENT","COACH")
					.requestMatchers("/target-muscles").hasAnyRole("CLIENT","COACH")
					.requestMatchers("/memberships").hasRole("CLIENT")
					.requestMatchers("/maintenance-tasks").hasAnyRole("EMPLOYEE","MANAGER")
					.requestMatchers("/halls").hasAnyRole("EMPLOYEE","MANAGER")
					.requestMatchers("/exercises").hasRole("CLIENT")
					.requestMatchers("/users").hasAnyRole("CLIENT","COACH","MANAGER")
					.requestMatchers("/hall-types").hasAnyRole("EMPLOYEE","MANAGER")
				// everything else authenticated
				//.anyRequest()
				//.authenticated()
			)
			.sessionManagement((session) ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.oauth2ResourceServer(
				(oauth2) -> oauth2
					.jwt(
						jwt -> jwt
							.jwtAuthenticationConverter(jwtAuthenticationConverter)
					)
			);
//			.addFilterBefore(userProvisioningFilter, BearerTokenAuthenticationFilter.class);
		return http.build();
	}
}
