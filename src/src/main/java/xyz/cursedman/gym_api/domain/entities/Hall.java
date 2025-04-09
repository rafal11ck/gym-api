package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Table(name = "hall")
@Entity
@Data
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    @OneToOne
    HallType hallType;
}
