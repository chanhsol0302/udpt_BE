package com.example.medicalrecordservice.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.Id;

import org.springframework.data.annotation.LastModifiedDate;

@Data
@Document(collection = "medicalrecords")
public class Medicalrecord {
	@Id
    private UUID id;

	@Field("patient_id")
	private UUID patientId;
	
	@Field("patient_name")
	private String patientName;
	
	@Field("doctor_id")
	private UUID doctorId;
	
	@Field("doctor_name")
	private String doctorName;
	
	@Field("appointment_id")
	private UUID appointmentId;
	
	@Field("visit_date")
	private LocalDateTime visitDate;
	
	@Field("illnesses")
	private List<UUID> illnessId;
	
	@Field("treatments")
	private List<UUID> treatmentId;

    @Field("prescription_id")
    private UUID prescriptionId;
    
    @Field("medicine_price")
    private float medicinePrice;
    
    @Field("treatment_price")
    private float treatmentPrice;
    
	@Field("total_price")
	private float totalPrice;

	@Field("payments")
	private boolean payments = false;
	
	private String note;
	
    @Field("created_at")
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;
    
    @Field("staff_id")
    private UUID staffId;
}