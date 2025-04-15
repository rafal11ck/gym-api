package xyz.cursedman.gym_api.domain.dtos.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.cursedman.gym_api.domain.entities.Country;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
	private UUID uuid;
	private Country country;
	private String cardNumber;
	private String cvv;
	private Date dateOfBirth;
	private String nameOnCard;
	private String postalCode;
}
