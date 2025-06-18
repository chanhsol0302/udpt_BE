package com.example.medicalrecordservice.dto;

import com.example.medicalrecordservice.model.Medicine;
import lombok.Data;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@Data
public class MedicalrecordCreateRequest {
	private UUID patientId;
	private String patientName;
	private UUID doctorId;
	private String doctorName;
	private UUID appointmentId;
	private LocalDateTime visitDate;
	private List<UUID> illnessId;
	private List<UUID> treatmentId;
	private String note;
    private List<Medicine> medicines;
}