package com.example.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private String to;
    private String subject;
    private String body;
    private String patientName; // Thêm vào để dùng trong email body
}