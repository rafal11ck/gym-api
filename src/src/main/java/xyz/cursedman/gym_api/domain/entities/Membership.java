package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "membership")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class Membership {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	ZonedDateTime validFrom;

	ZonedDateTime validUntil;

	@ManyToOne
	@JoinColumn(nullable = false)
	MembershipType membershipType;
}
