package xyz.cursedman.gym_api.domain.dtos.membershipType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Currency;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MembershipTypeRequest {
	@NotNull
	private String type;

	@NotNull
	private BigDecimal price;

	@NotNull
	private String currencyCode;
}
