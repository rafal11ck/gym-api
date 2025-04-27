package xyz.cursedman.gym_api.domain.dtos.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.cursedman.gym_api.domain.entities.Card;
import xyz.cursedman.gym_api.domain.entities.Membership;
import xyz.cursedman.gym_api.domain.entities.UserRole;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private UUID uuid;

	private UserRole role;

	private Card card;

	private Membership membership;

	private String firstName;

	private String lastName;

	private Date dateOfBirth;

	private String email;

	private String phoneNumber;

	private String imageUrl;
}
