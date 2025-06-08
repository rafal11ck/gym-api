package xyz.cursedman.gym_api.domain.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name = "workout_session")
@Entity
@Data
public final class WorkoutSession {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	@ManyToOne(fetch = FetchType.LAZY)
	@Nullable
	User coach;

	@ManyToOne(fetch = FetchType.LAZY)
	@Nullable
	Hall hall;

	LocalDate date;

	String title;

	String description;

	@ManyToMany(fetch = FetchType.LAZY)
	Set<User> attendants = new HashSet<>();
}
