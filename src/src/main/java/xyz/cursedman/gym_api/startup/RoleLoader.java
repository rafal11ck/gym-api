package xyz.cursedman.gym_api.startup;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xyz.cursedman.gym_api.domain.entities.UserRoleEnum;
import xyz.cursedman.gym_api.services.UserRoleService;

@Component
@AllArgsConstructor
public class RoleLoader implements CommandLineRunner {

	UserRoleService userRoleService;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Loading roles");

		for (UserRoleEnum role : UserRoleEnum.values()) {
			userRoleService.createUserRole(role.name());
		}
	}
}
