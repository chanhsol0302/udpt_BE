package com.example.medicalrecordservice.dto;

import com.example.medicalrecordservice.model.Medicine;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class PrescriptionCreateRequest {
	private UUID patientId;
    private List<Medicine> medicines;
}