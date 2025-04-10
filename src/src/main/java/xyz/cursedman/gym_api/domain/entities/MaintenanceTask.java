package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "maintenance_task")
@Data
public class MaintenanceTask {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	User maintainer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	Hall maintenanceHall;

	@Column(nullable = false)
	Date plannedStartDate;

	@Column(nullable = false)
	Date plannedEndDate;

	String description;
}
