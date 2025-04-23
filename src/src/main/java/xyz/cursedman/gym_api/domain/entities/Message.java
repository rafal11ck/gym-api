package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	@ManyToOne
	Chat chat;

	@ManyToOne
	User sender;

	String content;

	Date dateTime;
}
