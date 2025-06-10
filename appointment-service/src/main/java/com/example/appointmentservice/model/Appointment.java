package com.example.appointmentservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;
import java.time.LocalDate;

@Entity
@Table(name = "appointments")
@Data
public class Appointment {
	@Id
    @GeneratedValue
    private UUID id;

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "doctor_id", nullable = false)
    private UUID doctorId;
    
    @Column(name = "specialty_id", nullable = false)
    private UUID specialtyId;
    
    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;
    
    @Column(name = "appointment_shift", nullable = false)
    private Integer appointmentShift;
    
    @Column(nullable = false)
    private Integer status = 1;
}
