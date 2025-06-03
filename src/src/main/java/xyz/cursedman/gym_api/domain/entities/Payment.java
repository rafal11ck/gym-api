package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

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

	PaymentType paymentType;

	@Enumerated(EnumType.STRING)
	PaymentStatusEnum status;

	UUID externalRefId;

	@Enumerated(EnumType.STRING)
	PaymentExternalRefType externalRefType;
}
