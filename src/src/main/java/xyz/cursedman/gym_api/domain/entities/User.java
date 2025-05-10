package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public final class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	String customerId;

	@ManyToOne
	UserRole role;

	@ManyToOne(fetch = FetchType.LAZY)
	Card card;

	@OneToOne
	Membership membership;

	@NotNull
	String firstName;

	@NotNull
	String lastName;

	@NotNull
	Date dateOfBirth;

	String email;

	String phoneNumber;

	String imageUrl;
}
