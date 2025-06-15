package com.example.medicalrecordservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.medicalrecordservice.dto.PatientResponse;
import java.util.UUID;

@FeignClient(name = "patient-service", url = "http://localhost:8083")
public interface PatientServiceClient {
	@GetMapping("/api/patients/{id}") 
	PatientResponse getPatientById(@PathVariable UUID id);
}