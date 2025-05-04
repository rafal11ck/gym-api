package xyz.cursedman.gym_api.domain.dtos.hallType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HallTypeDto {
	private UUID uuid;
	private String name;
}
