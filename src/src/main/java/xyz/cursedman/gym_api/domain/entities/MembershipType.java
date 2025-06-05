package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class MembershipType {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	@Column(nullable = false)
	String type;

	@Column(nullable = false)
	Currency currency;

	@Column(nullable = false)
	BigDecimal price;
}
