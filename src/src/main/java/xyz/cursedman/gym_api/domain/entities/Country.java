package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

//bedzie potrzebny endpoint od krajow zeby dodac do dropdowna na froncie

@Entity
@Table(name = "country")
public class Country {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	@NotNull
	@Column(nullable = false)
	String country;
}
