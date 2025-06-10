package com.example.appointmentservice.dto;

import java.util.UUID;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AppointmentCreateRequest {
    private UUID patient_id;
    private UUID doctor_id;
    private UUID specialty_id;
    private LocalDate appointment_date;
    private Integer appointment_shift;
}