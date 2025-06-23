package com.example.reportservice.model;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "prescriptionCount")
public class PrescriptionReport {
	@Id
	private String id;
	
	@Indexed(unique = true)
	private String date;
	
	private Integer prescriptionCount;
}