package xyz.cursedman.gym_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.cursedman.gym_api.domain.entities.Country;

import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {
}
