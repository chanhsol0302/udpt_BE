package com.example.patientservice.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PatientResponse {
	private UUID id;
	private UUID userId;
	private String name;
	private LocalDate dateOfBirth;
	private String gender;
	private String address;
	private String phone;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}