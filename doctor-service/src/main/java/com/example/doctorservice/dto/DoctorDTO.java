package com.example.doctorservice.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class DoctorDTO {
	private UUID id;
    private String name;
    private UUID userId;
    private UUID specialtyId;
    private String specialtyName;
}