package xyz.cursedman.gym_api.domain.dtos.chatParticipant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.cursedman.gym_api.domain.entities.User;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChatParticipantDto {
	private UUID uuid;
	private User user;
	private Date lastReadDateTime;
}
