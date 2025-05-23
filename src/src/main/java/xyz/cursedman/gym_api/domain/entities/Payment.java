package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "payment")
@Data
public final class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	@ManyToOne
	Card card;

	@ManyToOne
	PaymentStatus status;

	@ManyToOne
	Membership membership;

//     TODO payment things
}
