package xyz.cursedman.gym_api.domain.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "user_details")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    String firstName;
    String lastName;
    Date dateOfBirth;
    String email;
    String phoneNumber;
    String imageUrl;
}
