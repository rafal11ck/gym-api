package xyz.cursedman.gym_api.startup;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xyz.cursedman.gym_api.domain.entities.UserRole;
import xyz.cursedman.gym_api.domain.entities.UserRoleEnum;
import xyz.cursedman.gym_api.repositories.UserRoleRepository;

@Component
@AllArgsConstructor
public class RoleLoader implements CommandLineRunner {

	UserRoleRepository userRoleRepository;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Loading roles");
		for (UserRoleEnum role : UserRoleEnum.values()) {
			// check if role doesn't exist already
			if (userRoleRepository.findByRoleNameEqualsIgnoreCase(role.name()).isEmpty()) {
				// if does not exist provision it
				UserRole userRole = new UserRole();
				userRole.setRoleName(role.name());
				userRoleRepository.save(userRole);
			}

		}
	}
}
