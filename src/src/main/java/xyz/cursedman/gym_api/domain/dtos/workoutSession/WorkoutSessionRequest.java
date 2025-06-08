package xyz.cursedman.gym_api.domain.dtos.workoutSession;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionRequest {
	private UUID coachUuid;

	private UUID hallUuid;

	@NotNull
	private LocalDate date;

	@NotNull
	private String title;

	@NotNull
	private String description;
}
