package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "exercise")
public final class Exercise {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	String name;

	@ManyToMany
	@JoinTable(
		name = "exercise_target_muscle",
		joinColumns = @JoinColumn(name = "exercise_uuid"),
		inverseJoinColumns = @JoinColumn(name = "target_muscle_uuid")
	)
	Set<TargetMuscle> targetMuscles = new HashSet<>();

}
