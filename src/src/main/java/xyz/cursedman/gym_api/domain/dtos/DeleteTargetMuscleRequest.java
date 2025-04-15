package xyz.cursedman.gym_api.domain.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public final class DeleteTargetMuscleRequest {
	@NotBlank(message = "UUID of target muscle to delete has to be provided")
	UUID uuid;
}
