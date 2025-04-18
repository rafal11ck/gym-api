package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public final class MembershipType {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;
	String type;
	Float price;
}
