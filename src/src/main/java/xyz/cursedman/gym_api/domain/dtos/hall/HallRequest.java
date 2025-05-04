package xyz.cursedman.gym_api.domain.dtos.hall;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HallRequest {
	@NotNull
	private UUID hallTypeUuid;

	@NotNull
	private String hallName;

	@NotNull
	private String hallDescription;
}
