package xyz.cursedman.gym_api.domain.dtos.country;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {
	private UUID uuid;
	private String countryName;
}
