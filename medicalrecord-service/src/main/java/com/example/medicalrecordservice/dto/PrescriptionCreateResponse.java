package com.example.medicalrecordservice.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.example.medicalrecordservice.model.Medicine;

@Data
public class PrescriptionCreateResponse {
    private UUID id;
    private String status;
    private List<Medicine> medicines;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}