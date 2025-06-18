package com.example.prescriptionservice.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@Document(collection = "prescriptions")
public class Prescription {
	@Id
    //@GeneratedValue - không phải JPA nên không có phần tự động sinh như này
    private UUID id;
	
	@Field("patient_id")
	private UUID patientId;
	
	@Field("medicalrecord_id")
	private UUID medicalrecordId;
	
	@Field("pharmacist_id")
	private UUID pharmacistId;

    private String status = "PENDING";

    @Field("medicines")
    private List<Medicine> medicines;
    
    @Field("medicine_price")
    private float medicinePrice;
    
    @Field("payments")
    private boolean payments = false;
    
    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;
}