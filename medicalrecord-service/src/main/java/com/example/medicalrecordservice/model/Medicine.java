package com.example.medicalrecordservice.model;

import lombok.Data;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Medicine {
	@Field("medicine_id")
    private UUID medicineId;

    private String name;
    private Integer quantity;
    private float price;
    private String note;
}