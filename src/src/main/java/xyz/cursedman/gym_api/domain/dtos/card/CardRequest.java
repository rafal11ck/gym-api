package xyz.cursedman.gym_api.domain.dtos.card;

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
	private UUID countryUuid;
	private String cardNumber;
	private String cvv;
	private Date dateOfBirth;
	private String nameOnCard;
	private String postalCode;
}
