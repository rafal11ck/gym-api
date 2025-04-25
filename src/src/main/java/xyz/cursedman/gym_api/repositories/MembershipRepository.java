package xyz.cursedman.gym_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.cursedman.gym_api.domain.entities.Membership;

import java.util.UUID;

public interface MembershipRepository extends JpaRepository<Membership, UUID> {
}
