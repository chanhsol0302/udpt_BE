package com.example.patientservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.UUID;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
	@Id
	@GeneratedValue
	private UUID id;
	
	@Column(name = "user_id", nullable = false, columnDefinition = "VARCHAR(36)")
	private UUID userId;
	
	@Column(name = "name", nullable = false, length = 191)
	private String name;
	
	@Column(name = "dateOfBirth", nullable = false)
	private LocalDate dateOfBirth;
	
	@Column(name = "gender", length = 10)
	private String gender;
	
	@Column(name = "address", columnDefinition = "TEXT")
	private String address;
	
	@Column(name = "phone", nullable = false, length = 10)
	private String phone;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
}
