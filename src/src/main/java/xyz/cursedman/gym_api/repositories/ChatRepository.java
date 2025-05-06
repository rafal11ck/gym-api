package xyz.cursedman.gym_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.cursedman.gym_api.domain.entities.Chat;

import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
}
