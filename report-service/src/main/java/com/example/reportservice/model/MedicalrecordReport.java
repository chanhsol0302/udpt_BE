package com.example.reportservice.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "medicalrecordCount")
public class MedicalrecordReport {
    private String date;
	private Integer medicalrecordCount;
}