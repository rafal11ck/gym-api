package xyz.cursedman.gym_api.domain.dtos.hall;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.cursedman.gym_api.domain.entities.HallType;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HallDto {
	private UUID uuid;

	private HallType hallType;

	private String hallName;

	private String hallDescription;
}
