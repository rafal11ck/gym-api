package xyz.cursedman.gym_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.cursedman.gym_api.domain.entities.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
