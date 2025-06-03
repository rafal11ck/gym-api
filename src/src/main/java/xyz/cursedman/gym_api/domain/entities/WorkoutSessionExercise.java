package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "workout_session_exercise")
public class WorkoutSessionExercise {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	@ManyToOne
	WorkoutSession workoutSession;

	@ManyToOne
	Exercise exercise;

	Integer reps;

	Float weight;

	Integer exerciseOrder;
}
