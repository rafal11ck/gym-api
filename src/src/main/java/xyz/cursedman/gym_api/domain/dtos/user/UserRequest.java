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
	@NotNull
	private UUID roleUuid;

	@NotNull
	private UUID cardUuid;

	@NotNull
	private UUID membershipUuid;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@NotNull
	private Date dateOfBirth;

	@NotNull
	private String email;

	@NotNull
	private String phoneNumber;

	@NotNull
	private String imageUrl;
}
