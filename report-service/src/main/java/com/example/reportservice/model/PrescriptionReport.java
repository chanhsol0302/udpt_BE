package com.example.reportservice.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "prescriptionCount")
public class PrescriptionReport {
    private String date;
	private Integer prescriptionCount;
}