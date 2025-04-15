package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "card")
public final class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	@ManyToOne
	@JoinColumn(nullable = false)
	Country country;

	@NotNull
	@Column(unique = true, nullable = false)
	String cardNumber;

	@NotNull
	@Column(nullable = false)
	String cvv;

	@NotNull
	@Column(nullable = false)
	Date dateOfBirth;

	@NotNull
	@Column(nullable = false)
	String nameOnCard;

	@NotNull
	@Column(nullable = false)
	String postalCode;
}
