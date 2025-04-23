package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "payment_status")
@Data
public final class PaymentStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	@Column(nullable = false, unique = true)
	String status;
}
