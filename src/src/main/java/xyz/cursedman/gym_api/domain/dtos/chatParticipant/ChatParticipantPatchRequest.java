package xyz.cursedman.gym_api.domain.dtos.chatParticipant;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatParticipantPatchRequest {
	@NotNull
	private Date lastReadDateTime;
}
