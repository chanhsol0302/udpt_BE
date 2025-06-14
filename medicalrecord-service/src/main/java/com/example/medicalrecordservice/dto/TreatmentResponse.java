package com.example.medicalrecordservice.dto;

import lombok.Data;
import java.util.UUID;
import java.time.LocalDateTime;

@Data
public class TreatmentResponse {
    private UUID id;
    private String code;
    private String name;
    private float price;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}