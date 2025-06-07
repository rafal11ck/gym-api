package xyz.cursedman.gym_api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cursedman.gym_api.domain.dtos.user.UserDto;
import xyz.cursedman.gym_api.services.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/whoami")
public class whoAmIController {

	private final UserService userService;

	@GetMapping
	ResponseEntity<UserDto> whoAmI() {
		return
			ResponseEntity.ok().body(userService.getCurrentUser());
	}
}
