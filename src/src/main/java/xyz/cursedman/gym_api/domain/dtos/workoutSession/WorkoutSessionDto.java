package xyz.cursedman.gym_api.domain.dtos.workoutSession;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.cursedman.gym_api.domain.entities.Hall;
import xyz.cursedman.gym_api.domain.entities.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionDto {
	private UUID uuid;

	private User coach;

	private Hall hall;

	private LocalDate date;

	private String title;

	private String description;

	private Set<User> attendants;

	private List<WorkoutSessionExerciseDto> exercises;
}
