package xyz.cursedman.gym_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.cursedman.gym_api.domain.entities.Chat;
import xyz.cursedman.gym_api.domain.entities.ChatParticipant;

import java.util.Set;
import java.util.UUID;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, UUID> {
	Set<ChatParticipant> findByChat(Chat chat);
	Set<ChatParticipant> findByUser_Uuid(UUID userUuid);
	Set<ChatParticipant> findByChat_Uuid(UUID chatUuid);
}
