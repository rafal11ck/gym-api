package xyz.cursedman.gym_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.cursedman.gym_api.domain.entities.MaintenanceTask;

import java.util.UUID;

public interface MaintenanceTaskRepository extends JpaRepository<MaintenanceTask, UUID> {
}
