package com.example.prescriptionservice.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

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
    private String id;

    private String status = "PENDING";

    @Field("medicines")
    private List<Medicine> medicines;
    
    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;
}