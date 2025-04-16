package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

//bedzie potrzebny endpoint od krajow zeby dodac do dropdowna na froncie

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "country")
public final class Country {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	@NotNull
	@Column(nullable = false)
	String countryName;
}
