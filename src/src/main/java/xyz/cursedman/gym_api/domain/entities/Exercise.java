package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Exercise {
    @Id
    UUID uuid;

    String name;

    @ManyToMany
    @JoinTable(
            name = "ExerciseTargetMuscle",
            joinColumns = @JoinColumn(name = "exercise_uuid"),
            inverseJoinColumns = @JoinColumn(name = "target_muscle_uuid")
    )
    Set<TargetMuscle> targetMuscles;

}
