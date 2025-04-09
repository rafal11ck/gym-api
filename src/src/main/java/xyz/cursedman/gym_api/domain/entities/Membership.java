package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "membership")
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    @ManyToOne
    Payment payment;

    @ManyToOne
    MembershipType membershipType;
}
