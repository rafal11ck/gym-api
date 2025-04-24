package xyz.cursedman.gym_api.domain.dtos.card;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardRequest {
	@NotNull
	private UUID countryUuid;

	@NotNull
	private String cardNumber;

	@NotNull
	private String cvv;

	@NotNull
	private Date dateOfBirth;

	@NotNull
	private String nameOnCard;

	@NotNull
	private String postalCode;
}
