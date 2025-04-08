package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "TargetMuscle")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data // might break mappers
public class TargetMuscle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    @Column(nullable = false, unique = true)
    String muscleName;
}
