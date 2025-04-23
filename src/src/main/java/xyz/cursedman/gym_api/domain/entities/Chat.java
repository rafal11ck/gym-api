package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat")
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;
}
