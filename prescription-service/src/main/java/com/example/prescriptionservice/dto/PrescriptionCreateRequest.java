package com.example.prescriptionservice.dto;

import com.example.prescriptionservice.model.Medicine;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class PrescriptionCreateRequest {
	private UUID patientId;
	private UUID medicalrecordId;
    private List<Medicine> medicines;
}