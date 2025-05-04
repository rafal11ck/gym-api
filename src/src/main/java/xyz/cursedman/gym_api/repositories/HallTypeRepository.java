package xyz.cursedman.gym_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.cursedman.gym_api.domain.entities.HallType;

import java.util.UUID;

public interface HallTypeRepository extends JpaRepository<HallType, UUID> {
}
