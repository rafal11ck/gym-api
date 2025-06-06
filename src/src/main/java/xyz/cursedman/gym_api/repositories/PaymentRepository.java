package xyz.cursedman.gym_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.cursedman.gym_api.domain.entities.Payment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
	List<Payment> findByExternalRefId(UUID externalRefId);
}
