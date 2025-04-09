package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID userId;

    @Enumerated(EnumType.STRING)
    UserRole role = UserRole.CLIENT;


    @ManyToOne(fetch = FetchType.LAZY)
    Card card;

    @OneToOne()
    Membership memberShip;

    @OneToOne()
    UserDetails userDetails;
}
