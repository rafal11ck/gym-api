package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

	LocalDateTime validFrom;

	LocalDateTime validUntil;

	@ManyToOne
	MembershipType membershipType;

}
