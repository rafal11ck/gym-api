package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "membership")
public final class Membership {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	@ManyToOne
	Payment payment;

	Date validUntil;

	@ManyToOne
	MembershipType membershipType;
}
