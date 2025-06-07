package xyz.cursedman.gym_api.domain.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.cursedman.gym_api.domain.entities.Membership;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private UUID uuid;

	private Collection<String> roles;

	private Membership membership;

	private String firstName;

	private String lastName;

	private Date dateOfBirth;

	private String email;

	private String username;

	private String phoneNumber;

	private String imageUrl;
}
