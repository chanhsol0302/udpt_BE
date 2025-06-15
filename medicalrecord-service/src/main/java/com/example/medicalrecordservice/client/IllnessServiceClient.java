package com.example.medicalrecordservice.client;

import com.example.medicalrecordservice.dto.IllnessResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@FeignClient(name = "illness-service", url = "http://localhost:8086")
public interface IllnessServiceClient {
	@GetMapping("/api/illnesses/{id}")
	ResponseEntity<IllnessResponse> getIllnessById(@PathVariable UUID id);
}