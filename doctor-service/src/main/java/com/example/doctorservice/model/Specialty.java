package com.example.doctorservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "specialties")
@Data
public class Specialty {
	@Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 191)
    private String name;

    @Column(length = 191)
    private String description;
}
