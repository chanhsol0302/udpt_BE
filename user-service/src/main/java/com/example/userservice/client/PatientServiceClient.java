// Feign Client Interface được gọi trong UserService.java
package com.example.userservice.client;

import com.example.userservice.dto.PatientCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "patient-service", url = "http://localhost:8083") // Tên dịch vụ và URL
public interface PatientServiceClient {
	@PostMapping("/api/patients") // Endpoint để tạo patient trong patient-service
	void createPatient(@RequestBody PatientCreateRequest request);
}