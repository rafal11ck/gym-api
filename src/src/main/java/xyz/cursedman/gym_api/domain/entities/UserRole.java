package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "user_role")
@Data
public final class UserRole {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	@Column(nullable = false, unique = true)
	String role;
}
