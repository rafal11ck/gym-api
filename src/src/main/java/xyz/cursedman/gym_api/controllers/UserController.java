package xyz.cursedman.gym_api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cursedman.gym_api.domain.dtos.chat.ChatDto;
import xyz.cursedman.gym_api.domain.dtos.progressStatistics.ProgressOverviewDto;
import xyz.cursedman.gym_api.domain.dtos.user.UserDto;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionDto;
import xyz.cursedman.gym_api.services.ChatService;
import xyz.cursedman.gym_api.services.ProgressStatisticsService;
import xyz.cursedman.gym_api.services.UserService;
import xyz.cursedman.gym_api.services.WorkoutSessionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	private final ChatService chatService;
	private final WorkoutSessionService workoutSessionService;
	private final ProgressStatisticsService progressStatisticsService;

	@GetMapping
	public ResponseEntity<List<UserDto>> listUsers() {
		return ResponseEntity.ok(userService.listUsers());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<UserDto> getUser(@Valid @PathVariable UUID id) {
		UserDto userDto = userService.getUser(id);
		return ResponseEntity.ok(userDto);
	}

	// stats

	@GetMapping("{id}/progress-statistics/overview")
	public ResponseEntity<ProgressOverviewDto> getUserStatistics(@PathVariable UUID id) {
		return ResponseEntity.ok(progressStatisticsService.getUserProgressOverview(id));
	}

	// chats

	@GetMapping("{id}/chats")
	public ResponseEntity<List<ChatDto>> listChats(@PathVariable UUID id) {
		return ResponseEntity.ok(chatService.listUserChats(id));
	}

	@GetMapping("{id}/workout-sessions")
	public ResponseEntity<List<WorkoutSessionDto>> listExercises(@Valid @PathVariable UUID id) {
		return ResponseEntity.ok(workoutSessionService.listUserWorkoutSessions(id));
	}
}
