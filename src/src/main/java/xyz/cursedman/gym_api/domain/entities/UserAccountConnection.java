package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Table(
	uniqueConstraints = @UniqueConstraint(
		columnNames = {"user_uuid", "externalAuthorizationProviderName"}
	)
)
public class UserAccountConnection {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	/**
	 * User which is linked to external authorization
	 */
	@ManyToOne
	@JoinColumn(name = "user_uuid", nullable = false)
	User user;

	/**
	 * Name of external authorizer
	 */
	@Column(nullable = false)
	String externalAuthorizationProviderName;

	/**
	 * Id of user from external authorizer
	 */
	String externalAuthorizationUserId;
}
