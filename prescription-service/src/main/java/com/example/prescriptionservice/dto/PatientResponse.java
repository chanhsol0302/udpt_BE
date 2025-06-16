package com.example.prescriptionservice.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class PatientResponse {
	private UUID userId;
}