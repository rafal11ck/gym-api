package xyz.cursedman.gym_api.domain.dtos.workoutSession;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkoutSessionAttendantRequest {
	@NotNull
	private UUID userUuid;
}
