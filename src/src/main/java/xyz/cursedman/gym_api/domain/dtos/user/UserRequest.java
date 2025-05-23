package xyz.cursedman.gym_api.domain.dtos.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
	private UUID roleUuid;

	private UUID cardUuid;

	private UUID membershipUuid;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	private Date dateOfBirth;

	@NotNull
	private String email;

	private String phoneNumber;

	private String imageUrl;
}
