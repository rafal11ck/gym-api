package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Table
@Entity
@Data
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID paymentId;

	@NotNull
	Currency currency;
	@NotNull
	BigDecimal price;

	@Enumerated(EnumType.STRING)
	PaymentType paymentType;

	@Enumerated(EnumType.STRING)
	PaymentStatusEnum status;

	UUID externalRefId;

	@Enumerated(EnumType.STRING)
	PaymentExternalRefType externalRefType;
}
