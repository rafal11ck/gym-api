package xyz.cursedman.gym_api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return
			new OpenAPI().components(new Components()
					.addSecuritySchemes("OAuth2",
						new SecurityScheme().type(SecurityScheme.Type.OAUTH2).flows(
							new OAuthFlows()
								.authorizationCode(new OAuthFlow()
									.authorizationUrl("http://localhost:8081/realms/gym/protocol/openid-connect/auth")
									.tokenUrl("http://localhost:8081/realms/gym/protocol/openid-connect/token")
									.refreshUrl("http://localhost:8081/realms/gym/protocol/openid-connect/token")
									.scopes(new Scopes())
								)
						)
					)
				)
				.addSecurityItem(new SecurityRequirement().addList("OAuth2"));
	}
}
