package com.example.prescriptionservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.prescriptionservice.dto.UserResponse;
import java.util.UUID;

@FeignClient(name = "user-service", url = "http://localhost:8082")
public interface UserServiceClient {
	@GetMapping("/users/{id}") 
	UserResponse getUserById(@PathVariable UUID id);
}