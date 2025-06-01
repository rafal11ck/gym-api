package xyz.cursedman.gym_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.cursedman.gym_api.domain.entities.PaymentStatus;

import java.util.List;
import java.util.UUID;

public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, UUID> {
	List<PaymentStatus> findByStatusEqualsIgnoreCase(String status);
}
