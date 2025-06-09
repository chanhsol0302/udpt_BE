package com.example.patientservice.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PatientUpdateRequest {
	private String name;
	private LocalDate dateOfBirth;
	private String gender;
	private String address;
	private String phone;
}