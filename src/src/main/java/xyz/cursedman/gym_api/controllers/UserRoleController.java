package xyz.cursedman.gym_api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cursedman.gym_api.domain.dtos.userRole.UserRoleDto;
import xyz.cursedman.gym_api.services.UserRoleService;

import java.util.List;

@RestController
@RequestMapping(path = "/user-roles")
@AllArgsConstructor
public class UserRoleController {
	private final UserRoleService userRoleService;

	@GetMapping
	public ResponseEntity<List<UserRoleDto>> listUserRoles() {
		return ResponseEntity.ok(userRoleService.listUserRoles());
	}
}
