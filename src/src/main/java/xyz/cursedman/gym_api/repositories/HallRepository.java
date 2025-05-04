package xyz.cursedman.gym_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.cursedman.gym_api.domain.entities.Hall;

import java.util.UUID;

public interface HallRepository extends JpaRepository<Hall, UUID> {
}
