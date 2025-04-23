package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_participant")
public class ChatParticipant {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	@ManyToOne
	@NotNull
	Chat chat;

	@ManyToOne
	@NotNull
	User user;

	Date lastReadDateTime;
}
