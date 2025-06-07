package xyz.cursedman.gym_api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cursedman.gym_api.domain.dtos.progressStatistics.ChartDto;
import xyz.cursedman.gym_api.domain.dtos.progressStatistics.ProgressOverviewDto;
import xyz.cursedman.gym_api.domain.dtos.user.UserDto;
import xyz.cursedman.gym_api.domain.dtos.workoutSession.WorkoutSessionDto;
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

	@GetMapping("{id}/progress/overview")
	public ResponseEntity<ProgressOverviewDto> getUserProgressOverview(@Valid @PathVariable UUID id) {
		return ResponseEntity.ok(progressStatisticsService.getUserProgressOverview(id));
	}

	@GetMapping("/{id}/progress/charts/total-effort")
	public ResponseEntity<ChartDto> getUserTotalChartData(
		@Valid @PathVariable UUID id,
		@RequestParam(defaultValue = "12") Integer numberOfWeeks
	) {
		return ResponseEntity.ok(progressStatisticsService.getUserTotalChartData(id, numberOfWeeks));
	}


	@GetMapping("{id}/progress/charts/exercise-heaviest-weight")
	public ResponseEntity<ChartDto> getUserExerciseChartData(
		@Valid @PathVariable UUID id,
		@RequestParam(defaultValue = "12") Integer numberOfWeeks
	) {
		return ResponseEntity.ok(progressStatisticsService.getUserExerciseChartData(id, numberOfWeeks));
	}


	@GetMapping("{id}/workout-sessions")
	public ResponseEntity<List<WorkoutSessionDto>> listUserWorkoutSessions(@Valid @PathVariable UUID id) {
		return ResponseEntity.ok(workoutSessionService.listUserWorkoutSessions(id));
	}

	@GetMapping("{id}/last-workout-session")
	public ResponseEntity<WorkoutSessionDto> getUserLastWorkoutSession(@Valid @PathVariable UUID id) {
		return ResponseEntity.ok(workoutSessionService.getUserLastWorkoutSession(id));
	}


}
