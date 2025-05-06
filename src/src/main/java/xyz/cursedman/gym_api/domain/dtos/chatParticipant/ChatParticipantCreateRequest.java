package xyz.cursedman.gym_api.domain.dtos.chatParticipant;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChatParticipantCreateRequest {
	@NotNull
	private UUID userUuid;

	private Date lastReadDateTime;
}
