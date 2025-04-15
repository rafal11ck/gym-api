package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "card")
public final class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;
}
