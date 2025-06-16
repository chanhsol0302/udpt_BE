package com.example.medicalrecordservice.client;

import com.example.medicalrecordservice.dto.PrescriptionCreateRequest;
import com.example.medicalrecordservice.dto.PrescriptionCreateResponse;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

@FeignClient(name = "prescription-service", url = "http://localhost:8089")
public interface PrescriptionServiceClient {
	@PostMapping("/api/prescriptions") 
	ResponseEntity<PrescriptionCreateResponse> createPrescription(@RequestBody PrescriptionCreateRequest request);
	
	@PutMapping("/api/prescriptions/updatePayments/{id}")
	void updatePrescriptionPaymentsById(@PathVariable UUID id);
}