package com.example.medicalrecordservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "appointment-service", url = "http://localhost:8085")
public interface AppointmentServiceClient {
	@PutMapping("/api/appointments/updateStatus") 
	void updateStatus(@RequestParam UUID id, @RequestParam Integer status);
}