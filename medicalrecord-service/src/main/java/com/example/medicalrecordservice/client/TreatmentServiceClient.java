package com.example.medicalrecordservice.client;

import com.example.medicalrecordservice.dto.TreatmentResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@FeignClient(name = "treatment-service", url = "http://localhost:8087")
public interface TreatmentServiceClient {
	@GetMapping("/api/treatments/{id}")
	ResponseEntity<TreatmentResponse> getTreatmentById(@PathVariable UUID id);
}