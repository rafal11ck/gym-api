package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public final class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	@ManyToMany(fetch = FetchType.EAGER)
	Set<UserRole> roles = new HashSet<>();

	@OneToOne
	Membership membership;

	@NotNull
	String firstName;

	@NotNull
	String lastName;

	Date dateOfBirth;

	@Column(unique = true)
	String username;

	String email;

	String phoneNumber;

	String imageUrl;
}
