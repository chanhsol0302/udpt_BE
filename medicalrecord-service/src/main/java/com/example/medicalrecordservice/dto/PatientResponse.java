package com.example.medicalrecordservice.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class PatientResponse {
	private UUID userId;
}