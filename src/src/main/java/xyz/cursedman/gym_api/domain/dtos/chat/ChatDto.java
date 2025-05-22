package xyz.cursedman.gym_api.domain.dtos.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.cursedman.gym_api.domain.dtos.chatParticipant.ChatParticipantDto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChatDto {
	private UUID uuid;
	
	@Builder.Default
	private Set<ChatParticipantDto> participants = new HashSet<>();
}
