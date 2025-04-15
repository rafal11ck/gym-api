package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "hall_type")
public final class HallType {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	@Column(unique = true, nullable = false)
	String name;
}
