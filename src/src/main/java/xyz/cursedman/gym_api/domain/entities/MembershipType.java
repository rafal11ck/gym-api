package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class MembershipType extends Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;
	String type;


}
