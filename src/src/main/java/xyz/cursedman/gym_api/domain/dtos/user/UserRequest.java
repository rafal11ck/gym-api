package xyz.cursedman.gym_api.domain.dtos.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

	@Builder.Default
	private Set<String> roles = new HashSet<>();

	private UUID cardUuid;

	private UUID membershipUuid;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	private Date dateOfBirth;

	@NotNull
	private String email;

	private String username;

	private String phoneNumber;

	private String imageUrl;
}
