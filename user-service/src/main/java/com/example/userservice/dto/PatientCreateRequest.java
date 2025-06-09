package com.example.userservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class PatientCreateRequest{
	private UUID userId; //ID của user vừa tạo
	private String name;
	private LocalDate dateOfBirth;
	private String gender;
	private String address;
	private String phone;
}