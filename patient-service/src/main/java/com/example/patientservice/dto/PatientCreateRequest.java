package com.example.patientservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientCreateRequest {
	@NotNull(message = "User ID is required")
	private UUID userId; // userId từ user-service
	
	@NotBlank(message = "Name is required")
	private String name;
	
	@NotNull(message = "Date Of Birth is required")
	@PastOrPresent(message = "Date Of Birth cannot be in the future")
	private LocalDate dateOfBirth;
	
	@NotNull(message = "Phone is required")
	private String phone;
	
	private String gender;
	private String address;
}