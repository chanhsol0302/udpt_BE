package com.example.medicalrecordservice.dto;

import com.example.medicalrecordservice.model.Medicine;
import lombok.Data;
import java.util.List;

@Data
public class PrescriptionCreateRequest {
    private List<Medicine> medicines;
}