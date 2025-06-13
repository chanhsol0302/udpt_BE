package com.example.prescriptionservice.dto;

import com.example.prescriptionservice.model.Medicine;
import lombok.Data;
import java.util.List;

@Data
public class PrescriptionCreateRequest {
    private List<Medicine> medicines;
}