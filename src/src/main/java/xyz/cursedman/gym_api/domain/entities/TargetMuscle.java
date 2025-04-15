package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

/**
 * @brief Represents muscle targeted by Exercise
 * @see Exercise
 */
@Data
@Entity
@Table(name = "target_muscle")
public final class TargetMuscle {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	@Column(unique = true, nullable = false)
	String name;
}
